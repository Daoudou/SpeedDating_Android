package fr.daoudou.speeddating.main

import android.content.DialogInterface
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.floatingactionbutton.FloatingActionButton
import fr.daoudou.speeddating.Info.PeopleInfos
import fr.daoudou.speeddating.R
import fr.daoudou.speeddating.Security.ResponseCode
import fr.daoudou.speeddating.Service.DateService
import fr.daoudou.speeddating.Service.LinkService
import fr.daoudou.speeddating.Service.PeopleService
import fr.daoudou.speeddating.Service.UserService
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class DateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        val btnDatePicker: FloatingActionButton
        super.onCreate(savedInstanceState)
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        setContentView(R.layout.date_add_main)
        val svcLink = LinkService()
        val svcDate = DateService()
        val svcPeople = PeopleService()
        val queryIdUserAdd = UserService().getToken()
        var queryIdInfoDateAdd = LinkService().getAll(queryIdUserAdd)
        var dateFormated : String =""
        var queryNote : String = ""
        var queryPeople  :String =""

        // Le spinner pour la note des rencontre
        val note = resources.getStringArray(R.array.NoteDate)
        val spinner = findViewById<Spinner>(R.id.noteSpinner)
            if(spinner != null){
                val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, note)
                spinner.adapter = adapter

                spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        Toast.makeText(this@DateActivity,
                        getString(R.string.selected_item) + " " + "" + note[position], Toast.LENGTH_SHORT).show()
                        queryNote = note[position]
                    }
                    override fun onNothingSelected(p0: AdapterView<*>?) {
                        Toast.makeText(this@DateActivity, "Veuilez entre un chiffre pour la note", Toast.LENGTH_LONG).show()
                    }
                }
            }

        //Le selector de la date de rencontre
        btnDatePicker = findViewById(R.id.floatingButtonAddDate)
        btnDatePicker.setOnClickListener{
            val date = MaterialDatePicker.Builder.datePicker().build()
            date.show(supportFragmentManager, "Date")

            date.addOnPositiveButtonClickListener {
                val formatDate = SimpleDateFormat("dd-MM-yyyy")
                dateFormated = formatDate.format(Date(it))
                Toast.makeText(this,"$dateFormated is selected",Toast.LENGTH_SHORT).show()
                var dateTextView = findViewById<TextView>(R.id.textViewDate).toString()
                dateTextView = dateFormated.toString()
            }

            date.addOnCancelListener{
                Toast.makeText(this,"Date select cancelled", Toast.LENGTH_LONG).show()
            }

        }


        var queryIdInfosDate : String = ""
            val spinnerDateName = findViewById<Spinner>(R.id.spinnerNameDateAdd)
                Thread(Runnable {
                    runOnUiThread {
                        try {
                            var People: List<PeopleInfos> = svcPeople.getAllInfosName()
                            var PeopleId : List<PeopleInfos> = svcPeople.getIdInfosName()
                            if(spinnerDateName != null){
                                val adapterListName = ArrayAdapter(this,
                                    android.R.layout.simple_spinner_item,
                                    People)
                                spinnerDateName.adapter = adapterListName

                                spinnerDateName.onItemSelectedListener = object :
                                    AdapterView.OnItemSelectedListener{
                                    override fun onItemSelected(
                                        p0: AdapterView<*>?,
                                        view: View?,
                                        position: Int,
                                        id: Long
                                    ) {
                                        queryPeople = People[position].toString()
                                        queryIdInfosDate = PeopleId[position].id
                                    }

                                    override fun onNothingSelected(p0: AdapterView<*>?) {
                                        TODO("Not yet implemented")
                                    }

                                }

                            }
                        }catch (e  :IOException){
                            e.printStackTrace()
                        }
                    }
                }).start()


        val queryComment = findViewById<EditText>(R.id.editDateComment).text.toString()

        findViewById<Button>(R.id.addDateBtn).setOnClickListener {
                    Thread(Runnable{
                    runOnUiThread {
                        try {
                            val createDateUserAdd = svcDate.createDatingByUser(queryPeople,dateFormated,queryComment,queryNote,queryIdInfosDate,queryIdUserAdd)
                            if (createDateUserAdd == ResponseCode.StatusCode.Created){
                                AlertDialog.Builder(this).apply {
                                    setTitle("Ajout d'une recontre")
                                    setMessage("Rencontre ajouter avec succees")
                                    setPositiveButton("D'accord",DialogInterface.OnClickListener { dialog, which ->
                                        Toast.makeText(this@DateActivity,"Ajout de la rencontre effectuer",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        dialog.dismiss()
                                    })
                                }.create().show()
                            }else if (createDateUserAdd == ResponseCode.StatusCode.BadRequest){
                                AlertDialog.Builder(this@DateActivity).apply {
                                    setTitle("Erreur d'ajout d'une rencontre.")
                                    setMessage("Impossible d'ajouter une rencontre")
                                    setPositiveButton("Retry",DialogInterface.OnClickListener{
                                            dialog,which->
                                        Toast.makeText(this@DateActivity,
                                            "Impossible d'ajout la rencontre",
                                            Toast.LENGTH_SHORT).show()
                                        dialog.dismiss()
                                    })
                                }.create().show()
                            }
                        }catch (e  :IOException){
                            e.printStackTrace()
                        }
                    }
                }).start()
                }

    }
}

private fun String?.get(index: String): String {
    return index
}
