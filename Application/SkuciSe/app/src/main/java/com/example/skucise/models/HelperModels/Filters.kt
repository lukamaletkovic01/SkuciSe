package com.example.skucise.models

import com.example.skucise.models.HelperModels.FurnishedType
import com.example.skucise.models.HelperModels.HeatingType
import com.example.skucise.models.HelperModels.OldNewBuilding
import java.io.Serializable

class Filters(
    var city: String,
    var terrace: Boolean,
    var parking: Boolean,
    var wifi: Boolean,
    var tv: Boolean,
    var airCondition: Boolean,
    var kitchen: Boolean,
    var bathroom: Boolean,
    var alarm: Boolean,
    var pool: Boolean,
    var barbecue: Boolean,
    var firePlace: Boolean,
    var gym: Boolean,
    var rent: Boolean,
    var sell: Boolean,
    var house: Boolean,
    var flat: Boolean,
    var garage: Boolean,
    var parkingLott: Boolean,
    var businessArea: Boolean,
    var priceMin: Long,
    var priceMax: Long

    ) : Serializable{
    constructor() : this(
        "Kragujevac",
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        100,
        1000
        )
}