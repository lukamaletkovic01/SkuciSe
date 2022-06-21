package com.example.skucise.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.skucise.R
import com.example.skucise.models.Review
import java.util.ArrayList



class ProductScreenReviewAdapter(dataholder: ArrayList<Review>) :
    RecyclerView.Adapter<ProductScreenReviewAdapter.myviewholderReview>() {
    var dataholder: ArrayList<Review>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myviewholderReview {
        val view: View =
            LayoutInflater.from(parent.context).inflate(com.example.skucise.R.layout.item_raview, parent, false)
        return myviewholderReview(view)
    }

    override fun onBindViewHolder(holder: myviewholderReview, position: Int) {
        val curretItem = dataholder[position]
        holder.nazivOcenjivaca.text = curretItem.nazivOcenjivaca.toString()
        holder.sadrzajReview.text = curretItem.sadrzajReview.toString()
    }

    override fun getItemCount(): Int {
        return dataholder.size
    }

    class myviewholderReview(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var nazivOcenjivaca: TextView = itemView.findViewById(R.id.nazivOcenjivaca)
        var sadrzajReview: TextView = itemView.findViewById(R.id.textRecenzije)

    }

    init {
        this.dataholder = dataholder
    }
}