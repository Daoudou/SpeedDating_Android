package fr.daoudou.speeddating.main

import android.os.Bundle
import android.os.StrictMode
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import fr.daoudou.speeddating.Info.PeopleInfos
import fr.daoudou.speeddating.R
import fr.daoudou.speeddating.Service.PeopleService
import fr.daoudou.speeddating.Service.UserService

class PeopleListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.people_list_main)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val svc = PeopleService()
        val queryIdUserAdd = UserService().getToken()
        val listPeople = findViewById<ListView>(R.id.listPeopleView)
        Thread(Runnable {
            runOnUiThread {
                listPeople.visibility = View.INVISIBLE
            }
            val result = svc.getAllInfos(queryIdUserAdd)
            runOnUiThread {
                listPeople.adapter = ArrayAdapter<PeopleInfos>(
                    applicationContext, android.R.layout.simple_list_item_1,
                    android.R.id.text1,
                    result
                )
                listPeople.visibility = View.VISIBLE
            }
        }).start()
    }
}