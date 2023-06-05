package com.weather.freeweatherapp.presentation.screens

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.weather.freeweatherapp.data.db.entity.DefaultLocation
import com.weather.freeweatherapp.presentation.screencomponents.AutoComplete
import com.weather.freeweatherapp.presentation.viewmodel.AppViewModel

@Composable
fun SettingsScreen(viewModel: AppViewModel){
    
    val placesListItem = viewModel.places.value

    val placeNameStr = rememberSaveable() {
        mutableStateOf("")
    }

    val latStr = rememberSaveable() {
        mutableStateOf("")
    }

    val lngStr = rememberSaveable() {
        mutableStateOf("")
    }

    val defaultLocation = viewModel.getDefaultLocation()

    Log.d("default_location", "SettingsScreen: defaultLocation: ${defaultLocation}")

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(start = 10.dp, end = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
        
        Text(text = "Enter default place", style = TextStyle(fontSize = 20.sp, color = Color.Black))

        AutoComplete(placesListItem = placesListItem){placeName,lat,lng ->
            Log.d("place_callback", "AutoComplete: ${placeName}, ${lat}, ${lng}")
            placeNameStr.value = placeName
            latStr.value = lat
            lngStr.value = lng
        }
            
        Button(onClick = {

            Log.d("place_callback", "Button event: ${placeNameStr.value}, ${latStr.value}, ${lngStr.value}")
            val defaultLocation = DefaultLocation(0,placeNameStr.value,latStr.value,lngStr.value)
            viewModel.insertDefaultLocation(defaultLocation)

            //save to db
        }) {

            Text(text = "Save", style = TextStyle(color = Color.Black))

        }

        val text = defaultLocation.name.toString()

        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {

            Box(modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .border(width = 2.dp, color = Color.Black, shape = RectangleShape)) {

                Text(text = text,
                    modifier = Modifier.padding(20.dp),
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium)

            }

        }

    }

}