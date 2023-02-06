package com.example.weather_app

import org.junit.Test
import org.mockito.Mockito
import retrofit2.Call
import retrofit2.Response

class WeatherApiTest {
    @Test
    fun `Test if response fits Weather data class`() {
        // Create a mocked Call object for the API response
        val weatherCall = Mockito.mock(Call::class.java) as Call<Weather>

        // Define the expected response from the API
        val expectedWeather = Weather(
            listOf(WeatherCondition("Clear", "clear sky", "01d")),
            Main(20.0, 18.0, 15.0, 25.0)
        )
        val expectedResponse = Response.success(expectedWeather)

        // Set the mocked API response
        Mockito.`when`(weatherCall.execute()).thenReturn(expectedResponse)

        // Call the API and verify if the response fits the Weather data class
        val response = weatherCall.execute()
        assert(response.isSuccessful)
        val weather = response.body()
        assert(weather?.weather?.get(0)?.main == expectedWeather.weather[0].main)
        assert(weather?.weather?.get(0)?.description == expectedWeather.weather[0].description)
        assert(weather?.weather?.get(0)?.icon == expectedWeather.weather[0].icon)
        assert(weather?.main?.temp == expectedWeather.main.temp)
        assert(weather?.main?.feelsLike == expectedWeather.main.feelsLike)
        assert(weather?.main?.tempMin == expectedWeather.main.tempMin)
        assert(weather?.main?.tempMax == expectedWeather.main.tempMax)
    }
}