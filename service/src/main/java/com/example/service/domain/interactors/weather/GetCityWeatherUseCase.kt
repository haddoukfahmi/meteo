package com.example.service.domain.interactors.weather

import com.example.service.domain.models.WeatherResultDomain

interface GetCityWeatherUseCase {
    suspend operator fun invoke(param: Param): Result<WeatherResultDomain>
}

data class Param(
    val city: String,
    val language: String
)