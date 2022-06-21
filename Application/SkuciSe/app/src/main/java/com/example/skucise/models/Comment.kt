package com.example.skucise.models

import java.io.Serializable

class Comment(
    var id: Long? = null,
    var content: String,
    var rating: Double,
    var datePublished: String,
    var user: User? = null,
    var advert: Advert? = null,
    var userId: Long,
    var advertId: Long,
    var reservationId: Long,
): Serializable {
}
