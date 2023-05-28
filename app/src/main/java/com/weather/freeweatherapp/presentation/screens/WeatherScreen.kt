package com.weather.freeweatherapp.presentation.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.weather.freeweatherapp.presentation.screencomponents.HourlyWeatherToday
import com.weather.freeweatherapp.presentation.screencomponents.WeatherToday
import com.weather.freeweatherapp.presentation.viewmodel.AppViewModel

@Composable
fun WeatherScreen(viewModel: AppViewModel){

    var dailyWeather = viewModel.dataDaily.value.data

    var hourlyWeather = viewModel.dataHourly.value.data


    Log.d("dailyWeather_recompose", "WeatherScreen: ${dailyWeather!!.daily.temperature2mMax.get(0)}")


//    val hourlyWeather = viewModel.dataHourly.collectAsState(initial = null)
//    val dailyWeather = viewModel.dataDaily.collectAsState(initial = null)

    Column(modifier = Modifier
        .wrapContentHeight()
        .fillMaxWidth()) {

        WeatherToday(dailyWeather)

        Spacer(modifier = Modifier
            .height(10.dp)
            .padding(bottom = 20.dp))
        
        HourlyWeatherToday(hourlyWeather!!)

        
        Divider(modifier = Modifier.height(1.dp),
                color = Color.LightGray,
                thickness = 1.dp)


    }



}


