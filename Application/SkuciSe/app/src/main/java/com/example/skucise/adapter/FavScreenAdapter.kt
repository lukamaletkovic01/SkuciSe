package com.example.skucise.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.skucise.LayoutActivities.ProductPageActivies.ProductPageActivity
import com.example.skucise.models.Advert
import com.example.skucise.R
import com.example.skucise.utils.Constrants
import com.example.skucise.viewModels.adverts.AdvertViewModel
import com.squareup.picasso.Picasso
import java.util.ArrayList



class FavScreenAdapter(dataholder: ArrayList<Advert>, id: String, advertViewModel: AdvertViewModel) :
    RecyclerView.Adapter<FavScreenAdapter.FavoritesViewHolder>() {
    var dataholder: ArrayList<Advert> = dataholder
    var newId : String = id
    var newAdvertViewmodel: AdvertViewModel = advertViewModel

    //var context : Context = context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(com.example.skucise.R.layout.item_favorites, parent, false)
        return FavoritesViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        val curretItem = dataholder[position]
        var firstImage : String
        if (!curretItem.advertImages!!.isNullOrEmpty()) {
            firstImage = curretItem.advertImages?.get(0)?.path.toString()
            Picasso.get().load(Constrants.BASE_URL + "/Images/AdvertImages/$firstImage")
                .into(holder.img)
        }
        holder.naslov.text = curretItem.name.toString()
        holder.adresa.text = curretItem.advertDetails?.city.toString()
        holder.buttonFav.setOnClickListener(View.OnClickListener {
            newAdvertViewmodel.postLike(newId.toLong(),dataholder[position].id!!)
            obrisiFavitem(holder.adapterPosition)
            notifyDataSetChanged()
        })

        holder.itemView.setOnClickListener(
            View.OnClickListener {
                var intent = Intent(holder.itemView.context, ProductPageActivity::class.java)
                intent.putExtra("advertId", curretItem.id)
                holder.itemView.context.startActivity(intent)

            }
        )

        /*holder.oglas.setOnClickListener(View.OnClickListener {
            var intent: Intent = Intent(this.context,ProductPageActivity::class.java)
            context.startActivity(intent)
        })*/

    }

    public override fun getItemCount(): Int {
        return dataholder.size
    }

    fun obrisiFavitem(adapterPosition: Int) {
        dataholder.removeAt(adapterPosition)
        notifyDataSetChanged()
    }

    class FavoritesViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var img: ImageView
        var naslov: TextView
        var adresa: TextView
        var buttonFav: ImageView
        var oglas: ConstraintLayout

        init {
            img = itemView.findViewById(R.id.slika_oglasa)
            naslov = itemView.findViewById(R.id.naslov_oglasa)
            adresa = itemView.findViewById(R.id.adresa_oglasa)
            buttonFav = itemView.findViewById(R.id.favButton)
            oglas = itemView.findViewById(R.id.oglas)

        }
    }

}