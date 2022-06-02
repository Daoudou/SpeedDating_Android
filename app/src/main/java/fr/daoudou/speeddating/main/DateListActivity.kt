package fr.daoudou.speeddating.main

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import fr.daoudou.speeddating.Info.DateInfo
import fr.daoudou.speeddating.R
import fr.daoudou.speeddating.Service.DateService

class DateListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.date_list_main)

        val svc = DateService()
        val listDate = findViewById<ListView>(R.id.listDateListView)
        Thread(Runnable {
            runOnUiThread {
                listDate.visibility = View.INVISIBLE
            }
            val result = svc.getAllDate()
            runOnUiThread {
                listDate.adapter = ArrayAdapter<DateInfo>(
                    applicationContext,android.R.layout.simple_list_item_1,
                    android.R.id.text1,
                    result
                )
                listDate.visibility = View.VISIBLE
            }
        }).start()
    }
}