package fr.daoudou.speeddating.View

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.daoudou.speeddating.R

class ListDateViewHolder(row: View) : RecyclerView.ViewHolder(row) {

    val textViewDateList = row.findViewById<TextView>(R.id.textDateList)
    val buttonUpdate = row.findViewById<ImageButton>(R.id.imageButtonUpdate)
    val buttonDelete = row.findViewById<ImageButton>(R.id.imageButtonDelete)


}