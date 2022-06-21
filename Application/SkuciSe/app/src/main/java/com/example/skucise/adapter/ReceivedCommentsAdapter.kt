package com.example.skucise.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.skucise.LayoutActivities.ProductPageActivies.ProductPageActivity
import com.example.skucise.R
import com.example.skucise.models.Advert
import com.example.skucise.models.Comment

class ReceivedCommentsAdapter(private var dataholder: List<Comment>) :
    RecyclerView.Adapter<ReceivedCommentsAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_received_comment, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = dataholder[position]
        val dateformat = (currentItem.datePublished.split("T")[0]).split("-")

        holder.content.text = currentItem.content
        holder.userFirstname.text = currentItem.user!!.firstname
        holder.advertName.text = currentItem.advert!!.name
        ("Ocena " + currentItem.rating.toString().split(".")[0]).also { holder.rating.text = it }
        (dateformat[2] + "." + dateformat[1] + "." + dateformat[0] + ".").also {
            holder.datePublished.text = it
        }

        holder.itemView.setOnClickListener(
            View.OnClickListener {
                var intent = Intent(holder.itemView.context, ProductPageActivity::class.java)
                intent.putExtra("advertId", currentItem.advertId)
                holder.itemView.context.startActivity(intent)

            }
        )
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
        var advertName: TextView


        init {
            content = itemView.findViewById(R.id.textRecenzije)
            userFirstname = itemView.findViewById(R.id.nazivOcenjivaca)
            datePublished = itemView.findViewById(R.id.vremeRecenzije)
            rating = itemView.findViewById(R.id.ocenaRecenzije)
            advertName = itemView.findViewById(R.id.nazivOglasa)
        }
    }
}