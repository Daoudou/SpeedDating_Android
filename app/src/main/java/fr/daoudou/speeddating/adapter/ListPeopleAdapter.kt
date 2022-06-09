package fr.daoudou.speeddating.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import fr.daoudou.speeddating.Info.PeopleInfos
import fr.daoudou.speeddating.R
import fr.daoudou.speeddating.Security.ResponseCode
import fr.daoudou.speeddating.Service.PeopleService
import fr.daoudou.speeddating.View.ListPeopleViewHolder
import fr.daoudou.speeddating.main.PeopleListActivity
import java.io.IOException

class ListPeopleAdapter (private val context: Context,
                         private val dataSource : ArrayList<PeopleInfos>
                         ) : RecyclerView.Adapter<ListPeopleViewHolder>() {

    private val svc = PeopleService()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListPeopleViewHolder {
        return ListPeopleViewHolder(LayoutInflater.from(context).inflate(R.layout.listrow_people,parent,false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ListPeopleViewHolder, position: Int) {
        val listPeople = dataSource[position]
        holder.textViePeopleList.text = listPeople.lastName + " " + listPeople.firstName

        holder.buttonDeletePeople.setOnClickListener {
                val deletePeople = svc.deleteUserInfos(listPeople.id)
                if (deletePeople == ResponseCode.StatusCode.OK){
                    AlertDialog.Builder(context).apply{
                        setTitle("Suppression d'une personne")
                        setMessage("Personne supprimer avec succees")
                        setPositiveButton("D'accord",
                            DialogInterface.OnClickListener { dialog, which ->
                            Toast.makeText(context,"Suppression effectuer", Toast.LENGTH_SHORT).show()
                            dialog.dismiss()
                            val intent = Intent(context,PeopleListActivity::class.java)
                                context.startActivity(intent)
                        })
                    }.create().show()
                } else if (deletePeople == ResponseCode.StatusCode.BadRequest){
                    AlertDialog.Builder(context).apply{
                        setTitle("Suppression d'une personne")
                        setMessage("Erreur lors de la suppression d'une personne supprimer d'abord la rencontre lier a cette personne'")
                        setPositiveButton("D'accord",DialogInterface.OnClickListener { dialog, which ->
                            Toast.makeText(context,"Suppression non effectuer",Toast.LENGTH_SHORT).show()
                            dialog.dismiss()
                        })
                    }.create().show()
                }
            notifyDataSetChanged()
        }

    }
    override fun getItemCount(): Int {
        return dataSource.size
    }


}

