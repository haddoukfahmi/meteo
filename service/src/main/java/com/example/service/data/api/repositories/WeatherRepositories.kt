package com.example.service.data.api.repositories

import com.example.service.core.constants.WEATHER_API_KEY
import com.example.service.data.api.entities.extension.toResponseDomain
import com.example.service.data.api.services.WeatherService
import com.example.service.domain.models.WeatherResultDomain
import javax.inject.Inject

class WeatherRepositories @Inject constructor(
    private val weatherService: WeatherService
) {
    suspend fun getCityWeather(city: String, language: String): Result<WeatherResultDomain> {
        return runCatching {
            weatherService.getCityWeather(
                city = city,
                language = language,
                apiId = WEATHER_API_KEY
            ).let {
                it.body()!!.toResponseDomain()
            }
        }
    }
}