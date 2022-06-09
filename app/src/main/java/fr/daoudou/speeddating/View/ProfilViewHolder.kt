package fr.daoudou.speeddating.View

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.daoudou.speeddating.R

class ProfilViewHolder(row : View) : RecyclerView.ViewHolder(row) {

    val textViewProfil = row.findViewById<TextView>(R.id.textViewProfil)

}