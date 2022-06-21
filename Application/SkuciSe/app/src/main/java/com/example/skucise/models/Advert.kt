package com.example.skucise.models

import com.example.skucise.models.HelperModels.AdvertType
import java.io.Serializable

class Advert(
	var id: Long?,
    var name: String,
    var price: Long,
    var ratingsCount: Int = 0,
    var advertAverageRating: Double = 0.0,

    // Foreign entities
    var userId: Long?,
    var user: User?,
    var advertDetailsId: Long?,
    var advertDetails: AdvertDetails?,
    var realtyTypeId: Long?,
    var advertTypeId: Long?,
    var advertType: AdvertType?,
    var advertImages: List<AdvertImage>?,
    var comments: List<Comment>
) : Serializable {
    constructor() : this(
        0,
        "",
        0,
        0,
        0.0,
        0,
        null,
        0,
        null,
        0,
        0,
        null,
        null,
        listOf()
    )
}