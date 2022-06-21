package com.example.skucise.services

import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.skucise.LayoutActivities.BasicActivities.ReservationDialogActivity
import com.example.skucise.LayoutActivities.LoginActivities.SessionManager
import com.example.skucise.LayoutActivities.WelcomeScreenActivities.WelcomeScreenActivity
import com.example.skucise.models.HelperModels.UserLogin
import com.example.skucise.utils.Constrants.Companion.BASE_URL
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import android.app.PendingIntent
import android.app.NotificationManager

import android.media.RingtoneManager
import android.net.Uri
import android.app.NotificationChannel
import android.graphics.Color
import android.R

import androidx.core.app.NotificationCompat


class SkuciSeFirebaseMessagingService : FirebaseMessagingService() {
    var context: Context = this
    var count = 0

    companion object {
        const val TAG = "SkuciSeFirebaseMsgService"
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
//        Log.d(TAG, "Message data payload: ${remoteMessage.data}")
//        sendNotification(
//            remoteMessage.data["title"],
//            remoteMessage.data["message"],
//            remoteMessage.notification?.body
//        )

        Log.d(TAG, "Message data payload: ${remoteMessage.data}")
        remoteMessage.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
            showReservationDialogBox(it.body, it.clickAction,
                remoteMessage.data["answer"] == "confirmed",
                remoteMessage.data["advertName"],
                remoteMessage.data["dateOfReservation"]
            )
            // addReservationEventToCalendar(it.body)
        }
    }



    private fun sendNotification(title: String?, messageBody: String?, body: String?) {
        val intent = Intent(applicationContext, WelcomeScreenActivity::class.java)

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra("pushnotification", "yes")

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val defaultSoundUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val mNotifyManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_LOW
            val mChannel = NotificationChannel("Reservation", "Reservation", importance)

            mChannel.description = body
            mChannel.enableLights(true)
            mChannel.lightColor = Color.RED
            mChannel.enableVibration(true)
            mChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)

            mNotifyManager.createNotificationChannel(mChannel)
        }

        val mBuilder: NotificationCompat.Builder = NotificationCompat.Builder(this, "Reservation")
        mBuilder.setContentTitle(title)
            .setSmallIcon(R.drawable.ic_dialog_info)
            .setContentText(body)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setColor(Color.parseColor("#FFD600"))
            .setContentIntent(pendingIntent)
            .setChannelId("Sesame").priority = NotificationCompat.PRIORITY_LOW

        mNotifyManager.notify(count, mBuilder.build());
        count++;
    }

    private fun showReservationDialogBox(
        body: String?,
        clickAction: String?,
        isConfirmed: Boolean,
        advertName: String?,
        dateOfReservation: String?
    ) {
        val reservationDialog = Intent(this, ReservationDialogActivity::class.java)
        reservationDialog.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        reservationDialog.putExtra("title", "Obaveštenje")
        reservationDialog.putExtra("buttonText", "Idi na Razgledanja")
        reservationDialog.putExtra("body", body)
        reservationDialog.putExtra("activity", clickAction)
        reservationDialog.putExtra("isConfirmed", isConfirmed)
        reservationDialog.putExtra("advertName", advertName)
        reservationDialog.putExtra("dateOfReservation", dateOfReservation)

        startActivity(reservationDialog)
    }

    override fun onNewToken(newToken: String) {
        super.onNewToken(newToken)
        sendNewTokenToServer(newToken)
    }

    private fun sendNewTokenToServer(newToken: String) {
        val session = SessionManager(context)
        val hash: HashMap<String, String> = session.getUserDetails()
        val userId = hash[SessionManager.KEY_ID]

        if (userId != null) {
            val userLogin = UserLogin(
                userId.toLong(), "", "", null, null, newToken
            )

            val mediaType = "application/json; charset=utf-8".toMediaType()
            val okHttpClient = OkHttpClient()
            val requestBody = userLogin.toString().toRequestBody(mediaType)
            val request = Request.Builder()
                .method("POST", requestBody)
                .url("$BASE_URL/User/UpdateFcmToken")
                .build()
            okHttpClient.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.d(TAG, "Error on sending new token to the server.")
                }

                override fun onResponse(call: Call, response: Response) {
                    Log.d(TAG, "Successfully sent new token to the server.")
                }
            })
        }
    }
}
