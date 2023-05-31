package com.weather.freeweatherapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.Settings.ACTION_WIFI_SETTINGS
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.weather.freeweatherapp.data.constants.Constants
import com.weather.freeweatherapp.data.model.places.PlacesListItem
import com.weather.freeweatherapp.presentation.navigation.Navigation
import com.weather.freeweatherapp.presentation.navigation.WeatherScreen
import com.weather.freeweatherapp.presentation.screencomponents.BottomNavigation
import com.weather.freeweatherapp.presentation.screencomponents.TopBar
import com.weather.freeweatherapp.presentation.screens.WeatherScreen
import com.weather.freeweatherapp.presentation.viewmodel.AppViewModel
import com.weather.freeweatherapp.ui.theme.FreeWeatherAppTheme
import com.weather.freeweatherapp.utils.networkstatus.ConnectionState
import com.weather.freeweatherapp.utils.networkstatus.connectivityState
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

                App(
                    places,
                    viewModel,
                    navController
                )


            }
        }
    }

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter", "SuspiciousIndentation")
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

        val connection by connectivityState()
        val isConnected = connection === ConnectionState.Available

        viewModel.isConnected.value = isConnected

        val scaffoldState = rememberScaffoldState()

        val context = LocalContext.current


            Scaffold(modifier = Modifier
                .fillMaxSize()
                .padding(1.dp),
                scaffoldState = scaffoldState,
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
                                viewModel.getHourlyWeather(latitude = searchCityLat.value, searchCityLng.value,1,Constants.HOURLY_PARAM)
                                viewModel.getDailyWeather(latitude = searchCityLat.value, longitude = searchCityLng.value,Constants.DAILY_PARAM)
                                navController.navigate(WeatherScreen.route)
                            }

                        }

                    }

                },
                bottomBar = {

                    var isSnackbarShown by remember {
                        mutableStateOf(false)
                    }

                    if (isConnected){
                        isSnackbarShown = true
                    }else{
                        isSnackbarShown = false
                    }

                    Column(verticalArrangement = Arrangement.Bottom) {

                        if (!isConnected){

                        LaunchedEffect(!isConnected){

                            if (isSnackbarShown == false){

                                scaffoldState.snackbarHostState.showSnackbar(
                                    message = "Check your internet connection",
                                    actionLabel = "Go to Settings",
                                    duration = SnackbarDuration.Indefinite
                                ).run {
                                    val wifiIntent = Intent(ACTION_WIFI_SETTINGS)
                                    context.startActivity(wifiIntent)
                                    scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                                    isSnackbarShown = true
                                }

                            }

                        }

                        }else{
                            scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                        }

                        BottomNavigation(navController, modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.1f))

                    }


                }) {

                Log.d("place_callback", "App: retrieved values: place: ${searchCity.value}, latitude: ${searchCityLat.value}, longitude: ${searchCityLng.value}")
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
