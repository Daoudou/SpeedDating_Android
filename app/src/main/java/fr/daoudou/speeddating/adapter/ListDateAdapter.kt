package fr.daoudou.speeddating.adapter

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.provider.Settings.Global.getString
import android.view.Gravity.apply
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.floatingactionbutton.FloatingActionButton
import fr.daoudou.speeddating.Info.DateInfo
import fr.daoudou.speeddating.R
import fr.daoudou.speeddating.Security.ResponseCode
import fr.daoudou.speeddating.Service.DateService
import fr.daoudou.speeddating.View.ListDateViewHolder
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import androidx.fragment.app.FragmentActivity
import fr.daoudou.speeddating.main.DateListActivity
import java.io.IOException

class ListDateAdapter(private val context: Context,
                      private val dataSource: ArrayList<DateInfo>
                    ) : RecyclerView.Adapter<ListDateViewHolder>() {


    private val svc = DateService()
    private var cal = Calendar.getInstance()
    private var dateFormatedUpdate : String = ""
    private var queryNote : Int = 0

    @SuppressLint("InflateParams")
    private val promptsView = LayoutInflater.from(context).inflate(R.layout.alert_dialog_update_date,null)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListDateViewHolder {
        return ListDateViewHolder(LayoutInflater.from(context).inflate(R.layout.listrow,parent,false))
    }

    @SuppressLint("SetTextI18n", "NotifyDataSetChanged", "SimpleDateFormat")
    override fun onBindViewHolder(holder: ListDateViewHolder, position: Int) {

        val dateSetListener = object : DatePickerDialog.OnDateSetListener{
            override fun onDateSet(view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_YEAR,dayOfMonth)
                updateDateInView()
            }

        }


        val listDate = dataSource[position]
        holder.textViewDateList.text = "Rencontre le : " + listDate.date + "\n" + "Avec : " + listDate.peopleAdd

        holder.buttonDelete.setOnClickListener {
            val DeleteDate = svc.deleteDate(listDate.id)
            if (DeleteDate == ResponseCode.StatusCode.OK ){
                AlertDialog.Builder(context).apply{
                    setTitle("Suppression d'une recontre")
                    setMessage("Rencontre supprimer avec succees")
                    setPositiveButton("D'accord",DialogInterface.OnClickListener { dialog, which ->
                        Toast.makeText(context,"Suppression effectuer",Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    })
                }.create().show()
            } else if (DeleteDate == ResponseCode.StatusCode.BadRequest){
                AlertDialog.Builder(context).apply{
                    setTitle("Suppression d'une recontre")
                    setMessage("Erreur lors de la suppression de la rencontre")
                    setPositiveButton("D'accord",DialogInterface.OnClickListener { dialog, which ->
                        Toast.makeText(context,"Suppression non effectuer",Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    })
                }.create().show()
            }
            val intent = Intent(context,DateListActivity::class.java)
            context.startActivity(intent)
            notifyDataSetChanged()
        }

        holder.buttonUpdate.setOnClickListener {
           AlertDialog.Builder(context).apply {
                setView(promptsView)
                setCancelable(false)
              promptsView.findViewById<Button>(R.id.ButtonUpdateDate).setOnClickListener(object : View.OnClickListener {
                   override fun onClick(view: View?) {
                     DatePickerDialog(context,dateSetListener,
                           cal.get(Calendar.YEAR),
                           cal.get(Calendar.MONTH),
                           cal.get(Calendar.DAY_OF_MONTH)).show()
                   }
               })

               val note  = context.resources.getStringArray(R.array.NotePeople)
                val spinner = promptsView.findViewById<Spinner>(R.id.SpinnerUpdateNote)
                if (spinner != null){
                    val adapter = ArrayAdapter(context,
                        android.R.layout.simple_spinner_item, note
                        )
                    spinner.adapter = adapter
                    spinner.onItemSelectedListener = object :
                        AdapterView.OnItemSelectedListener{
                        override fun onItemSelected(p0: AdapterView<*>?,view: View?,position: Int,id: Long
                        ) {
                            Toast.makeText(context,"Note selectionner" + " " + "" + note[position], Toast.LENGTH_LONG).show()
                            queryNote = note[position].toInt()
                        }
                        override fun onNothingSelected(p0: AdapterView<*>?) {
                            Toast.makeText(context, "Veuilez entre un chiffre pour la note", Toast.LENGTH_LONG).show()
                        }
                    }
                }
                setPositiveButton("OK", DialogInterface.OnClickListener { dialogInterface, which ->
                    Toast.makeText(context,"Ajout en cours",Toast.LENGTH_SHORT).show()
                    dialogInterface.dismiss()
                    val intent = Intent(context,DateListActivity::class.java)
                    context.startActivity(intent)
                })

               promptsView.findViewById<Button>(R.id.buttonUpdateDateComfirm).setOnClickListener {
                   val editTextComment = promptsView.findViewById<EditText>(R.id.editTextDateCommentaireUpdate).text.toString()
                   try {
                       val dateUpdate = svc.updateDate(listDate.id,dateFormatedUpdate,queryNote,editTextComment)
                       if (dateUpdate == ResponseCode.StatusCode.OK){
                           Toast.makeText(context,"Mise a jour terminer",Toast.LENGTH_SHORT).show()
                           val intent = Intent(context,DateListActivity::class.java)
                           context.startActivity(intent)
                       }else if(dateUpdate == ResponseCode.StatusCode.BadRequest){
                           Toast.makeText(context,"Mise a jour echouer",Toast.LENGTH_SHORT).show()
                       }
                   }catch (e : IOException){
                       e.printStackTrace()
                   }
                   notifyDataSetChanged()
               }
            }.create().show()
            notifyDataSetChanged()
        }
    }

    private fun updateDateInView(){
        val myFormat = "yyyy-MM-dd"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        dateFormatedUpdate = sdf.format(cal.getTime())
    }

    override fun getItemCount(): Int {
        return dataSource.size
    }

}
