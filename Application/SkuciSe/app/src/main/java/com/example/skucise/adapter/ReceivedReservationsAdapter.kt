package com.example.skucise.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.example.skucise.R
import com.example.skucise.models.Reservation
import kotlin.collections.ArrayList

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import com.example.skucise.LayoutActivities.BasicActivities.LoadingUtils
import com.example.skucise.models.HelperModels.ReservationMessage
import com.example.skucise.utils.Constrants
import com.example.skucise.viewModels.NotificationViewModel
import com.squareup.picasso.Picasso


class ReceivedReservationsAdapter(
    var dataholder: ArrayList<Reservation>,
    var lifecycleOwner: LifecycleOwner?,
    var notificationViewModel: NotificationViewModel,
    var userId: Long?
) :
    RecyclerView.Adapter<ReceivedReservationsAdapter.MyViewHolder>() {
    private var notificationTag = "NotificationLog"

    lateinit var view: View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        view =
            LayoutInflater.from(parent.context)
                .inflate(com.example.skucise.R.layout.item_received_reservation, parent, false)

        sendReservationMessageObservable()

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
        holder.reservationFromName.text =
            "Narucilac: " + currentItem.user?.lastname + " " + currentItem.user?.firstname
        holder.reservationFromPhoneNumber.text = "Broj telefona: " + currentItem.user?.phoneNumber
        holder.reservationFromEmail.text = "Email: " + currentItem.user?.email
        holder.date.text =
            "Datum rezgledanja: " + currentItem.date?.dayOfMonth + "." + currentItem.date?.monthOfYear + "." + currentItem.date?.year + "."
        holder.time.text = currentItem.date?.hourOfDay.toString() + "h"

        holder.acceptReservation.setOnClickListener {
            val reservationMessage = ReservationMessage(
                senderId = userId,
                receiverId = currentItem.user?.id,
                advertId = currentItem.advert?.id,
                title = "Potvrda razgledanja",
                flag = 1,
                answer = 2
            )

            notificationViewModel.sendReservationMessage(reservationMessage)
        }

        holder.declineReservation.setOnClickListener {
            val reservationMessage = ReservationMessage(
                senderId = userId,
                receiverId = currentItem.user?.id,
                advertId = currentItem.advert?.id,
                title = "Odbijanje razgledanja",
                flag = 1,
                answer = 1
            )

            notificationViewModel.sendReservationMessage(reservationMessage)
        }
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
        var acceptReservation: AppCompatButton
        var declineReservation: AppCompatButton

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
            acceptReservation = itemView.findViewById(R.id.potvrdiRezervaciju)
            declineReservation = itemView.findViewById(R.id.otkaziRezervaciju)
        }
    }

    private fun sendReservationMessageObservable() {
        lifecycleOwner?.let {
            notificationViewModel.sendReservationMessageResponse.observe(it) { response ->
                if (response.isSuccessful) {
                    Log.d(notificationTag, response.body().toString())

                } else {
                    Log.d(
                        notificationTag,
                        "Send reservation message request error. ${response.errorBody()}"
                    )
                }
                LoadingUtils.hideDialog()
            }
        }
    }

}