package com.example.skucise.models.HelperModels

import org.joda.time.DateTime
import java.io.Serializable

data class ReservationMessage(
    val senderId: Long?,
    val receiverId: Long?,
    val advertId: Long?,
    val title: String,
    val dateOfReservation: String? = null,
    val flag: Int,
    val answer: Int? = null
) : Serializable {
}