package com.example.skucise.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.skucise.R
import com.example.skucise.models.Advert
import com.example.skucise.models.Comment

class CommentsAdapter(private var dataholder: List<Comment>) :
    RecyclerView.Adapter<CommentsAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_raview, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = dataholder[position]
        val dateformat = (currentItem.datePublished.split("T")[0]).split("-")

        holder.content.text = currentItem.content
        holder.userFirstname.text = currentItem.user!!.firstname
        ("Ocena " + currentItem.rating.toString().split(".")[0]).also { holder.rating.text = it }
        (dateformat[2] + "." + dateformat[1] + "." + dateformat[0] + ".").also {
            holder.datePublished.text = it
        }
    }

    override fun getItemCount(): Int {
        return dataholder.size
    }

    class MyViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var content: TextView
        var userFirstname: TextView
        var datePublished: TextView
        var rating: TextView

        init {
            content = itemView.findViewById(R.id.textRecenzije)
            userFirstname = itemView.findViewById(R.id.nazivOcenjivaca)
            datePublished = itemView.findViewById(R.id.vremeRecenzije)
            rating = itemView.findViewById(R.id.ocenaRecenzije)
        }
    }
}