package com.example.skucise.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.skucise.R
import com.example.skucise.models.HelperModels.DateModel
import com.example.skucise.models.HelperModels.HoursModel
import com.example.skucise.utils.Constrants
import com.squareup.picasso.Picasso
import java.util.ArrayList

class ReservationHoursAdapter(dataholder: ArrayList<HoursModel>) :
    RecyclerView.Adapter<ReservationHoursAdapter.MyViewHolder>() {
    var dataholder: ArrayList<HoursModel> = dataholder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(com.example.skucise.R.layout.item_hour, parent, false)
        return MyViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = dataholder[position]

        holder.hours.text = currentItem.hourFirst.toString() + " - " + currentItem.hourLast.toString()
        holder.daytime.text = currentItem.dayTime
    }

    override fun getItemCount(): Int {
        return dataholder.size
    }

    class MyViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var hours: TextView = itemView.findViewById(R.id.hours)
        var daytime: TextView = itemView.findViewById(R.id.dayTime)
    }

}