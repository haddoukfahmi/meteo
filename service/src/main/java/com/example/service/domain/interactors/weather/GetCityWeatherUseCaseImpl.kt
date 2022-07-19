package com.example.service.domain.interactors.weather

import com.example.service.data.api.repositories.WeatherRepositories
import com.example.service.domain.models.WeatherResultDomain
import javax.inject.Inject

internal class GetCityWeatherUseCaseImpl @Inject constructor(
    private val repositories: WeatherRepositories
) : GetCityWeatherUseCase {
    override suspend fun invoke(param: Param): Result<WeatherResultDomain> {
        return repositories.getCityWeather(param.city, param.language)
    }
}