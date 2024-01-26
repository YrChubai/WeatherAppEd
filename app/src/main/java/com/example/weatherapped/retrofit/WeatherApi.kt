package com.example.weatherapped.retrofit
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    interface WeatherApi {
        @GET("forecast.json")
        fun getWeatherData(
            @Query("key") apiKey: String,
            @Query("q") city: String,
            @Query("days") days: String,
            @Query("aqi") aqi: String,
            @Query("alerts") alerts: String
        ): Call<String>
    }
}