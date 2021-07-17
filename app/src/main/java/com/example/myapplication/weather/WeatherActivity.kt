package com.example.myapplication.weather

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.myapplication.base.BaseActivity
import com.example.myapplication.databinding.ActivityWeatherBinding
import com.example.myapplication.databinding.ItemForecastBinding
import com.example.myapplication.entity.Weather
import com.example.myapplication.entity.getSky
import com.example.myapplication.network.DataResult
import com.example.myapplication.utils.KeyboardUtils
import com.example.myapplication.utils.toast
import java.text.SimpleDateFormat
import java.util.*

class WeatherActivity : BaseActivity() {

    private lateinit var binding: ActivityWeatherBinding

    val weatherModel: WeatherViewModel by viewModels()

    private val inflater by lazy {
        val inflater = LayoutInflater.from(this)
        inflater
    }

    companion object {
        fun actionStart(context: Context, placeName: String, lng: String, lat: String) {
            val intent = Intent(context, WeatherActivity::class.java)
                .putExtra("lng", lng)
                .putExtra("lat", lat)
                .putExtra("placeName", placeName)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //透明状态栏
        if (Build.VERSION.SDK_INT >= 21) {
            val decorView = window.decorView
            decorView.systemUiVisibility =
                (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
            window.statusBarColor = Color.TRANSPARENT
        }
        binding = ActivityWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        weatherModel.lng = intent.getStringExtra("lng") ?: ""
        weatherModel.lat = intent.getStringExtra("lat") ?: ""
        weatherModel.placeName = intent.getStringExtra("placeName") ?: ""

        refreshWeather()

        binding.swipeRefresh.setOnRefreshListener {
            refreshWeather()
            binding.swipeRefresh.isRefreshing = true
        }

        binding.nowWeather.swithPlace.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }

        binding.drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
            }

            override fun onDrawerOpened(drawerView: View) {
            }

            override fun onDrawerClosed(drawerView: View) {
                KeyboardUtils.close(drawerView)
            }

            override fun onDrawerStateChanged(newState: Int) {
            }
        })

    }

    fun refreshWeather() {
        weatherModel.refreshWeather(weatherModel.lng, weatherModel.lat).observe(this) {
            when (it) {
                is DataResult.Success -> {
                    showWeather(it.response)
                }
                is DataResult.Error -> {
                    toast(this@WeatherActivity, it.exception.msg)
                }
            }
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun showWeather(weather: Weather) {
        binding.weatherLayout.visibility = View.VISIBLE

        val realtimeResult = weather.realtimeResult
        val realtime = realtimeResult.result.realtime
        val dailyResult = weather.dailyResult
        val daily = dailyResult.result.daily
        binding.nowWeather.placeName.text = weatherModel.placeName
        binding.nowWeather.nowLayout.setBackgroundResource(getSky(realtime.skycon).bg)
        binding.nowWeather.nowTemp.text = realtime.temperature.toString()
        binding.nowWeather.nowSky.text = getSky(realtime.skycon).info
        binding.nowWeather.nowAQI.text = "空气指数 ${realtime.airQuality.aqi.chn.toInt()}"

        binding.forecastWeather.forecastWrapper.removeAllViews()
        val days = dailyResult.result.daily.skycon.size
        for (i in 0 until days) {
            val skycon = daily.skycon[i]
            val temperature = daily.temperature[i]

            val itemBinding = ItemForecastBinding.inflate(inflater,
                binding.forecastWeather.forecastWrapper,
                false)
            val dateInfo = itemBinding.dateInfo
            val skyIcon = itemBinding.skyIcon
            val skyInfo = itemBinding.skyInfo
            val temperatureInfo = itemBinding.temperatureInfo

            val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            dateInfo.text = dateFormatter.format(skycon.date)
            skyIcon.setImageResource(getSky(skycon.value).icon)
            skyInfo.text = getSky(skycon.value).info
            temperatureInfo.text = "${temperature.min.toInt()} ~ ${temperature.max.toInt()} ℃"

            binding.forecastWeather.forecastWrapper.addView(itemBinding.root)
        }

        val lifeIndex = dailyResult.result.daily.lifeIndex
        binding.lifeIndex.coldRiskText.text = lifeIndex.coldRisk[0].desc
        binding.lifeIndex.dressingText.text = lifeIndex.dressing[0].desc
        binding.lifeIndex.ultravioletText.text = lifeIndex.ultraviolet[0].desc
        binding.lifeIndex.carWashingText.text = lifeIndex.carWashing[0].desc
    }

    fun closeDrawers() {
        binding.drawerLayout.closeDrawers()
    }
}