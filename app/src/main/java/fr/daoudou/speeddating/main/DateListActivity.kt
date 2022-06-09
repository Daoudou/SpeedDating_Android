package fr.daoudou.speeddating.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.floatingactionbutton.FloatingActionButton
import fr.daoudou.speeddating.Info.DateInfo
import fr.daoudou.speeddating.R
import fr.daoudou.speeddating.Service.DateService
import fr.daoudou.speeddating.Service.UserService
import fr.daoudou.speeddating.adapter.ListDateAdapter
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class DateListActivity : AppCompatActivity() {

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.date_list_main)

        var dateFormated : String = ""

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        findViewById<Button>(R.id.buttonRetrun).setOnClickListener {
            val intent = Intent(this,AcceuilActivity::class.java)
            startActivity(intent)
        }



        val recyclerViewDateList = findViewById<RecyclerView>(R.id.recyclerDateList)

        Thread(Runnable {
            runOnUiThread {
                try {
                    val token = UserService().getToken()
                    val svc = DateService()
                    val svcDateList = svc.getAllDateByUser(token)

                    recyclerViewDateList.layoutManager = LinearLayoutManager(this)
                    recyclerViewDateList.adapter = ListDateAdapter(this, svcDateList as ArrayList<DateInfo>)
                    (recyclerViewDateList.adapter as ListDateAdapter).notifyDataSetChanged()

                }catch (e  :IOException){
                    e.printStackTrace()
                }
            }
        }).start()
    }
}