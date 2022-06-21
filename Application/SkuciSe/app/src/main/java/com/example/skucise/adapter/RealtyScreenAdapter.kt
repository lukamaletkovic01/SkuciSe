package com.example.skucise.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.skucise.LayoutActivities.ProductPageActivies.ProductPageActivity
import com.example.skucise.models.Advert
import com.example.skucise.R
import com.example.skucise.utils.Constrants
import com.squareup.picasso.Picasso
import java.util.ArrayList


class RealtyScreenAdapter(dataholder: ArrayList<Advert>) :
    RecyclerView.Adapter<RealtyScreenAdapter.MyViewHolder>() {
    var dataholder: ArrayList<Advert> = dataholder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(com.example.skucise.R.layout.item_nearby, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = dataholder[position]
        var animation: Animation = AnimationUtils.loadAnimation(holder.itemView.context,R.anim.fall_down)

        var firstImage : String
        if (!currentItem.advertImages!!.isNullOrEmpty()) {
            firstImage = currentItem.advertImages?.get(0)?.path.toString()
            Picasso.get().load(Constrants.BASE_URL + "/Images/AdvertImages/$firstImage")
                .into(holder.img)
        }
        holder.name.text = currentItem.name
        holder.township.text = currentItem.advertDetails!!.township
        holder.price.text = currentItem.price.toString()+ "\u20ac"
        holder.ocena.text = if(currentItem.advertAverageRating != 0.0) currentItem.advertAverageRating.toString() else "N/A"
        holder.advertType.text = currentItem.advertType!!.advertTypeName
        holder.itemView.startAnimation(animation)
        holder.img.setOnClickListener(
            View.OnClickListener {
                var intent = Intent(holder.itemView.context, ProductPageActivity::class.java)
                intent.putExtra("advertId", currentItem.id)
                holder.itemView.context.startActivity(intent)

            }
        )
    }

    override fun getItemCount(): Int {
        return dataholder.size
    }

    class MyViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var img: ImageButton
        var name: TextView
        var township: TextView
        var price: TextView
        var advertType: TextView
        var ocena: TextView // add to db

        init {
            img = itemView.findViewById(R.id.title_image)
            name = itemView.findViewById(R.id.naslov_oglasa)
            township = itemView.findViewById(R.id.adresa_oglasa)
            price = itemView.findViewById(R.id.cena)
            ocena = itemView.findViewById(R.id.ocena)
            advertType = itemView.findViewById(R.id.tipOglasa)
        }
    }

}