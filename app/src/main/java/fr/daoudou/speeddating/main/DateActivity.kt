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

class DateActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        val btnDatePicker: FloatingActionButton
        super.onCreate(savedInstanceState)
        setContentView(R.layout.date_add_main)

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
                val dateFormated = formatDate.format(Date(it))
                Toast.makeText(this,"$dateFormated is selected",Toast.LENGTH_SHORT).show()
            }

            date.addOnCancelListener{
                Toast.makeText(this,"Date select cancelled", Toast.LENGTH_LONG).show()
            }

        }
    }
}