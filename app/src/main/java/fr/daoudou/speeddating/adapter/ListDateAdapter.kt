package fr.daoudou.speeddating.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
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

class ListDateAdapter(private val context: Context,
                      private val dataSource: ArrayList<DateInfo>
                    ) : RecyclerView.Adapter<ListDateViewHolder>() {


    private val svc = DateService()


    @SuppressLint("InflateParams")
    private val promptsView = LayoutInflater.from(context).inflate(R.layout.alert_dialog_update_date,null)
    private val editTextComment = promptsView.findViewById<EditText>(R.id.editTextDateCommentaireUpdate)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListDateViewHolder {
        return ListDateViewHolder(LayoutInflater.from(context).inflate(R.layout.listrow,parent,false))
    }

    @SuppressLint("SetTextI18n", "NotifyDataSetChanged", "SimpleDateFormat")
    override fun onBindViewHolder(holder: ListDateViewHolder, position: Int) {

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
            notifyDataSetChanged()
        }


        holder.buttonUpdate.setOnClickListener {
           AlertDialog.Builder(context).apply {
                setView(promptsView)
                setCancelable(false)
                setPositiveButton("OK", DialogInterface.OnClickListener { dialogInterface, which ->
                    Toast.makeText(context,"Ajout en cours",Toast.LENGTH_SHORT).show()
                    dialogInterface.dismiss()
                })
            }.create().show()
        }
    }

    override fun getItemCount(): Int {
        return dataSource.size
    }

}
