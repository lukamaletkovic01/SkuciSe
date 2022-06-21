package com.example.skucise.LayoutActivities.RatePropertyActivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.skucise.LayoutActivities.BasicActivities.LoadingUtils
import com.example.skucise.LayoutActivities.HomeScreenActivities.HomeScreenActivity
import com.example.skucise.LayoutActivities.LoginActivities.SessionManager
import com.example.skucise.LayoutActivities.NewRealEstate.NewRealEstatePicturesAddActivity
import com.example.skucise.LayoutActivities.ProductPageActivies.ProductPageActivity
import com.example.skucise.R
import com.example.skucise.models.Comment
import com.example.skucise.models.Reservation
import com.example.skucise.repository.AdvertRepository
import com.example.skucise.repository.LoginRepository
import com.example.skucise.repository.ReservationRepository
import com.example.skucise.services.SkuciSeFirebaseMessagingService.Companion.TAG
import com.example.skucise.viewModels.LoginViewModel
import com.example.skucise.viewModels.LoginViewModelFactory
import com.example.skucise.viewModels.adverts.AdvertViewModel
import com.example.skucise.viewModels.adverts.AdvertViewModelFactory
import com.example.skucise.viewModels.reservations.ReservationViewModel
import com.example.skucise.viewModels.reservations.ReservationViewModelFactory
import com.google.android.material.textfield.TextInputLayout
import com.hsalf.smilerating.BaseRating
import com.hsalf.smilerating.SmileRating
import com.hsalf.smileyrating.SmileyRating
import com.hsalf.smileyrating.SmileyRating.OnSmileySelectedListener
import org.joda.time.DateTime


class RatePropertyActivity : AppCompatActivity() {
    private lateinit var reservationViewModel: ReservationViewModel
    private lateinit var smileRating: SmileRating
    private lateinit var commentLayout: TextInputLayout
    private lateinit var comment: EditText
    private lateinit var dataholderReservation: Reservation

    private var rating: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rate_property)
        init()
    }

    private fun init() {
        setDataholderReservation()
        initSmileyRating()
        setObservables()

        commentLayout = findViewById(R.id.add_new_commentLayout)
        comment = findViewById(R.id.add_new_comment)
    }

    private fun setObservables() {
        val reservationRepository = ReservationRepository()
        val reservationViewModelFactory = ReservationViewModelFactory(reservationRepository)

        reservationViewModel =
            ViewModelProvider(
                this,
                reservationViewModelFactory
            ).get(ReservationViewModel::class.java)

        reservationViewModel.leaveCommentForReservationResponse.observe(this, { response ->
            if (response.isSuccessful) {
                val intent = Intent(this, CommentSentActivity::class.java)
                intent.putExtra("advertId", dataholderReservation.advertId)

                startActivity(intent)
            } else {
                Log.d("leaveCommentForReservationResponse", response.errorBody().toString())
            }
        })
    }

    private fun setDataholderReservation() {
        dataholderReservation = intent.getSerializableExtra("reservation") as Reservation
    }

    fun sendReview(view: View) {
        var nonValue1st: Long = -1
        var nonValue2st: Long = 0

        var smiley = smileRating.selectedSmile
        rating = smiley.toLong() + 1

        var checkComment = checkComment()

        //comment.text je komentar
        //rating je ocena od 1 do 5

        if (checkComment) {
            val comment: String = findViewById<EditText>(R.id.add_new_comment).text.toString()
            if (rating != nonValue1st && rating != nonValue2st) {
                try {
                    dataholderReservation.comment = Comment(
                        id = 0,
                        content = comment,
                        rating = rating.toDouble(),
                        datePublished = DateTime.now().toString(),
                        userId = dataholderReservation.userId!!,
                        advertId = dataholderReservation.advertId!!,
                        reservationId = dataholderReservation.id!!
                    )

                    reservationViewModel.leaveCommentForReservation(dataholderReservation)
                } catch (e: Exception){
                    Log.d("leaveCommentForReservation", "Error while leaving comment. $e")
                }
            } else {
                val myToast = Toast.makeText(
                    applicationContext,
                    "Dajte ocenu nekretnine !",
                    Toast.LENGTH_SHORT
                )
                myToast.setGravity(Gravity.BOTTOM, 200, 200)
                myToast.show()
            }
        }

    }

    private fun initSmileyRating() {
        smileRating = findViewById(R.id.smile_rating)

        /*OVDE SETUJEM CUSTOM VREDNOSTI ZA TEKST ISPOD SMAJLIJA*/
        smileRating.setNameForSmile(BaseRating.TERRIBLE, "Uzasno")
        smileRating.setNameForSmile(BaseRating.BAD, "Lose")
        smileRating.setNameForSmile(BaseRating.OKAY, "Ok")
        smileRating.setNameForSmile(BaseRating.GOOD, "Dobro")
        smileRating.setNameForSmile(BaseRating.GREAT, "Odlicno")
    }

    private fun checkComment(): Boolean {
        var comment: String = findViewById<EditText>(R.id.add_new_comment).text.toString()

        if (comment.isEmpty()) {
            commentLayout.error = "Polje ne sme biti prazno"
            return false
        } else {
            commentLayout.error = null
            return true
        }
    }

}