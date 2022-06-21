package com.example.skucise.LayoutActivities.ReservationActivities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.children
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.skucise.LayoutActivities.BasicActivities.LoadingUtils
import com.example.skucise.LayoutActivities.BasicActivities.RecyclerItemClickListener
import com.example.skucise.LayoutActivities.BasicActivities.ReservationDialogActivity
import com.example.skucise.LayoutActivities.LoginActivities.SessionManager
import com.example.skucise.R
import com.example.skucise.adapter.ReservationDateAdapter
import com.example.skucise.adapter.ReservationHoursAdapter
import com.example.skucise.models.HelperModels.DateModel
import com.example.skucise.models.HelperModels.HoursModel
import com.example.skucise.models.HelperModels.ReservationMessage
import com.example.skucise.models.User
import com.example.skucise.repository.NotificationRepository
import com.example.skucise.repository.ReservationRepository
import com.example.skucise.repository.UserRepository
import com.example.skucise.viewModels.NotificationViewModel
import com.example.skucise.viewModels.NotificationViewModelFactory
import com.example.skucise.viewModels.UserViewModel
import com.example.skucise.viewModels.UserViewModelFactory
import com.example.skucise.viewModels.reservations.ReservationViewModel
import com.example.skucise.viewModels.reservations.ReservationViewModelFactory
import com.google.android.material.textfield.TextInputLayout
import org.joda.time.DateTime
import org.joda.time.LocalDateTime
import java.util.*
import java.util.regex.Pattern


class ReservationActivity : AppCompatActivity() {
    private var notificationTag = "NotificationLog"

    private lateinit var recyclerViewDate: RecyclerView
    private lateinit var recyclerViewHour: RecyclerView
    private lateinit var zakaziRezervation: AppCompatButton
    private lateinit var dataholderDateModel: ArrayList<DateModel>
    private lateinit var dataholderHourModel: ArrayList<HoursModel>

    private lateinit var notificationViewModel: NotificationViewModel
    private lateinit var reservationViewModel: ReservationViewModel

    private lateinit var userViewModel: UserViewModel
    private var userId: Long? = 0
    private var user: User? = null

    private var advertId: Long? = 0
    private var ownerId: Long? = 0

    private var date: String = ""
    private var hour: String = ""
    private lateinit var reservedTerms : MutableList<LocalDateTime>

