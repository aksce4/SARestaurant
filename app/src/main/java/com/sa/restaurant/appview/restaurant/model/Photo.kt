package com.sa.restaurant.appview.restaurant.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Photo {

    @Expose
    var height: Long? = null
    @SerializedName("html_attributions")
    var htmlAttributions: List<String>? = null
    @SerializedName("photo_reference")
    var photoReference: String? = null
    @Expose
    var width: Long? = null

}
