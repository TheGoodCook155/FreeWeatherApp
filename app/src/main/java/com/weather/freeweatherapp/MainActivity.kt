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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.weather.freeweatherapp.data.model.PlacesListItem
import com.weather.freeweatherapp.presentation.screencomponents.BottomNavigation
import com.weather.freeweatherapp.presentation.screencomponents.TopBar
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
                val places = viewModel.places

//                Log.d("retrieved_data", ": MainActivity: ${data}")


                App(
                    places
                )


            }
        }
    }

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    fun App(places: MutableState<List<PlacesListItem>>) {

        val searchCity = remember{
            mutableStateOf("")
        }

        val searchCityLat = remember{
            mutableStateOf("")
        }

        val searchCityLng = remember{
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

                        TopBar(modifier = Modifier.fillMaxHeight(0.1f),places.value) { placeName, lat,lng ->
                            searchCity.value = placeName
                            searchCityLat.value = lat
                            searchCityLng.value = lng
                        }
                    }

                },
                bottomBar = {

                    BottomNavigation(null, modifier = Modifier.fillMaxHeight(0.1f))


                }) {

            Log.d("place_callback", "App: retrieved values: place: ${searchCity.value}, latitude: ${searchCityLat.value}, longitude: ${searchCity.value}")

                //navigation
//            WeeklyWeather(weatherApiResponse, modifier = Modifier.fillMaxHeight(0.8f))


        }

    }

}
