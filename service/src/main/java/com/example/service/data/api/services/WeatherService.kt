package com.example.service.data.api.services

import com.example.service.data.api.entities.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("data/2.5/weather")
    suspend fun getCityWeather(
        @Query("q") city: String = "Paris",
        @Query("lang") language: String = "fr",
        @Query("units") units: String = "metric",
        @Query("appid") apiId: String
    ): Response<WeatherResponse>
}