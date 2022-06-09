package fr.daoudou.speeddating.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.daoudou.speeddating.Info.DateInfo
import fr.daoudou.speeddating.Info.UserInfo
import fr.daoudou.speeddating.R
import fr.daoudou.speeddating.View.ListDateViewHolder
import fr.daoudou.speeddating.View.ProfilViewHolder

class ProfilAdapter(private val context: Context,
    private val dataSource: ArrayList<UserInfo>
    ) : RecyclerView.Adapter<ProfilViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfilViewHolder {
        return ProfilViewHolder(LayoutInflater.from(context).inflate(R.layout.listrow_profil,parent,false))
    }

    override fun onBindViewHolder(holder: ProfilViewHolder, position: Int) {

        val listProfil = dataSource[position]
       holder.textViewProfil.text = "Pseudo : " + listProfil.pseudo + "\n" + "\n" + "Email : " + listProfil.email
    }

    override fun getItemCount(): Int {
        return dataSource.size
    }


}