package com.sa.restaurant.appview.weather.model

data class Main(val temp: Double = 0.0,
                val tempMin: Double = 0.0,
                val tempMax: Double = 0.0,
                val pressure: Double = 0.0,
                val seaLevel: Double = 0.0,
                val grndLevel: Double = 0.0,
                val humidity: Int = 0,
                val tempKf: Double = 0.0)