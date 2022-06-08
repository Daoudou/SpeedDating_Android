package fr.daoudou.speeddating.main

import android.os.Bundle
import android.os.StrictMode
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.daoudou.speeddating.Info.DateInfo
import fr.daoudou.speeddating.R
import fr.daoudou.speeddating.Service.DateService
import fr.daoudou.speeddating.Service.UserService
import fr.daoudou.speeddating.adapter.ListDateAdapter
import java.io.IOException

class DateListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.date_list_main)

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

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