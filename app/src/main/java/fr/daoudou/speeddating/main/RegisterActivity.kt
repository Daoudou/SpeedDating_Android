package fr.daoudou.speeddating.main

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import fr.daoudou.speeddating.R
import fr.daoudou.speeddating.Security.ResponseCode
import fr.daoudou.speeddating.Service.UserService
import java.io.IOException

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_page)
        val svcRegister = UserService()

        findViewById<Button>(R.id.buttonInscriptionPage).setOnClickListener {
            val progressBarRegister = findViewById<ProgressBar>(R.id.progressBarRegister)
            val queryPseudo = findViewById<EditText>(R.id.editTextRegisterPseudo).text.toString()
            val queryEmail = findViewById<EditText>(R.id.editTextRegisterEmail).text.toString()
            val queryPassword = findViewById<EditText>(R.id.editTextRegisterPassword).text.toString()
            Thread(Runnable{
                runOnUiThread{
                    progressBarRegister.visibility = View.INVISIBLE
                }
                try {
                    val registerPage = svcRegister.createUser(queryPseudo,queryEmail,queryPassword)
                    runOnUiThread {
                        progressBarRegister.visibility = View.VISIBLE
                        if (registerPage == ResponseCode.StatusCode.Created){
                            AlertDialog.Builder(this).apply {
                                setMessage("Inscription effectuée")
                            }.create().show()
                        }else if(registerPage == ResponseCode.StatusCode.BadRequest){
                            AlertDialog.Builder(this).apply {
                                setMessage("Erreur lors de l'inscription")
                            }.create().show()
                        }
                    }
                }catch (e : IOException){
                }
            }).start()
        }
    }
}