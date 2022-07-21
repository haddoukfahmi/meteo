package com.example.service.domain.models

import android.os.Parcelable
import com.example.service.data.api.entities.Main
import com.example.service.data.api.entities.Sys
import com.example.service.data.api.entities.Weather
import com.example.service.data.api.entities.Wind
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WeatherResultDomain(
    val main: Main? = null,
    val name: String? = null,
    val sys: Sys? = null,
    val timezone: Int = 0,
    val weather: List<Weather>? = emptyList(),
    val wind: Wind? = null
) : Parcelable