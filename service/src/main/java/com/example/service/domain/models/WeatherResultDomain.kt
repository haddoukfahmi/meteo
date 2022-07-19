package com.example.service.domain.models

import androidx.annotation.Keep
import com.example.service.data.api.entities.Main
import com.example.service.data.api.entities.Sys
import com.example.service.data.api.entities.Weather
import com.example.service.data.api.entities.Wind

@Keep
data class WeatherResultDomain(
    val main: Main? = null,
    val name: String? = null,
    val sys: Sys? = null,
    val timezone: Int = 0,
    val weather: List<Weather>? = emptyList(),
    val wind: Wind? = null
)