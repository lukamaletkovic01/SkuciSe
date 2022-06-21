package com.example.skucise.models.HelperModels

import java.io.Serializable

class DateModel(
    var dayName: String,
    var dayDate: Long,
    var dayMonth: Long
) : Serializable {
}