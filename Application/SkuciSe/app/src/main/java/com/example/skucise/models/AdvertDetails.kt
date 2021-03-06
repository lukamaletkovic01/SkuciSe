package com.example.skucise.models

import com.example.skucise.models.HelperModels.FurnishedType
import com.example.skucise.models.HelperModels.HeatingType
import com.example.skucise.models.HelperModels.OldNewBuilding
import java.io.Serializable

class AdvertDetails(
    var id: Long,
    var date: String,
    var township: String,
    var city: String,
    var squaredArea: Int,
    var bedroomNumber: Int,
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
    var description: String,
    var houseOrder: String,
    var floorNumber: Int,

    var advertId: Long,
    var furnishedTypeId: Long,
    var furnishedType: FurnishedType?,
    var heatingTypeId: Long,
    var heatingType: HeatingType?,
    var oldNewBuildingId: Long,
    var oldNewBuilding: OldNewBuilding?,
    var latitude: Double?,
    var longitude: Double?
) : Serializable{ constructor(): this(
   0,
    "",
    "",
    "",
    0,
    0,
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
    "",
    "",
    0,
   0,
    0,
    null,
    0,
    null,
    0,
    null,
    44.8167,
    20.4667
)
}
