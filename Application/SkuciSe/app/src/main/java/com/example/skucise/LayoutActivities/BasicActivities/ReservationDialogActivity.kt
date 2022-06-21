package com.example.skucise.LayoutActivities.BasicActivities

import android.app.AlertDialog
import android.content.Intent
import android.icu.util.Calendar
import android.icu.util.TimeZone

import android.os.Bundle
import android.provider.CalendarContract

import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*
import androidx.core.app.ActivityCompat

import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
import java.lang.Exception


class ReservationDialogActivity : AppCompatActivity() {
    private var dateFormat: SimpleDateFormat =
        SimpleDateFormat("dd-MMM-yy HH:mm:ss", Locale.getDefault())

    private var title: String? = null
    private var body: String? = null
    private var buttonText: String? = null
    private var activity: String? = null
    private var isConfirmed: Boolean? = null
    private var advertName: String? = null
    private var dateOfReservation: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = intent.extras
        if (b != null) {
            title = b!!.getString("title")
            body = b!!.getString("body")
            buttonText = b!!.getString("buttonText")
            activity = b!!.getString("activity")
            isConfirmed = b!!.getBoolean("isConfirmed")
            advertName = b!!.getString("advertName")
            dateOfReservation = b!!.getString("dateOfReservation")

            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setTitle(title)
            builder.setMessage(body)
                .setCancelable(false)
                .setNeutralButton(if (isConfirmed!!) "Dodaj u kalendar" else "") { dialog, id ->
                    if (advertName != null && dateOfReservation != null) {
                        addReservationEventToCalendar(
                            advertName,
                            dateOfReservation
                        )
                    }
                }
                .setPositiveButton(buttonText) { dialog, id ->
                    if (activity != "") {
                        val intent = Intent(activity)
                        startActivity(intent)
                    }
                    finish()
                }

            val alertDialog = builder.create()
            alertDialog.show()
        }
    }

    private fun addReservationEventToCalendar(title: String?, dateOfReservation: String?) {
        val intent = Intent(Intent.ACTION_EDIT)
        val startMillis: Long = getMilliseconds(dateOfReservation!!)

        intent.type = "vnd.android.cursor.item/event"
        intent.putExtra(
            CalendarContract.EXTRA_EVENT_BEGIN_TIME,
            startMillis
        )
        intent.putExtra("endTime", startMillis + 3600000)
        intent.putExtra("title", "Razgledanje $title nekretnine")
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        startActivity(intent)
    }

    private fun checkPermission(callbackId: Int, vararg permissionsId: String) {
        var permissions = true
        for (p in permissionsId) {
            permissions =
                permissions && ContextCompat.checkSelfPermission(this, p) == PERMISSION_GRANTED
        }
        if (!permissions) ActivityCompat.requestPermissions(this, permissionsId, callbackId)
    }

    private fun getMilliseconds(date: String): Long {
        return try {
            val dateParsed = dateFormat.parse(date)
            val cal: Calendar = Calendar.getInstance(TimeZone.getDefault())

            cal.time = dateParsed
            val beginCal = Calendar.getInstance(TimeZone.getDefault())

            beginCal[cal[Calendar.YEAR], cal[Calendar.MONTH], cal[Calendar.DAY_OF_MONTH], cal[Calendar.HOUR_OF_DAY]] =
                cal[Calendar.MINUTE]

            beginCal.timeInMillis
        } catch (e: Exception) {
            Date().time
        }
    }

    companion object {
        var b: Bundle? = null
    }
}