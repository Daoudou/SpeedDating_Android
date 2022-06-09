package fr.daoudou.speeddating.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.daoudou.speeddating.Info.PeopleInfos
import fr.daoudou.speeddating.R
import fr.daoudou.speeddating.View.ListPeopleViewHolder

class ListPeopleAdapter (private val context: Context,
                         private val dataSource : ArrayList<PeopleInfos>
                         ) : RecyclerView.Adapter<ListPeopleViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListPeopleViewHolder {
        return ListPeopleViewHolder(LayoutInflater.from(context).inflate(R.layout.listrow_people,parent,false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ListPeopleViewHolder, position: Int) {
        val listPeople = dataSource[position]
        holder.textViePeopleList.text = listPeople.lastName + " " + listPeople.firstName
    }

    override fun getItemCount(): Int {
        return dataSource.size
    }


}

