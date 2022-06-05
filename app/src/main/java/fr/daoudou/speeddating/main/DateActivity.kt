package fr.daoudou.speeddating.main

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.floatingactionbutton.FloatingActionButton
import fr.daoudou.speeddating.R
import fr.daoudou.speeddating.Security.ResponseCode
import fr.daoudou.speeddating.Service.DateService
import fr.daoudou.speeddating.Service.LinkService
import fr.daoudou.speeddating.Service.UserService
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class DateActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        val btnDatePicker: FloatingActionButton
        super.onCreate(savedInstanceState)
        setContentView(R.layout.date_add_main)
        val svcLink = LinkService()
        val svcDate = DateService()
        val queryIdUserAdd = UserService().getToken()
        var dateFormated : String? = null
        var queryNote : String? = null
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
            }

            date.addOnCancelListener{
                Toast.makeText(this,"Date select cancelled", Toast.LENGTH_LONG).show()
            }

        }

        // SE CODE EST A DONNER LORS DE LA RECHERCHE DES PERSONNE ENREGISTRER PTN
        // je pense que tout sa ne server a rien enfin pas totalement non plus
        val intent = Intent(this,LinkActivity::class.java)
        startActivity(intent)
        val idGet : String? = intent.getStringExtra("Data")
        val spinnerDateName = findViewById<Spinner>(R.id.spinnerNameDateAdd)
            if (spinnerDateName != null){
                val adapterDate : ArrayAdapter<String?> = ArrayAdapter<String?>(
                    this,
                    android.R.layout.simple_spinner_item, idGet as List<String>
                )
                adapterDate.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerDateName.adapter = adapterDate
            }

        findViewById<Button>(R.id.addDateBtn).setOnClickListener {
            var queryPeople : String? = null
            spinnerDateName.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{
                override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    queryPeople = idGet?.get(position).toString()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

            }

            Thread(Runnable {
                try {
                    val queryIdInfosDate = svcLink.getAll(queryIdUserAdd).toString()
                    val queryComment = findViewById<EditText>(R.id.editDateComment).toString()
                    val createDateUser = queryPeople?.let { it1 ->
                        dateFormated?.let { it2 ->
                            queryNote?.let { it3 ->
                                svcDate.createDatingByUser(it1,
                                    it2,
                                    queryComment,
                                    it3,queryIdInfosDate,
                                    queryIdUserAdd)
                            }
                        }
                    }

                    if (createDateUser != ResponseCode.StatusCode.Created){
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
                    }else if (createDateUser != ResponseCode.StatusCode.BadRequest){
                        AlertDialog.Builder(this).apply {
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
                    println(e)
                }
            }).start()
        }

    }


}