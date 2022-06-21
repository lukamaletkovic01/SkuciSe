package com.example.skucise.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.skucise.LayoutActivities.EditProductActivities.EditProductActivity
import com.example.skucise.LayoutActivities.ProductPageActivies.ProductPageActivity
import com.example.skucise.models.Advert
import com.example.skucise.R
import com.example.skucise.utils.Constrants
import com.squareup.picasso.Picasso
import java.util.ArrayList



class MyPropertyAdapter(dataholder: ArrayList<Advert>) :
    RecyclerView.Adapter<MyPropertyAdapter.MyPropertyViewHolder>() {
    var dataholder: ArrayList<Advert> = dataholder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPropertyViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(com.example.skucise.R.layout.item_my_property, parent, false)
        return MyPropertyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyPropertyViewHolder, position: Int) {
        val curretItem = dataholder[position]
        var firstImage : String

        if (!curretItem.advertImages!!.isNullOrEmpty()) {
            firstImage = curretItem.advertImages?.get(0)?.path.toString()
            Picasso.get().load(Constrants.BASE_URL + "/Images/AdvertImages/$firstImage")
                .into(holder.img)
        }

        holder.naslov.text = curretItem.name
        holder.adresa.text = curretItem.advertDetails!!.township
        holder.editButton.setOnClickListener(View.OnClickListener {
            val intent = Intent(holder.itemView.context, EditProductActivity::class.java)
            intent.putExtra("advertId", curretItem.id)
            holder.itemView.context.startActivity(intent)
        })

        holder.itemView.setOnClickListener(
            View.OnClickListener {
                var intent = Intent(holder.itemView.context, ProductPageActivity::class.java)
                intent.putExtra("advertId", curretItem.id)
                holder.itemView.context.startActivity(intent)

            }
        )

    }

    public override fun getItemCount(): Int {
        return dataholder.size
    }


    class MyPropertyViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var img: ImageView
        var editButton: ImageView
        var naslov: TextView
        var adresa: TextView

        init {
            img = itemView.findViewById(R.id.slika_oglasa)
            editButton = itemView.findViewById(R.id.editButton)
            naslov = itemView.findViewById(R.id.naslov_oglasa)
            adresa = itemView.findViewById(R.id.adresa_oglasa)

        }
    }

}