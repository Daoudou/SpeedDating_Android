package fr.daoudou.speeddating.View

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.daoudou.speeddating.R

class ListPeopleViewHolder(row: View) : RecyclerView.ViewHolder(row) {
    val textViePeopleList = row.findViewById<TextView>(R.id.textPeopleList)
    val buttonDeletePeople = row.findViewById<ImageButton>(R.id.imageButtonDeletePeople)
}