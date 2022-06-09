package fr.daoudou.speeddating.main

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.daoudou.speeddating.Info.DateInfo
import fr.daoudou.speeddating.Info.UserInfo
import fr.daoudou.speeddating.R
import fr.daoudou.speeddating.Service.UserService
import fr.daoudou.speeddating.adapter.ListDateAdapter
import fr.daoudou.speeddating.adapter.ProfilAdapter
import java.io.IOException

class ProfilActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profil_main)

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        findViewById<Button>(R.id.buttonRetrun).setOnClickListener {
            val intent = Intent(this,AcceuilActivity::class.java)
            startActivity(intent)
        }

        val recyclerViewProfil = findViewById<RecyclerView>(R.id.recyclerProfil)


        Thread(Runnable{
                runOnUiThread{
                   try {
                       val svc  = UserService()
                       val svcToken = UserService().getToken()
                       val result = svc.getUserById(svcToken)

                       recyclerViewProfil.layoutManager = LinearLayoutManager(this)
                       recyclerViewProfil.adapter = ProfilAdapter(this, result as ArrayList<UserInfo>)
                       (recyclerViewProfil.adapter as ProfilAdapter).notifyDataSetChanged()

                   }catch (e  :IOException){
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