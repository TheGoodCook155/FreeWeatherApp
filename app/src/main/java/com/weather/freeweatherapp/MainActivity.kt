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
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.weather.freeweatherapp.data.model.places.PlacesListItem
import com.weather.freeweatherapp.presentation.navigation.Navigation
import com.weather.freeweatherapp.presentation.navigation.WeatherScreen
import com.weather.freeweatherapp.presentation.screencomponents.BottomNavigation
import com.weather.freeweatherapp.presentation.screencomponents.TopBar
import com.weather.freeweatherapp.presentation.screens.WeatherScreen
import com.weather.freeweatherapp.presentation.viewmodel.AppViewModel
import com.weather.freeweatherapp.ui.theme.FreeWeatherAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FreeWeatherAppTheme {

                val viewModel: AppViewModel = viewModel()
                val navController = rememberNavController()

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
        places: State<List<PlacesListItem>>,
        viewModel: AppViewModel,
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
                        .background(MaterialTheme.colors.background)
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
                                viewModel.getDailyWeather(latitude = searchCityLat.value, longitude = searchCityLng.value,null)
                                navController.navigate(WeatherScreen.route)
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
//            Log.d("queried_data", "App: ${viewModel.dataHourly.collectAsState()}")
                //navigation

            Column(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
                .background(MaterialTheme.colors.background)) {
                Navigation(navHostController = navController, viewModel)
            }


        }

    }

}
