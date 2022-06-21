package com.example.skucise.adapter

import android.annotation.SuppressLint
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.skucise.models.HelperModels.DateModel
import com.example.skucise.utils.Constrants
import com.squareup.picasso.Picasso
import java.util.ArrayList
import android.graphics.Color
import androidx.cardview.widget.CardView
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.R

class ReservationDateAdapter(dataholder: ArrayList<DateModel>) :
    RecyclerView.Adapter<ReservationDateAdapter.MyViewHolder>() {
    var dataholder: ArrayList<DateModel> = dataholder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(com.example.skucise.R.layout.item_date, parent, false)
        return MyViewHolder(view)
    }

    @SuppressLint("SetTextI18n", "ClickableViewAccessibility")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = dataholder[position]
        holder.day.text = currentItem.dayName
        holder.date.text = currentItem.dayDate.toString() +"."+currentItem.dayMonth.toString()+"."
    }

    override fun getItemCount(): Int {
        return dataholder.size
    }

    class MyViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var day: TextView = itemView.findViewById(com.example.skucise.R.id.dan)
        var date: TextView = itemView.findViewById(com.example.skucise.R.id.datum)
    }

}