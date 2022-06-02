package fr.daoudou.speeddating.main

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.floatingactionbutton.FloatingActionButton
import fr.daoudou.speeddating.R
import java.text.SimpleDateFormat
import java.util.*

class PeopleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val btnPeoplePicker : FloatingActionButton
        super.onCreate(savedInstanceState)
        setContentView(R.layout.people_add_main)

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
                val formateBirthdate = SimpleDateFormat("dd=MM-yyyy")
                val birthdateFormated = formateBirthdate.format(Date(it))
                Toast.makeText(this,"$birthdateFormated is selected", Toast.LENGTH_LONG).show()
            }
        }
    }
}