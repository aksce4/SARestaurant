package com.sa.restaurant.appview.weather.model

data class ListItem(val dt: Int = 0,
                    val main: Main,
                    val weather: List<WeatherItem>?,
                    val clouds: Clouds,
                    val wind: Wind,
                    val sys: Sys,
                    val dtTxt: String = "")