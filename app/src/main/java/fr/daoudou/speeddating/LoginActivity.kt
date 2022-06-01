package fr.daoudou.speeddating

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import fr.daoudou.speeddating.Service.UserService
import java.io.IOException

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_page)
        val svcLogin = UserService()

        findViewById<Button>(R.id.buttonLoginPage).setOnClickListener {
            val progressBarlogin = findViewById<ProgressBar>(R.id.progressBarLogin)
            val queryEmail = findViewById<EditText>(R.id.editTextLoginEmail).text.toString()
            val queryPassword = findViewById<EditText>(R.id.editTextLoginPassword).text.toString()
            Thread(Runnable {
                runOnUiThread {
                    progressBarlogin.visibility = View.INVISIBLE
                }
                try {
                    val loginPage = svcLogin.loginUser(queryEmail,queryPassword)
                    runOnUiThread {
                        progressBarlogin.visibility = View.VISIBLE
                        if (loginPage == ResponseCode.StatusCode.OK ){
                            AlertDialog.Builder(this).apply {
                                setTitle("Connexion effectuée")
                                setMessage("Direction la page d'acceuil")
                                setPositiveButton("Ok",DialogInterface.OnClickListener{
                                    dialog,which->
                                    Toast.makeText(this@LoginActivity,
                                    "Chargement de la page d'acceuil",
                                    Toast.LENGTH_SHORT
                                    ).show()
                                    dialog.dismiss()
                                    val intent = Intent(this@LoginActivity,AcceuilActivity::class.java)
                                    startActivity(intent)
                                })
                            }.create().show()
                        }else if(loginPage == ResponseCode.StatusCode.BadRequest){
                            val dialogError = AlertDialog.Builder(this).apply {
                                setTitle("Connexion impossible.")
                                setMessage("Merci de vérifier les informations renseignées ")
                                setPositiveButton("Retry",DialogInterface.OnClickListener{
                                    dialog,which->
                                    val intent = Intent(this@LoginActivity,LoginActivity::class.java)
                                    startActivity(intent)
                                })
                            }.create().show()
                        }
                    }
                }catch (e :IOException){
                }
            }).start()
        }
    }
}