package com.example.skucise.models.HelperModels

import java.io.Serializable

data class UserLogin(


    val id: Long?,
    val email: String,
    val password: String,
    val role: Byte?,
    val token: String?,
    val fcmToken: String?
): Serializable {}
