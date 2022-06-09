package fr.daoudou.speeddating.main

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.daoudou.speeddating.Info.PeopleInfos
import fr.daoudou.speeddating.R
import fr.daoudou.speeddating.Service.PeopleService
import fr.daoudou.speeddating.Service.UserService
import fr.daoudou.speeddating.adapter.ListPeopleAdapter
import java.io.IOException

class PeopleListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.people_list_main)

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val recyclerViewPeople = findViewById<RecyclerView>(R.id.recyclerViewPeopleView)
        Thread(Runnable {
            runOnUiThread {
                try {
                    val svc = PeopleService()
                    val queryIdUserAdd = UserService().getToken()
                    val svcPeopleList = svc.getAllInfos(queryIdUserAdd)

                    recyclerViewPeople.layoutManager = LinearLayoutManager(this)
                    recyclerViewPeople.adapter = ListPeopleAdapter(this, svcPeopleList as ArrayList<PeopleInfos>)
                    (recyclerViewPeople.adapter as ListPeopleAdapter).notifyDataSetChanged()

                }catch (e :IOException){
                    e.printStackTrace()
                }
            }
        }).start()

        findViewById<Button>(R.id.buttonRetrun).setOnClickListener {
            val intent = Intent(this,AcceuilActivity::class.java)
            startActivity(intent)
        }
    }
}