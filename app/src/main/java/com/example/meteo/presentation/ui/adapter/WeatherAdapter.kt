package com.example.meteo.presentation.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.meteo.databinding.CityCellBinding
import com.example.meteo.presentation.ui.adapter.WeatherAdapter.WeatherViewHolder
import com.example.service.core.constants.WEATHER_ICON_BASE_URL
import com.example.service.domain.models.WeatherResultDomain

class WeatherAdapter(
    private val callback: CityWeatherAdapterCallback,
    val context: Context
) : ListAdapter<WeatherResultDomain, WeatherViewHolder>(WeatherDiffCallBack()) {

    init {
        setHasStableIds(true)
    }

    fun interface CityWeatherAdapterCallback {
        fun onAddressItemClick(weather: WeatherResultDomain)
    }

    public var list: List<WeatherResultDomain> = emptyList()
        set(value) {
            field = value
            submitList(value)
        }
        get() = currentList

    private class WeatherDiffCallBack : DiffUtil.ItemCallback<WeatherResultDomain>() {
        override fun areItemsTheSame(
            oldItem: WeatherResultDomain,
            newItem: WeatherResultDomain
        ): Boolean =
            oldItem.name == newItem.name

        override fun areContentsTheSame(
            oldItem: WeatherResultDomain,
            newItem: WeatherResultDomain
        ): Boolean =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder =
        WeatherViewHolder(
            CityCellBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), context
        )

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val cityWeather = list[position]
        holder.apply {
            fillView(cityWeather)
            itemView.setOnClickListener {
                callback.onAddressItemClick(weather = cityWeather)
            }
        }
    }

    override fun getItemCount(): Int = list.size

    class WeatherViewHolder(private val binding: CityCellBinding, private val context: Context) :
        RecyclerView.ViewHolder(binding.root) {

        fun fillView(cityWeather: WeatherResultDomain) {
            binding.weatherCityName.text = cityWeather.name

            val imageUrl = "${
                WEATHER_ICON_BASE_URL
            }${cityWeather.weather?.get(0)?.icon}@2x.png"
            Glide.with(context).load(imageUrl)
                .into(binding.weatherIcn)
        }
    }
}