package com.example.service.data.api.repositories

import com.example.service.core.constants.WEATHER_API_KEY
import com.example.service.data.api.entities.WeatherResponse
import com.example.service.data.api.entities.extension.toResponseDomain
import com.example.service.data.api.services.WeatherService
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Response
import java.io.IOException

@ExperimentalCoroutinesApi
internal class WeatherRepositoriesTest {


    @Mock
    private lateinit var weatherService: WeatherService

    private lateinit var repository: WeatherRepositories

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        repository = WeatherRepositories(weatherService)
    }

    @Test
    fun `get city weather failed`() = runBlocking {

        `when`(
            weatherService.getCityWeather(
                "paris",
                "fr",
                "metric",
                ""
            )
        )
            .thenReturn(Response.error(400, "Hello world".toResponseBody(null)))

        val result = repository.getCityWeather(
            "paris",
            "fr"
        )

        Mockito.verify(weatherService, Mockito.times(1)).getCityWeather(
            "paris",
            "fr", "metric", WEATHER_API_KEY
        )

        assert(result.isFailure)
        assert(result.exceptionOrNull() is Exception)
    }

    @Test
    fun `get city weather success`() = runBlocking {
        val responseBody = WeatherResponse(
            null, null,
            null, 0,
            emptyList(), null
        )

        `when`(
            weatherService.getCityWeather(
                "paris",
                "fr",
                "metric",
                WEATHER_API_KEY
            )
        ).thenReturn(Response.success(200, responseBody))

        val result = repository.getCityWeather(
            "paris",
            "fr"
        )
        Mockito.verify(weatherService, Mockito.times(1)).getCityWeather(
            "paris",
            "fr", "metric", WEATHER_API_KEY
        )

        Assert.assertFalse(result.isFailure)
        Assert.assertNotNull(result.getOrNull())
    }
}