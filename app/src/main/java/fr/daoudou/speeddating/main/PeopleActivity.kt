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
import fr.daoudou.speeddating.Service.PeopleService
import fr.daoudou.speeddating.Service.UserService
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class PeopleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val btnPeoplePicker : FloatingActionButton
        super.onCreate(savedInstanceState)
        setContentView(R.layout.people_add_main)
        val svc = PeopleService()
        var birthdateFormated : String? = null
        // Le spinner pour la note des rencontre
        val note  = resources.getStringArray(R.array.NotePeople)
        val spinner = findViewById<Spinner>(R.id.noteSpinnerPeople)
            if (spinner != null){
                val adapter = ArrayAdapter(this,
                    android.R.layout.simple_spinner_item, note)
                    spinner.adapter = adapter

                    spinner.onItemSelectedListener = object :
                    AdapterView.OnItemSelectedListener{
                        override fun onItemSelected(p0: AdapterView<*>?,view: View?,position: Int,id: Long
                        ) {
                           Toast.makeText(this@PeopleActivity,
                          getString(R.string.selected_item) + " " + "" + note[position], Toast.LENGTH_LONG).show()
                        }

                        override fun onNothingSelected(p0: AdapterView<*>?) {
                            Toast.makeText(this@PeopleActivity, "Veuilez entre un chiffre pour la note", Toast.LENGTH_LONG).show()
                        }
                    }
            }

        // Le selector pour la date de naissance
        btnPeoplePicker = findViewById(R.id.floatingButtonAddPeople)
        btnPeoplePicker.setOnClickListener {
            val birthdate = MaterialDatePicker.Builder.datePicker().build()
            birthdate.show(supportFragmentManager, "Birthdate")
            birthdate.addOnPositiveButtonClickListener {
                val formateBirthdate = SimpleDateFormat("dd-MM-yyyy")
                birthdateFormated = formateBirthdate.format(Date(it))
            }
        }

        findViewById<Button>(R.id.addPeopleBtn).setOnClickListener {
            val queryFirstName = findViewById<EditText>(R.id.editTextFirstNamePeople).text.toString()
            val queryLastName = findViewById<EditText>(R.id.editTextLastNamePeople).text.toString()
            val querySexe = findViewById<EditText>(R.id.editTextSexePeople).text.toString()
            val queryIdUserAdd = UserService().getToken()
            Thread(Runnable {
                try {
                    val peopleAdd = birthdateFormated?.let { it1 -> svc.createUserInfos(queryFirstName,queryLastName,querySexe,it1, queryIdUserAdd)
                    }
                    runOnUiThread {
                        if(peopleAdd == ResponseCode.StatusCode.Created){
                            AlertDialog.Builder(this).apply {
                                setTitle("Ajout d'une personne")
                                setMessage("Personne ajouter avec succees")
                                setPositiveButton("D'accord",DialogInterface.OnClickListener { dialog, which ->
                                    Toast.makeText(this@PeopleActivity,
                                    "Ajout effectuer",
                                    Toast.LENGTH_SHORT).show()
                                    dialog.dismiss()
                                    //val intent = Intent(this@PeopleActivity, PeopleListActivity::class.java)
                                    //startActivity(intent)
                                })
                            }.create().show()
                        }else if(peopleAdd == ResponseCode.StatusCode.BadRequest){
                            val dialogError = AlertDialog.Builder(this).apply {
                                setTitle("Ajout d'une personne impossible.")
                                setMessage("Merci de vérifier les informations renseignées ou Personne deja existante")
                                setPositiveButton("Retry",DialogInterface.OnClickListener{
                                        dialog,which->
                                    val intent = Intent(this@PeopleActivity, PeopleActivity::class.java)
                                    startActivity(intent)
                                })
                            }.create().show()
                        }
                    }
                }catch (e :IOException){
                    println(e)
                }
            }).start()
        }
    }
}