package com.munene.haliyahewa.ui.forecast

import com.munene.haliyahewa.R
import com.munene.haliyahewa.data.db.entities.Forecast
import com.munene.haliyahewa.databinding.ItemForecastBinding
import com.munene.haliyahewa.util.recyclerview.DataBoundAdapter
import com.munene.haliyahewa.util.recyclerview.DataBoundViewHolder

class ForecastAdapter : DataBoundAdapter<ItemForecastBinding>(R.layout.item_forecast) {

    private var mForecast = listOf<Forecast>()

    override fun getItemCount(): Int = mForecast.size

    override fun bindItem(
        holder: DataBoundViewHolder<ItemForecastBinding>?,
        position: Int,
        payloads: MutableList<Any>?
    ) {
        val binding = holder?.binding!!
        val forecast = mForecast[position]
        binding.forecast = forecast
    }

    fun updateForecast(forecast: List<Forecast>) {
        mForecast = forecast
        notifyDataSetChanged()
    }
}
