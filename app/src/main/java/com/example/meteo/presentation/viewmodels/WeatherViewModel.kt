package com.example.meteo.presentation.viewmodels

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.service.domain.interactors.weather.GetCityWeatherUseCase
import com.example.service.domain.interactors.weather.Param
import com.example.service.domain.models.WeatherResultDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getCityWeatherUseCase: GetCityWeatherUseCase
) : BaseViewModel() {

    private val singleWeather = MutableLiveData<WeatherResultDomain?>()
    internal val weather: LiveData<WeatherResultDomain?>
        get() = singleWeather

    override fun removeObservers(owner: LifecycleOwner, clear: Boolean?) {
        singleWeather.removeObservers(owner)
        weather.removeObservers(owner)

        super.removeObservers(owner, clear)
    }

    override fun clear() {
        super.clear()
        singleWeather.value = null
    }

    suspend fun getCityWeather(city: String, lang: String = "FR") {
        getCityWeatherUseCase.invoke(
            Param(city = city, language = lang)
        )
            .onSuccess {
                singleWeather.postValue(it)
            }
            .onFailure {
                singleError.postValue(it)
                singleWeather.value = null
                return
            }
    }
}