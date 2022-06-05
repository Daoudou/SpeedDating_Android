package fr.daoudou.speeddating.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import fr.daoudou.speeddating.Service.LinkService
import fr.daoudou.speeddating.Service.UserService
import java.io.IOException

class LinkActivity : AppCompatActivity() {
    var idInfos :String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            val svc = LinkService()
            val queryIdUserAdd = UserService().getToken()
            idInfos = svc.getAll(queryIdUserAdd).toString()
            val idValue = Intent(this,DateActivity::class.java).apply {
                putExtra("Date",idInfos)
            }
            startActivity(idValue)
        }catch (e : IOException){
            println(e)
        }
    }

}