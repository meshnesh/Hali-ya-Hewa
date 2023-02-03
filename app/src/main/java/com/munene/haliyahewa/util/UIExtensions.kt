package com.munene.haliyahewa.util

import android.annotation.SuppressLint
import android.text.format.DateUtils
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.munene.haliyahewa.R
import com.munene.haliyahewa.data.db.entities.Forecast
import com.munene.haliyahewa.data.weather.WeatherType
import com.munene.haliyahewa.ui.forecast.ForecastAdapter
import com.munene.haliyahewa.util.bindings.imageName

object UIExtensions {

    @JvmStatic
    @BindingAdapter("temperature")
    fun TextView.setTemperature(double: Double?) {
        val context = this.context
        if (double != null) {
            this.text =
                context.resources.getString(R.string.current_temperature, double.toInt().toString())
        } else {
            this.text = context.resources.getString(R.string.current_temperature, "--")
        }
    }

    @JvmStatic
    @BindingAdapter("weatherImage")
    fun ImageView.setWeatherImage(code: Int) {
        when (code) {
            in WeatherType.CLEAR.range -> {
                this.imageName("sea_sunny")
            }
            in WeatherType.CLOUDS.range,
            in WeatherType.ATMOSPHERE.range -> {
                this.imageName("sea_cloudy")
            }
            in WeatherType.DRIZZLE.range,
            in WeatherType.RAIN.range,
            in WeatherType.THUNDERSTORM.range,
            in WeatherType.SNOW.range -> {
                this.imageName("sea_rainy")
            }
        }
    }

    @JvmStatic
    @BindingAdapter("weatherBackground")
    fun ViewGroup.setWeatherBackgroundColor(code: Int) {
        when (code) {
            in WeatherType.CLEAR.range -> {
                this.setBackgroundResource(R.color.color_sunny)
            }
            in WeatherType.CLOUDS.range,
            in WeatherType.ATMOSPHERE.range -> {
                this.setBackgroundResource(R.color.color_cloudy)
            }
            in WeatherType.DRIZZLE.range,
            in WeatherType.RAIN.range,
            in WeatherType.THUNDERSTORM.range,
            in WeatherType.SNOW.range -> {
                this.setBackgroundResource(R.color.color_rainy)
            }
        }
    }

    @JvmStatic
    @BindingAdapter("weatherScrim")
    fun CollapsingToolbarLayout.setWeatherScrimColor(code: Int) {
        when (code) {
            in WeatherType.CLEAR.range -> {
                this.setContentScrimColor(ContextCompat.getColor(context, R.color.color_sunny))
                this.setStatusBarScrimColor(ContextCompat.getColor(context, R.color.color_sunny))
            }
            in WeatherType.CLOUDS.range,
            in WeatherType.ATMOSPHERE.range -> {
                this.setContentScrimColor(ContextCompat.getColor(context, R.color.color_cloudy))
                this.setStatusBarScrimColor(ContextCompat.getColor(context, R.color.color_cloudy))
            }
            in WeatherType.DRIZZLE.range,
            in WeatherType.RAIN.range,
            in WeatherType.THUNDERSTORM.range,
            in WeatherType.SNOW.range -> {
                this.setContentScrimColor(ContextCompat.getColor(context, R.color.color_rainy))
                this.setStatusBarScrimColor(ContextCompat.getColor(context, R.color.color_rainy))
            }
        }
    }

    @SuppressLint("SetTextI18n")
    @JvmStatic
    @BindingAdapter("lastUpdate")
    fun TextView.setLastUpdate(lastUpdate: Long) {
        val now = System.currentTimeMillis()
        val ago = DateUtils.getRelativeTimeSpanString(
            lastUpdate,
            now,
            0L,
            DateUtils.FORMAT_ABBREV_RELATIVE
        )
        this.text = "Last update: $ago"
    }

    @JvmStatic
    @BindingAdapter("forecast")
    fun RecyclerView.updateForecast(forecasts: List<Forecast>?) {
        if (forecasts != null) {
            (adapter as ForecastAdapter).updateForecast(forecasts)
        }
    }

    @JvmStatic
    @BindingAdapter("forecastIcon")
    fun ImageView.setForecastIcon(code: Int) {
        when (code) {
            in WeatherType.CLEAR.range -> {
                this.imageName("ic_sun")
            }
            in WeatherType.CLOUDS.range,
            in WeatherType.ATMOSPHERE.range -> {
                this.imageName("ic_cloud")
            }
            in WeatherType.DRIZZLE.range -> {
                this.imageName("ic_drizzle")
            }
            in WeatherType.RAIN.range -> {
                this.imageName("ic_rain")
            }
            in WeatherType.THUNDERSTORM.range -> {
                this.imageName("ic_storm")
            }
            in WeatherType.SNOW.range -> {
                this.imageName("ic_snow")
            }
        }
    }
}
