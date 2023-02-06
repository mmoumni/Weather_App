package com.example.weather_app

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("weather")
    fun getWeather(
        @Query("q") cityName: String,
        @Query("APPID") API_KEY: String,
        @Query("units") units: String
    ): Call<Weather>
}
