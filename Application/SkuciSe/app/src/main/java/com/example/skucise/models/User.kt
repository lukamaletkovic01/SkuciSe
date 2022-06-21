package com.example.skucise.models

import java.io.Serializable
import java.util.*

data class User(
    val id: Long?,
    val email: String,
    var firstname: String?,
    var lastname: String?,
    val password: String,
    val profileImage: String?,
    var phoneNumber: String?,
    val role: Byte,
    val fcmToken: String = "",
    var city: String?,
    var address: String?,
    var userAverageRating: Double = 0.0,
    var ratingsCount: Int = 0
): Serializable {
}


