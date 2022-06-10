package fr.daoudou.speeddating.View

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.daoudou.speeddating.R

class ListUserViewHolder(row: View) : RecyclerView.ViewHolder(row) {

    val textViewAllUser = row.findViewById<TextView>(R.id.textViewAllUser)
    val imageView = row.findViewById<ImageView>(R.id.imageViewAllUser)

}