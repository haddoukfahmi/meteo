package com.example.meteo.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.meteo.databinding.FragmentWeatherDetailsBinding
import com.example.service.core.constants.WEATHER_ICON_BASE_URL
import com.example.service.domain.models.WeatherResultDomain
import java.text.SimpleDateFormat
import java.util.*

class WeatherDetailsFragment : Fragment() {

    private var _binding: FragmentWeatherDetailsBinding? = null
    private val binding: FragmentWeatherDetailsBinding
        get() = _binding
            ?: error("ViewBinding is missing")

    private val arguments: WeatherDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        populateUi(arguments.weather)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun populateUi(weather: WeatherResultDomain) {
        binding.apply {
            weatherCityName.text = "${weather.name}, ${weather.sys?.country}"

            val sdf = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
            val currentDate: String = sdf.format(Date())
            weatherDate.text = currentDate

            weatherTemp.text = "${weather.main?.temp.toString()}°"
            weatherHumid.text = "${weather.main?.humidity.toString()}%"

            val imageUrl = "$WEATHER_ICON_BASE_URL${weather.weather?.get(0)?.icon}@2x.png"
            Glide.with(requireContext()).load(imageUrl)
                .into(binding.weatherIcn)

            toolbar.title = "${weather.name}"
            toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }
}