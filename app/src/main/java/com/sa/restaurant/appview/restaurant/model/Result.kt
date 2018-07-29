package com.sa.restaurant.appview.restaurant.model

data class Result( val geometry: Geometry,
                   val icon: String,
                   val id: String,
                   val name: String,
                   val place_id: String,
                   val reference: String,
                   val scope: String,
                   val types: List<String>,
                   val vicinity: String,
                   val rating: Double,
                   val photos: List<Photo>,
                   val opening_hour: OpeningHour)