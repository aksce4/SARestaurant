package com.sa.restaurant.appview.weather.model

data class City(val id: Int = 0,
                val name: String = "",
                val coord: Coord,
                val country: String = "")