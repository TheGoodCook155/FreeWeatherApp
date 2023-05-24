package com.weather.freeweatherapp.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.weather.freeweatherapp.presentation.screencomponents.HourlyWeatherToday
import com.weather.freeweatherapp.presentation.viewmodel.AppViewModel

@Composable
fun WeatherScreen(viewModel: AppViewModel){

    val weatherAPIResponse = viewModel.data.value.data

    Column(modifier = Modifier
        .wrapContentHeight()
        .fillMaxWidth()
        .background(Color.Yellow)) {

        HourlyWeatherToday(weatherAPIResponse!!)

        Divider(modifier = Modifier.height(1.dp),
                color = Color.LightGray,
                thickness = 1.dp)

//        FollowingDays()

    }



}


