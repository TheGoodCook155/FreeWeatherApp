package com.weather.freeweatherapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.weather.freeweatherapp.data.model.daily.Daily
import com.weather.freeweatherapp.data.model.hourly.WeatherAPIResponse
import com.weather.freeweatherapp.presentation.screens.SettingsScreen
import com.weather.freeweatherapp.presentation.screens.WeatherScreen
import com.weather.freeweatherapp.presentation.viewmodel.AppViewModel


interface Destinations{
    val route: String
    val isWeatherScreen: Boolean
}

object WeatherScreen: Destinations{
    override val route: String
        get() = "WeatherScreen"
    override val isWeatherScreen: Boolean
        get() = true


}

object SettingsScreen: Destinations{
    override val route: String
        get() = "SettingsScreen"
    override val isWeatherScreen: Boolean
        get() = false

}


@Composable
fun Navigation(
    navHostController: NavHostController,
    viewModel: AppViewModel,
){

    NavHost(navController = navHostController, startDestination = WeatherScreen.route){

        composable(WeatherScreen.route){
            WeatherScreen(viewModel = viewModel)
        }

        composable(SettingsScreen.route){
            SettingsScreen(viewModel = viewModel)
        }

    }


}