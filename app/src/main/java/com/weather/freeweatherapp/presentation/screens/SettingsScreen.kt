package com.weather.freeweatherapp.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun SettingsScreen(){

    Column(modifier = Modifier.wrapContentHeight().fillMaxWidth().background(Color.Yellow)) {
        Text(text = "Settings Screen")
    }


}