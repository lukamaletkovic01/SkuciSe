package com.example.skucise.models

import com.example.skucise.models.HelperModels.Status
import org.joda.time.DateTime
import org.joda.time.LocalDateTime
import java.io.Serializable

class Reservation (
    var id: Long?,
    var advertId: Long?,
    var advert: Advert?,
    var ownerId: Long?,
    var userId: Long?,
    var user: User?,
    var dateOfReservation: String?,
    var date: LocalDateTime?,
    var status: Status?,
    var commentId: Long?,
    var comment: Comment? = null
): Serializable {}