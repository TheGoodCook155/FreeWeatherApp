package com.weather.freeweatherapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.weather.freeweatherapp.data.model.PlacesListItem
import com.weather.freeweatherapp.presentation.navigation.Navigation
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
                val navController = rememberNavController()

                val data = viewModel.data.value
                val places = viewModel.places

//                Log.d("retrieved_data", ": MainActivity: ${data}")


                App(
                    places,
                    viewModel,
                    navController
                )


            }
        }
    }

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    fun App(
        places: MutableState<List<PlacesListItem>>,
        viewModel: HourlyViewModel,
        navController: NavHostController
    ) {

        val searchCity = rememberSaveable(){
            mutableStateOf("")
        }

        val searchCityLat = rememberSaveable(){
            mutableStateOf("")
        }

        val searchCityLng = rememberSaveable(){
            mutableStateOf("")
        }

        val showSearchBar = rememberSaveable() {
            mutableStateOf(true)
        }

        val navBackStackEntry by navController.currentBackStackEntryAsState()

        val currentRoute = navBackStackEntry?.destination?.route

        Log.d("currentRoute", "App: ${currentRoute}")

        if (currentRoute.equals("WeatherScreen")){
            showSearchBar.value = true
        }else{
            showSearchBar.value = false
        }


        Scaffold(modifier = Modifier
            .fillMaxSize()
            .padding(1.dp),
                topBar = {

                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(Color.Cyan)
                        ,
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally) {

                        Icon(imageVector = Icons.Default.Home, contentDescription = "Logo")

                        if (showSearchBar.value){

                            TopBar(modifier = Modifier.fillMaxHeight(),places.value) { placeName, lat,lng ->
                                searchCity.value = placeName
                                searchCityLat.value = lat
                                searchCityLng.value = lng
                                viewModel.getHourlyWeather(latitude = searchCityLat.value, searchCityLng.value,1,null)
                            }

                        }

                    }

                },
                bottomBar = {

                    BottomNavigation(navController, modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.1f))

                }) {

            Log.d("place_callback", "App: retrieved values: place: ${searchCity.value}, latitude: ${searchCityLat.value}, longitude: ${searchCityLng.value}")
            Log.d("queried_data", "App: ${viewModel.data.value.data}")
                //navigation

            Column(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
                .background(Color.White)) {
                Navigation(navHostController = navController)
            }


        }

    }

}
