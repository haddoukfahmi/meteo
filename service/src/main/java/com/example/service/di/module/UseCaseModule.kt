package com.example.service.di.module

import com.example.service.domain.interactors.weather.GetCityWeatherUseCase
import com.example.service.domain.interactors.weather.GetCityWeatherUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    internal fun bindGetCityWeatherUseCase(
        impl: GetCityWeatherUseCaseImpl
    ): GetCityWeatherUseCase = impl
}