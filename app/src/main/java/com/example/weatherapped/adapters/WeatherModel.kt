package com.example.weatherapped.adapters

import com.example.weatherapped.retrofit.HourModel
import java.util.concurrent.locks.Condition

data class WeatherModel(
    val city: String,
    val time: String,
    val condition: String,
    val currentTemp: String,
    val maxTemp: String,
    val minTemp: String,
    val imageUrl: String,
    val hours: List<HourModel>?
)
