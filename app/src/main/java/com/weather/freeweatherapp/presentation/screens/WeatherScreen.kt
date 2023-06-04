package com.weather.freeweatherapp.presentation.screens

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.weather.freeweatherapp.presentation.screencomponents.HourlyWeatherToday
import com.weather.freeweatherapp.presentation.screencomponents.WeatherToday
import com.weather.freeweatherapp.presentation.viewmodel.AppViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "SuspiciousIndentation")
@Composable
fun WeatherScreen(viewModel: AppViewModel){

    val dailyWeather = viewModel.dataDaily.value.data

    val hourlyWeather = viewModel.dataHourly.value.data



    Log.d("hourly_Weather", "WeatherScreen: hourlyWeather : ${hourlyWeather}")


    var orientation by remember {
        mutableStateOf(Configuration.ORIENTATION_PORTRAIT)
    }

    val configuration = LocalConfiguration.current

    LaunchedEffect(configuration) {

        snapshotFlow {
            configuration.orientation
        }.collect {
            orientation = it
        }
    }

    when (orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {

            Column(modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()) {

                Column(modifier = Modifier.verticalScroll(rememberScrollState()).wrapContentHeight()) {

                    WeatherToday(dailyWeather)

                }
                    HourlyWeatherToday(hourlyWeather)
            }
        }
        else -> {
            Column(modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()) {

                WeatherToday(dailyWeather)

                Spacer(modifier = Modifier
                    .height(10.dp)
                    .padding(bottom = 20.dp))

                HourlyWeatherToday(hourlyWeather)

            }
        }
    }

    Divider(modifier = Modifier.height(1.dp),
        color = Color.LightGray,
        thickness = 1.dp)

}








