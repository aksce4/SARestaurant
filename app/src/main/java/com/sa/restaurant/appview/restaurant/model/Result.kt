package com.sa.restaurant.appview.restaurant.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Result {

    @Expose
    var geometry: Geometry? = null
    @Expose
    var icon: String? = null
    @Expose
    var id: String? = null
    @Expose
    var name: String? = null
    @Expose
    var photos: List<Photo>? = null
    @SerializedName("place_id")
    var placeId: String? = null
    @SerializedName("plus_code")
    var plusCode: PlusCode? = null
    @Expose
    var rating: Double? = null
    @Expose
    var reference: String? = null
    @Expose
    var scope: String? = null
    @Expose
    var types: List<String>? = null
    @Expose
    var vicinity: String? = null

}
