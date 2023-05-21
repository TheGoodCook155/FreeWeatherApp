package com.weather.freeweatherapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.weather.freeweatherapp.data.wrapper.NetworkResult
import com.weather.freeweatherapp.presentation.screencomponents.BottomNavigation
import com.weather.freeweatherapp.presentation.screencomponents.TopBar
import com.weather.freeweatherapp.presentation.screencomponents.WeeklyWeather
import com.weather.freeweatherapp.presentation.viewmodel.HourlyViewModel
import com.weather.freeweatherapp.ui.theme.FreeWeatherAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FreeWeatherAppTheme {

                val viewModel: HourlyViewModel = viewModel()

                val data = viewModel.data.value

                Log.d("retrieved_data", ": MainActivity: ${data}")


//                App(
//                    weatherApiResponse
//                )


            }
        }
    }

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    fun App() {

        val searchCity = remember{
            mutableStateOf("")
        }

        Scaffold(modifier = Modifier
            .fillMaxSize()
            .padding(1.dp),
                topBar = {

                    Column(modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally) {

                        Icon(imageVector = Icons.Default.Home, contentDescription = "Logo")

                        TopBar(searchCity, modifier = Modifier.fillMaxHeight(0.1f)) {

                        }
                    }

                },
                bottomBar = {

                    BottomNavigation(null, modifier = Modifier.fillMaxHeight(0.1f))


                }) {

                //navigation
//            WeeklyWeather(weatherApiResponse, modifier = Modifier.fillMaxHeight(0.8f))


        }

    }

}
