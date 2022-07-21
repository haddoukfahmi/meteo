package com.example.meteo.presentation.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo.IME_ACTION_DONE
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.meteo.databinding.FragmentWeatherBinding
import com.example.meteo.extension.navigateTo
import com.example.meteo.presentation.ui.adapter.WeatherAdapter
import com.example.meteo.presentation.viewmodels.WeatherViewModel
import com.example.service.domain.models.WeatherResultDomain
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WeatherFragment : Fragment() {

    private val adapterCallback = WeatherAdapter.CityWeatherAdapterCallback {
        binding.addWeather.text = null
        navigateTo(
            WeatherFragmentDirections.actionWeatherFragmentToWeatherDetailsFragment(
                weather = it
            )
        )
    }

    private val viewModel: WeatherViewModel by viewModels()
    private val weatherAdapter by lazy {
        WeatherAdapter(
            adapterCallback,
            context = requireContext()
        )
    }
    private val weatherList: MutableList<WeatherResultDomain> = ArrayList()

    private var _binding: FragmentWeatherBinding? = null
    private val binding: FragmentWeatherBinding
        get() = _binding
            ?: error("ViewBinding is missing")

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(query: Editable?) {
            if (query?.length ?: 0 >= 5) {
                lifecycleScope.launch {
                    viewModel.getCityWeather(query.toString())
                }
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.weatherCityList) {
            val dividerItemDecoration = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
            layoutManager = LinearLayoutManager(requireView().context)
            addItemDecoration(dividerItemDecoration)
            adapter = weatherAdapter
        }

        if (weatherList.isNullOrEmpty()) {
            binding.stubCityNameError.inflate()
        }

        binding.addWeather.apply {
            addTextChangedListener(textWatcher)
            setOnEditorActionListener(object : TextView.OnEditorActionListener {
                override fun onEditorAction(
                    v: TextView?,
                    actionId: Int,
                    event: KeyEvent?
                ): Boolean {
                    if (actionId == IME_ACTION_DONE) {

                        return true
                    }
                    return false
                }

            })
        }

        viewModel.apply {
            weather.observe(viewLifecycleOwner, Observer(::handleWeatherResult))
            error.observe(viewLifecycleOwner, Observer(::handleWeatherError))
        }
    }

    private fun handleWeatherError(throwable: Throwable?) {
        if (throwable != null && weatherList.isNullOrEmpty())
            binding.stubCityNameError.visibility = VISIBLE
    }

    private fun handleWeatherResult(weatherResultDomain: WeatherResultDomain?) {
        if (!weatherAdapter.list.contains(weatherResultDomain)) {
            weatherResultDomain?.let { weatherList.add(it) }

        }
        weatherAdapter.list = weatherList
        binding.stubCityNameError.visibility = GONE
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}