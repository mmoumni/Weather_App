package com.example.weather_app

data class Weather(
    val weather: List<WeatherCondition>,
    val main: Main,
    val wind: Wind
)

data class WeatherCondition(
    val main: String,
    val description: String,
    val icon: String
)

data class Main(
    val temp: Double,
    val feelsLike: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure: Double,
    val humidity: Double


)data class Wind(
    val speed: Double



)