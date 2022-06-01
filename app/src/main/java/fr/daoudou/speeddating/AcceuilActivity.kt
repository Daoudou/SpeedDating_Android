package fr.daoudou.speeddating

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import fr.daoudou.speeddating.Info.UserInfo
import fr.daoudou.speeddating.Service.UserService

class AcceuilActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private val NEW_SPINNER_ID = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acceuil_page)
        val svc = UserService()
        val listUser = findViewById<ListView>(R.id.listViewAll)
        val progressBar = findViewById<ProgressBar>(R.id.ProgressBarQuery)
        Thread(Runnable{
            runOnUiThread{
                progressBar.visibility = View.INVISIBLE
                listUser.visibility = View.INVISIBLE
            }
            val result = svc.getAllUser()
            runOnUiThread {
                listUser.adapter = ArrayAdapter<UserInfo>(applicationContext,
                    android.R.layout.simple_list_item_1,
                    android.R.id.text1,
                    result)
                progressBar.visibility = View.VISIBLE
                listUser.visibility = View.VISIBLE
            }
        }).start()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater : MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent = Intent(this,ProfilActivity::class.java)
        val intentDate = Intent(this,DateActivity::class.java)

        val spinner = Spinner(this)
        spinner.id = NEW_SPINNER_ID

        when(item.itemId){
            R.id.profilMenu -> startActivity(intent)
            R.id.addDateMenu -> startActivity(intentDate)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemSelected(p0: AdapterView<*>?, view: View?, p2: Int, p3: Long) {
        TODO("Not yet implemented")
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

}