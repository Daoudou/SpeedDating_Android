package fr.daoudou.speeddating.main

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import fr.daoudou.speeddating.Info.UserInfo
import fr.daoudou.speeddating.R
import fr.daoudou.speeddating.Service.UserService

class ProfilActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profil_main)
        val svc  = UserService()
        val svcToken = UserService().getToken().toString()
        val listPseudo = findViewById<ListView>(R.id.listViewProfil)
        Thread(Runnable{
            val result = svc.getUserById(svcToken)
                runOnUiThread{
                    listPseudo.adapter = ArrayAdapter<UserInfo>(applicationContext,
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1,
                        result)
                }
        }).start()

        findViewById<Button>(R.id.buttonRetrun).setOnClickListener {
            val intent = Intent(this,AcceuilActivity::class.java)
            startActivity(intent)
        }
    }
}