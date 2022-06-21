package com.example.skucise.adapter

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.skucise.LayoutActivities.BasicActivities.LoadingUtils
import com.example.skucise.models.Advert
import com.example.skucise.R
import com.example.skucise.models.HelperModels.Status
import com.example.skucise.models.Reservation
import com.example.skucise.repository.AdvertRepository
import com.example.skucise.repository.ReservationRepository
import com.example.skucise.utils.Constrants
import com.example.skucise.viewModels.NotificationViewModel
import com.example.skucise.viewModels.adverts.AdvertViewModel
import com.example.skucise.viewModels.adverts.AdvertViewModelFactory
import com.example.skucise.viewModels.reservations.ReservationViewModel
import com.example.skucise.viewModels.reservations.ReservationViewModelFactory
import com.squareup.picasso.Picasso
import java.time.LocalDateTime
import kotlin.collections.ArrayList

class SentReservationAdapter(
    var dataholder: ArrayList<Reservation>,
    var lifecycleOwner: LifecycleOwner?,
    var reservationViewModel: ReservationViewModel,
    var userId: Long?
) :
    RecyclerView.Adapter<SentReservationAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_sent_reservation, parent, false)
        return MyViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val animation: Animation =
            AnimationUtils.loadAnimation(holder.itemView.context, android.R.anim.slide_in_left)
        val currentItem = dataholder[position]
        setObservables()

        try {
            if (!currentItem.advert?.advertImages!!.isNullOrEmpty()) {
                var firstImage = currentItem.advert?.advertImages?.get(0)?.path.toString()
                Picasso.get().load(Constrants.BASE_URL + "/Images/AdvertImages/$firstImage")
                    .into(holder.img)
            }

            holder.name.text = currentItem.advert?.name
            holder.township.text = currentItem.advert?.advertDetails?.township
            (currentItem.advert?.price.toString() + "\u20ac").also { holder.price.text = it }
            ("Ponudjac: " + currentItem.advert?.user?.lastname + " " + currentItem.advert?.user?.firstname).also {
                holder.reservationFromName.text = it
            }
            ("Broj telefona: " + currentItem.advert?.user?.phoneNumber).also {
                holder.reservationFromPhoneNumber.text = it
            }
            ("Email: " + currentItem.advert?.user?.email).also {
                holder.reservationFromEmail.text = it
            }
            ("Datum rezgledanja: " + currentItem.date?.dayOfMonth + "." + currentItem.date?.monthOfYear + "." + currentItem.date?.year + ".").also {
                holder.date.text = it
            }
            (currentItem.date?.hourOfDay.toString() + "h").also { holder.time.text = it }

            holder.itemView.startAnimation(animation)
            holder.cancelReservation.setOnClickListener {
                currentItem.id?.let { reservationId -> reservationViewModel.cancelReservation(reservationId) }
            }

            if (currentItem.status!! == Status.Pending && (LocalDateTime.parse(currentItem.dateOfReservation) < LocalDateTime.now()))
                holder.cancelReservation.visibility = View.VISIBLE
            else
                holder.cancelReservation.visibility = View.GONE

        } catch (e: Exception) {
            Log.d("SentReservationAdapter", e.toString())
        }
    }

    private fun setObservables() {
        lifecycleOwner?.let {
            reservationViewModel.cancelReservationResponse.observe(it) { response ->
                if (response.isSuccessful) {
                    userId?.let { id -> reservationViewModel.getSentReservations(id, Status.Pending) }
                } else {
                    Log.d(
                        "SentReservationAdapter",
                        "Send reservation cancellation request error. ${response.errorBody()}"
                    )
                }
            }
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
        var cancelReservation: AppCompatButton

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
            cancelReservation = itemView.findViewById(R.id.cancelReservation)
        }
    }

    init {
        this.dataholder = dataholder
    }
}