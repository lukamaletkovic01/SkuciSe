package com.example.skucise.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.skucise.R
import com.example.skucise.models.Reservation
import kotlin.collections.ArrayList

import com.example.skucise.utils.Constrants
import com.squareup.picasso.Picasso


class SentReservationsDefaultAdapter(
    var dataholder: ArrayList<Reservation>
) :
    RecyclerView.Adapter<SentReservationsDefaultAdapter.MyViewHolder>() {
    private var notificationTag = "NotificationLog"

    lateinit var view: View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        view =
            LayoutInflater.from(parent.context)
                .inflate(com.example.skucise.R.layout.item_default_reservation, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var animation: Animation =
            AnimationUtils.loadAnimation(holder.itemView.context, android.R.anim.slide_in_left)
        val currentItem = dataholder[position]

        if (!currentItem.advert?.advertImages!!.isNullOrEmpty()) {
            var firstImage = currentItem.advert?.advertImages?.get(0)?.path.toString()
            Picasso.get().load(Constrants.BASE_URL + "/Images/AdvertImages/$firstImage")
                .into(holder.img)
        }
        holder.name.text = currentItem.advert?.name
        holder.township.text = currentItem.advert?.advertDetails?.township
        holder.price.text = currentItem.advert?.price.toString() + "\u20ac"
        holder.reservationFromName.text = "Ponudjac: " + currentItem.advert?.user?.lastname + " " + currentItem.advert?.user?.firstname
        holder.reservationFromPhoneNumber.text = "Broj telefona: " + currentItem.advert?.user?.phoneNumber
        holder.reservationFromEmail.text = "Email: " + currentItem.advert?.user?.email
        holder.date.text = "Datum rezgledanja: " + currentItem.date?.dayOfMonth + "." + currentItem.date?.monthOfYear + "." + currentItem.date?.year + "."
        holder.time.text = currentItem.date?.hourOfDay.toString() + "h"



        holder.itemView.startAnimation(animation)


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
        var reservationFromName: TextView
        var reservationFromPhoneNumber: TextView
        var reservationFromEmail: TextView
        var date: TextView
        var time: TextView


        init {
            img = itemView.findViewById(R.id.title_image)
            name = itemView.findViewById(R.id.naslov_oglasa)
            township = itemView.findViewById(R.id.adresa_oglasa)
            price = itemView.findViewById(R.id.cena)
            reservationFromName = itemView.findViewById(R.id.narucilac)
            reservationFromPhoneNumber = itemView.findViewById(R.id.narucilac_brojTelefona)
            reservationFromEmail = itemView.findViewById(R.id.narucilac_email)
            date = itemView.findViewById(R.id.narucilac_datumPorudzbine)
            time = itemView.findViewById(R.id.narucilac_satnicaZakazivanja)
        }
    }


}