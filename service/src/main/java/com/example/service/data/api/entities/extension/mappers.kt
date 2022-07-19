package com.example.service.data.api.entities.extension

import com.example.service.data.api.entities.WeatherResponse
import com.example.service.domain.models.WeatherResultDomain

internal fun WeatherResponse.toResponseDomain(): WeatherResultDomain = WeatherResultDomain(
    main = main,
    name = name,
    sys = sys,
    timezone = timezone,
    weather = weather,
    wind = wind
)