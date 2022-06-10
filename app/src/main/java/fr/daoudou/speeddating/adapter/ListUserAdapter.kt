package fr.daoudou.speeddating.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.daoudou.speeddating.Info.UserInfo
import fr.daoudou.speeddating.R
import fr.daoudou.speeddating.View.ListUserViewHolder

class ListUserAdapter (private val context: Context,
                       private val dataSource : ArrayList<UserInfo>
) : RecyclerView.Adapter<ListUserViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListUserViewHolder {
        return ListUserViewHolder(LayoutInflater.from(context).inflate(R.layout.listrow_user_main, parent,false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ListUserViewHolder, position: Int) {
        val listUser = dataSource[position]

    holder.textViewAllUser.text = "Utilisateur : " + listUser.pseudo + "\n" + "Email : " + listUser.email

    }

    override fun getItemCount(): Int {
        return dataSource.size
    }


}