    lateinit var phonenumberLayout : TextInputLayout
    lateinit var emailLayout : TextInputLayout
    lateinit var nameLayout : TextInputLayout
    lateinit var emailEditText : EditText
    private val PHONE_NUMBER : Pattern =
        Pattern.compile("^06[0-69][0-9]{6,7}$")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.skucise.R.layout.activity_reservation)

        zakaziRezervation = findViewById(R.id.zakaziReservation)

        emailEditText = findViewById<EditText>(R.id.emailReservation)
        emailLayout = findViewById<TextInputLayout>(R.id.lastnameLayout)
        nameLayout = findViewById<TextInputLayout>(R.id.firstnameLayout)
        phonenumberLayout = findViewById<TextInputLayout>(R.id.phoneNumberLayout)


        addOnClickListenerForReservationButton()
        sendReservationMessageObservable()
        addDateRecyclerView()
        addUserInfo()

        val reservationRepository = ReservationRepository()
        val reservationViewModelFactory = ReservationViewModelFactory(reservationRepository)
        reservationViewModel = ViewModelProvider(this, reservationViewModelFactory).get(ReservationViewModel::class.java)

        reservationViewModel.reservedTerms.observe(this, Observer { response ->
            if (response.isSuccessful) {
                formTerms(response.body() as List<String>)
                addHoursRecyclerView()

            } else {
                Log.d("GRESKA", "Nije uspelo")
            }

        })
    }

    private fun formTerms(list: List<String>) {
        reservedTerms = ArrayList();
        for (dt in list){
            Log.d("ALO", dt)
            var localDateTime: LocalDateTime = LocalDateTime.parse(dt)
            reservedTerms.add(localDateTime)

        }

    }

    private fun addUserInfo() {
        setIdentities()

        val userRepository = UserRepository()
        val userViewModelFactory = UserViewModelFactory(userRepository)
        userViewModel = ViewModelProvider(this, userViewModelFactory).get(UserViewModel::class.java)

        if(userId != null) {
            userViewModel.getUserInfo(userId!!)

            userViewModel.user.observe(this, Observer { response ->
                if (response.isSuccessful) {
                    user = response.body()!!

                    findViewById<EditText>(R.id.firstnameLastnameReservation).setText(user!!.firstname + " " + user!!.lastname)
                    findViewById<EditText>(R.id.phoneNumberReservation).setText(user!!.phoneNumber)
                    findViewById<EditText>(R.id.emailReservation).setText(user!!.email)

                    ownerId?.let { reservationViewModel.getReservedTerms(it) }
                    Log.d("USER", response.body().toString())
                } else {
                    Log.d("GRESKA", "Nije uspelo")
                }

            })
        }
    }

    private fun setIdentities() {
        userId = (getHashMapFromSession()[SessionManager.KEY_ID])?.toLong()
        advertId = intent.getSerializableExtra("advertId") as Long
        ownerId = intent.getSerializableExtra("ownerId") as Long
    }

    private fun getHashMapFromSession(): HashMap<String, String> {
        val session = SessionManager(this)
        return session.getUserDetails()
    }

    private fun addDateRecyclerView() {
        recyclerViewDate = findViewById(R.id.recyclerViewDate)
        recyclerViewDate.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewDate.isNestedScrollingEnabled = false;

        dataholderDateModel = java.util.ArrayList()
        dataholderDateModel = createDate()
        recyclerViewDate.adapter = ReservationDateAdapter(dataholderDateModel)
        addOnTouchListenerForDates()
    }

    private fun createDate(): ArrayList<DateModel> {
        var dayNumber: Int
        var dayName: String
        var dayDate: Int
        var dayMonth: Int

        for (i in 1..7) {
            dayNumber = DateTime.now().plusDays(i).toDate().day
            dayDate = DateTime.now().plusDays(i).toDate().date
            dayMonth = DateTime.now().plusDays(i).toDate().month+1

            dayName = when (dayNumber) {
                1 -> "PON"
                2 -> "UTO"
                3 -> "SRE"
                4 -> "CET"
                5 -> "PET"
                6 -> "SUB"
                else -> "NED"
            }

            dataholderDateModel.add(DateModel(dayName, dayDate.toLong(), dayMonth.toLong()))

        }


        return dataholderDateModel
    }

    private fun addHoursRecyclerView() {
        recyclerViewHour = findViewById(R.id.recyclerViewSat)
        recyclerViewHour.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewHour.isNestedScrollingEnabled = false;

        dataholderHourModel = java.util.ArrayList()
        var naslovSat : TextView = findViewById(R.id.naslovSat)
        //dataholderHourModel = createHours(Calendar.getInstance().get(Calendar.YEAR).toString() + "-" + DateTime.now().plusDays(1).monthOfYear().toString() + "-" + DateTime.now().plusDays(1).dayOfMonth().toString())
        recyclerViewHour.adapter = ReservationHoursAdapter(dataholderHourModel)
        addOnTouchListenerForHours()
    }

    private fun createHours(date: String): ArrayList<HoursModel> {

        dataholderHourModel.clear()
        var ldt : LocalDateTime = LocalDateTime.parse(date)


        var hour = 13
        var flag : Boolean
        if (date!=""){
            while(hour < 20){
                flag = true
                for(term in reservedTerms){
                    if (term.dayOfMonth.equals(ldt.dayOfMonth) && term.year.equals(ldt.year) && term.monthOfYear.equals(ldt.monthOfYear) && term.hourOfDay == hour){
                        flag = false
                    }
                }
                if (flag) {
                    dataholderHourModel.add(HoursModel("$hour:00", (hour+1).toString()+":00", "P.M."))
                }
                hour += 1
            }
        }

        recyclerViewHour.adapter = ReservationHoursAdapter(dataholderHourModel)
        var naslovSat : TextView = findViewById(R.id.naslovSat)
        if (dataholderHourModel.isEmpty())
            naslovSat.text = "Nema termina za izabrani datum"
        else
            naslovSat.text = "Izaberite vreme"

        return dataholderHourModel
    }

    private fun addOnClickListenerForReservationButton() {
            zakaziRezervation.setOnClickListener {
                if (userId != null && ownerId != null && advertId != null) {
                    var emailCheck = validateEmail()
                    var nameCheck = checkName()
                    var phoneCheck = checkPhoneNumber()
                    if (emailCheck && nameCheck && phoneCheck) {
                        if (hour != "" && date != "") {
                            LoadingUtils.showDialog(this, false)
                            val reservationMessage = ReservationMessage(
                                senderId = userId,
                                receiverId = ownerId,
                                advertId = advertId,
                                title = "Zahtev za rezervaciju",
                                dateOfReservation = date + hour,
                                flag = 0
                            )

                            notificationViewModel.sendReservationMessage(reservationMessage)
                        } else {
                            val myToast = Toast.makeText(
                                applicationContext,
                                "Izaberite datum i vreme! ",
                                Toast.LENGTH_SHORT
                            )
                            myToast.setGravity(Gravity.BOTTOM, 200, 200)
                            myToast.show()
                        }
                    }
                } else
                    Log.d(
                        notificationTag,
                        "Send reservation message request error. Parameters null"
                    )
            }
    }

    private fun sendReservationMessageObservable() {
        val notificationRepository = NotificationRepository()
        val notificationViewModelFactory = NotificationViewModelFactory(notificationRepository)
        notificationViewModel = ViewModelProvider(
            this,
            notificationViewModelFactory
        ).get(NotificationViewModel::class.java)

        notificationViewModel.sendReservationMessageResponse.observe(this) { response ->
            if (response.isSuccessful) {
                Log.d(notificationTag, response.body().toString())
            } else {
                showReservationDialogBox()
                Log.d(
                    notificationTag,
                    "Send reservation message request error. ${response.errorBody()}"
                )
            }
            LoadingUtils.hideDialog()
        }
    }

    private fun showReservationDialogBox() {
        val reservationDialog = Intent(this, ReservationDialogActivity::class.java)
        reservationDialog.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        reservationDialog.putExtra("title",  "Greška")
        reservationDialog.putExtra("body",  "Neuspešno slanje rezervacije")
        reservationDialog.putExtra("buttonText", "U redu")
        reservationDialog.putExtra("activity", "")

        startActivity(reservationDialog)
    }


    private fun addOnTouchListenerForDates() {
        recyclerViewDate.addOnItemTouchListener(
            RecyclerItemClickListener(
                this,
                recyclerViewDate,
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View?, position: Int) {
                        this?.let {
                        }

                        for (cardView in recyclerViewDate.children) {
                            cardView.setBackgroundResource(R.drawable.item_add_new_product_backround)
                        }
                        view?.setBackgroundResource(R.drawable.item_add_new_product_backround_filled)
                        date = Calendar.getInstance().get(Calendar.YEAR).toString() + "-" + dataholderDateModel[position].dayMonth.toString() + "-" + dataholderDateModel[position].dayDate.toString()
                        createHours(date)
                    }

                    override fun onLongItemClick(view: View?, position: Int) {
                    }
                })
        )
    }


    private fun addOnTouchListenerForHours() {
        recyclerViewHour.addOnItemTouchListener(
            RecyclerItemClickListener(
                this,
                recyclerViewHour,
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View?, position: Int) {
                        this?.let {
                        }

                        for (cardView in recyclerViewHour.children) {
                            cardView.setBackgroundResource(R.drawable.item_add_new_product_backround)
                        }
                        view?.setBackgroundResource(R.drawable.item_add_new_product_backround_filled)
                        hour = " " + dataholderHourModel[position].hourFirst.toString()+":23.1226798"
                    }

                    override fun onLongItemClick(view: View?, position: Int) {
                    }
                })
        )
    }

    public fun validateEmail() : Boolean{

        var email : String = emailEditText.text.toString()

        if (email.isNullOrEmpty()) {

            emailLayout.setError("Polje ne sme biti prazno")
            return false
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailLayout.setError("Email nije u ispravnom formatu")
            return false
        }
        else{
            emailLayout.setError(null)
            return true
        }

    }
    public fun checkName() : Boolean{

        var firstname: String = findViewById<EditText>(R.id.firstnameLastnameReservation).text.toString()

        if (firstname.isNullOrEmpty()){
            nameLayout.setError("Polje ne sme biti prazno")
            return false
        }
        else{
            nameLayout.setError(null)
            return true
        }
    }
    public fun checkPhoneNumber() : Boolean{

        var phoneNumber: String = findViewById<EditText>(R.id.phoneNumberReservation).text.toString()

        if (phoneNumber.isNullOrEmpty()){
            phonenumberLayout.setError("Polje ne sme biti prazno")
            return false
        }
        else if (!PHONE_NUMBER.matcher(phoneNumber).matches()){
            phonenumberLayout.setError("Broj telefona nije u ispravnom formatu (Format: 06XYYYYYYY)")
            return false
        }
        else {
            phonenumberLayout.setError(null)
            return true
        }


    }
}