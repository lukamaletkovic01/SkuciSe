package com.example.skucise.models

import com.example.skucise.models.HelperModels.FurnishedType
import com.example.skucise.models.HelperModels.HeatingType
import com.example.skucise.models.HelperModels.OldNewBuilding
import java.io.Serializable

class AdvertImage(
    var id: Long,
    var path : String,
    var advertId : Long
) : Serializable {
}