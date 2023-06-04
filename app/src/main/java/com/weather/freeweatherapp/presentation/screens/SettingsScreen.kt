package com.weather.freeweatherapp.presentation.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

    Column(modifier = Modifier.fillMaxSize().padding(start = 10.dp, end = 10.dp),
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

            //save to db
        }) {

            Text(text = "Save", style = TextStyle(color = Color.Black))

        }

    }

}