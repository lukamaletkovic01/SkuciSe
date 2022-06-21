package com.example.skucise.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.skucise.R
import com.example.skucise.models.Category
import java.util.ArrayList



class HomeScreenCategoryAdapter(dataholder: ArrayList<Category>) :
    RecyclerView.Adapter<HomeScreenCategoryAdapter.myviewholderCategory>() {
    var dataholder: ArrayList<Category>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myviewholderCategory {
        val view: View =
            LayoutInflater.from(parent.context).inflate(com.example.skucise.R.layout.item_category, parent, false)
        return myviewholderCategory(view)
    }

    override fun onBindViewHolder(holder: myviewholderCategory, position: Int) {
        val curretItem = dataholder[position]
        holder.img.setImageResource(curretItem.slika)
        holder.naslov.text = curretItem.naslov.toString()
    }

    override fun getItemCount(): Int {
        return dataholder.size
    }

    class myviewholderCategory(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var img: ImageView
        var naslov: TextView

        init {
            img = itemView.findViewById(R.id.slika_kategorije)
            naslov = itemView.findViewById(R.id.naslov_kategorije)
        }
    }

    init {
        this.dataholder = dataholder
    }
}