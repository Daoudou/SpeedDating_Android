package fr.daoudou.speeddating.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.daoudou.speeddating.Info.DateInfo
import fr.daoudou.speeddating.R
import fr.daoudou.speeddating.View.ListDateViewHolder
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ListDateAdapter(private val context: Context,
                      private val dataSource: ArrayList<DateInfo>
                    ) : RecyclerView.Adapter<ListDateViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListDateViewHolder {
        return ListDateViewHolder(LayoutInflater.from(context).inflate(R.layout.date_list_main,parent,false))
    }

    override fun onBindViewHolder(holder: ListDateViewHolder, position: Int) {

        val listDate = dataSource[position]

        holder.textViewDateList.text = listDate.date

    }

    override fun getItemCount(): Int {
        return dataSource.size
    }

}