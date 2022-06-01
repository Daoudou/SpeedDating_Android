package fr.daoudou.speeddating

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import fr.daoudou.speeddating.Info.UserInfo
import fr.daoudou.speeddating.Service.UserService

class ProfilActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profil_main)
        val svc  = UserService()
        val listPseudo = findViewById<ListView>(R.id.listViewProfil)
        Thread(Runnable{
            val result = svc.getUserById()
                runOnUiThread{
                    listPseudo.adapter = ArrayAdapter<UserInfo>(applicationContext,
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1,
                        result)
                }
        }).start()
    }
}