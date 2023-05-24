package com.weather.freeweatherapp.presentation.screencomponents

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.List
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.weather.freeweatherapp.data.model.hourly.WeatherAPIResponse
import com.weather.freeweatherapp.presentation.bottommenu.BottomMenuItem
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.toSize
import com.weather.freeweatherapp.data.model.places.PlacesListItem
import com.weather.freeweatherapp.presentation.navigation.SettingsScreen
import com.weather.freeweatherapp.presentation.navigation.WeatherScreen
import com.weather.freeweatherapp.utils.convertDate
import kotlinx.coroutines.launch


@Composable
fun BottomNavigation(controller: NavHostController, modifier: Modifier = Modifier) {

    val bottomMenuItemsList = prepareBottomMenu()

    val selectedItem = remember {
        mutableStateOf("Weather")
    }

    Box(modifier = Modifier
        .wrapContentHeight()
        .fillMaxWidth()) {

        BottomNavigation(
            modifier = Modifier
                .padding(1.dp)
                .fillMaxWidth()
                .padding(bottom = 0.dp, top = 5.dp)
                .heightIn(70.dp, 90.dp)
                .align(alignment = Alignment.BottomCenter),
            elevation = 2.dp,
            backgroundColor = MaterialTheme.colors.onBackground,
        ) {

            bottomMenuItemsList.forEach { menuItem ->
                // adding each item
                BottomNavigationItem(
                    selected = (selectedItem.value == menuItem.label),
                    onClick = {
                        selectedItem.value = menuItem.label
                        val routeObjScreen = if (menuItem.label.equals("Weather")) WeatherScreen else SettingsScreen
                        controller.navigate(routeObjScreen.route)
                    },

                    selectedContentColor = Color.White,
                    unselectedContentColor = MaterialTheme.colors.secondary,
                    icon = {
                        val icon =
                            if (menuItem.iconMapper.equals("Icons.Outlined.List")) Icons.Outlined.List else Icons.Outlined.Info

                        Icon(imageVector = icon, contentDescription = "Icon")

                    },
                    modifier = Modifier.padding(bottom = 5.dp),
                    label = {
                        Text(
                            text = menuItem.label,
                            fontSize = 16.sp
                        )
                    },
                    enabled = true
                )
            }
        }

    }

}



fun prepareBottomMenu(): List<BottomMenuItem> {

    val bottomMenuItemsList = arrayListOf<BottomMenuItem>()

    bottomMenuItemsList.add(BottomMenuItem(label = "Weather", iconMapper = "Icons.Outlined.List"))
    bottomMenuItemsList.add(BottomMenuItem(label = "Settings", iconMapper = "Icons.Outlined.Info"))

    return bottomMenuItemsList
}

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    placesListItem: List<PlacesListItem>,
    cityCallback: (String,String,String) -> Unit
){

    AutoComplete(placesListItem = placesListItem){placeName,lat,lng ->
        Log.d("place_callback", "TopBar: ${placeName}, ${lat}, ${lng}")
        cityCallback(placeName,lat,lng)
    }

    Divider(modifier = Modifier.padding(top = 5.dp, bottom = 20.dp, start = 5.dp, end = 5.dp),
        color = Color.LightGray,
        thickness = 1.dp,
        startIndent = 2.dp)


}


@Composable
fun CreateSingleDayCard(weatherApiResponse: WeatherAPIResponse, index: Int) {


}

//@Preview
@Composable
fun HourlyWeatherToday(weatherApiResponse: WeatherAPIResponse){

    //Alt + 248 °

    /*
    relatve humidity
snowfall
temperature
time
winddirection
windspeed
     */
    /*
    NetworkResult(data=WeatherAPIResponse(elevation=655.0, generationtimeMs=0.7129907608032227, hourly=Hourly(rain=[0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0], relativehumidity2m=[92, 93, 94, 94, 94, 92, 84, 79, 71, 58, 53, 49, 44, 40, 40, 42, 43, 55, 68, 71, 80, 88, 87, 88, 90, 91, 91, 89, 89, 88, 78, 73, 65, 59, 53, 45, 42, 41, 43, 45, 62, 70, 73, 78, 85, 87, 86, 89, 93, 94, 95, 96, 96, 95, 87, 84, 76, 70, 62, 58, 53, 49, 49, 58, 67, 71, 78, 83, 89, 91, 92, 93, 93, 92, 92, 92, 92, 91, 81, 73, 66, 61, 56, 53, 49, 53, 61, 68, 72, 75, 79, 85, 92, 96, 95, 91, 88, 87, 86, 84, 82, 79, 75, 70, 63, 59, 58, 60, 61, 62, 62, 65, 71, 79, 85, 87, 86, 86, 86, 87, 87, 87, 87, 86, 83, 80, 77, 69, 66, 63, 60, 57, 55, 55, 57, 60, 66, 74, 81, 85, 87, 89, 90, 91, 92, 94, 95, 94, 88, 78, 70, 64, 60, 56, 52, 50, 47, 44, 42, 42, 47, 55, 61, 63, 63, 64, 66, 68], snowfall=[0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0], temperature2m=[12.3, 11.8, 11.4, 11.0, 10.8, 12.3, 14.3, 15.9, 17.5, 19.2, 20.2, 21.1, 21.7, 22.1, 21.7, 21.4, 21.0, 19.7, 17.6, 16.8, 15.2, 13.8, 13.4, 13.0, 12.5, 12.0, 11.8, 11.8, 11.9, 13.2, 15.7, 17.5, 19.3, 21.0, 21.5, 22.2, 22.8, 22.7, 22.6, 22.2, 20.3, 18.8, 17.9, 16.8, 15.4, 14.7, 14.6, 13.9, 13.1, 12.9, 12.5, 12.0, 12.0, 13.7, 15.8, 17.4, 18.8, 19.7, 20.5, 21.1, 21.7, 22.0, 21.5, 20.2, 19.3, 18.6, 17.5, 16.6, 15.7, 15.2, 14.6, 14.0, 13.6, 13.2, 12.8, 12.6, 12.6, 14.2, 16.2, 17.6, 18.8, 19.7, 20.5, 21.3, 21.5, 21.1, 20.3, 19.5, 18.8, 18.1, 17.3, 16.4, 15.5, 14.7, 14.2, 13.7, 13.4, 13.2, 13.1, 13.4, 14.2, 15.5, 16.6, 17.6, 18.4, 19.2, 19.8, 20.2, 20.4, 20.3, 19.9, 19.3, 18.4, 17.4, 16.5, 15.9, 15.5, 15.1, 14.6, 14.1, 13.7, 13.3, 12.9, 13.0, 13.9, 15.2, 16.2, 17.5, 18.5, 19.3, 20.1, 20.7, 21.1, 21.3, 21.3, 20.9, 19.9, 18.5, 17.3, 16.5, 15.9, 15.3, 14.7, 14.3, 13.8, 13.2, 12.6, 12.5, 13.3, 14.6, 15.8, 16.7, 17.5, 18.2, 19.0, 19.7, 20.2, 20.4, 20.5, 20.3, 19.6, 18.7, 17.8, 17.1, 16.5, 15.9, 15.2, 14.6], time=[2023-05-23T00:00, 2023-05-23T01:00, 2023-05-23T02:00, 2023-05-23T03:00, 2023-05-23T04:00, 2023-05-23T05:00, 2023-05-23T06:00, 2023-05-23T07:00, 2023-05-23T08:00, 2023-05-23T09:00, 2023-05-23T10:00, 2023-05-23T11:00, 2023-05-23T12:00, 2023-05-23T13:00, 2023-05-23T14:00, 2023-05-23T15:00, 2023-05-23T16:00, 2023-05-23T17:00, 2023-05-23T18:00, 2023-05-23T19:00, 2023-05-23T20:00, 2023-05-23T21:00, 2023-05-23T22:00, 2023-05-23T23:00, 2023-05-24T00:00, 2023-05-24T01:00, 2023-05-24T02:00, 2023-05-24T03:00
     */

//    val weatherAPIResponse = WeatherAPIResponse(
//        elevation=655.0,
//        generationtimeMs=0.7129907608032227,
//        hourly= Hourly(rain = listOf(12.3,12.3,12.3,12.3,12.3,12.3,12.3,12.3,12.3,12.3),
//            relativehumidity2m = listOf(55,55,55,55,55,55,55,55,55,),
//            snowfall = listOf(0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1),
//            temperature2m= listOf( 12.3, 11.8, 11.4, 11.0, 10.8, 12.3, 14.3, 15.9),
//            time = listOf("2023-05-23T00:00", "2023-05-23T01:00", "2023-05-23T02:00", "2023-05-23T03:00", "2023-05-23T04:00", "2023-05-23T05:00", "2023-05-23T06:00", "2023-05-23T07:00"), winddirection180m = listOf(12,200,340,67,237,45,120,155,188), windspeed10m = listOf(12.0,200.0,340.4,67.2,237.4,45.4,120.4,155.4,188.4)
//        , windspeed180m = listOf(12.1,12.3, 11.8, 11.4, 11.0, 10.8, 12.3, 14.3, 15.9)
//        ),
//        hourlyUnits = HourlyUnits("","","","","","","",""),
//        latitude = 12.2,
//        longitude = 13.3,
//        timezone = "",
//        timezoneAbbreviation = "",
//        utcOffsetSeconds = 1,
//
//    )

    /*
                                Text(text = "Temperature: ${
                                weatherApiResponse.hourly.temperature2m.get(
                                    index
                                )}°C")

                            Text("Rain: ${weatherApiResponse.hourly.rain.get(index)}mm")

                            Text(text = "Relative humidity: ${
                                weatherApiResponse.hourly.relativehumidity2m.get(
                                    index
                                )}%" )

                            Text(text = "Snowfall: ${weatherApiResponse.hourly.snowfall.get(index)}mm")

                            Text(text = "Wind speed: ${weatherApiResponse.hourly.windspeed10m.get(index)}km/h")
     */


    if (weatherApiResponse == null) {
        Box{}
    }else{

        val listState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()

        LazyColumn(state = listState){

            //scroll to first item when the view recomposes
            coroutineScope.launch {
                listState.scrollToItem(0)
            }

            items(weatherApiResponse.hourly.time.size){ index ->

                val windRotationValue = weatherApiResponse.hourly.winddirection180m.get(index).toFloat()


                Card(modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth()
                    .wrapContentHeight(),
                    shape = RoundedCornerShape(10.dp),
                    elevation = 5.dp
                ) {

                    Column {

                        Column(modifier = Modifier
                            .padding(top = 10.dp)
                            .fillMaxWidth()
                            .fillMaxHeight(0.2f),
                            horizontalAlignment = Alignment.CenterHorizontally) {

                            val convertedDate = convertDate(weatherApiResponse.hourly.time.get(index))
                            //hour
                            Text(text = "${convertedDate.get(1)}, ${convertedDate.get(0)}",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold)
                        }
                        Column(modifier = Modifier
                            .padding(start = 15.dp, bottom = 5.dp, end = 5.dp, top = 5.dp)
                            .fillMaxWidth()
                            .fillMaxHeight(0.8f),
                            horizontalAlignment = Alignment.Start) {

                            Text(text = "Temperature: ${
                                weatherApiResponse.hourly.temperature2m.getOrElse(
                                    index,{0}
                                )}°C")

                            Text("Rain: ${weatherApiResponse.hourly.rain.getOrElse(index,{0})}mm")

                            Text(text = "Relative humidity: ${
                                weatherApiResponse.hourly.relativehumidity2m.getOrElse(
                                    index,{0}
                                )}%" )

                            Text(text = "Snowfall: ${weatherApiResponse.hourly.snowfall.getOrElse(index,{0})}mm")

                            Text(text = "Wind speed: ${weatherApiResponse.hourly.windspeed10m.getOrElse(index,{0})}km/h")

                            //wind direction
                            Row(modifier = Modifier.padding(end = 20.dp),
                                horizontalArrangement = Arrangement.SpaceAround,
                                verticalAlignment = Alignment.CenterVertically) {
                                Text(text = "Wind direction: ")

                                Icon(imageVector = Icons.Default.KeyboardArrowUp, contentDescription = "wind direction",
                                    modifier = Modifier
                                        .rotate(windRotationValue)
                                        .height(50.dp)
                                        .width(50.dp))
                            }
                        }
                    }
                }

            }

        }

    }


}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AutoComplete(placesListItem: List<PlacesListItem>, placeCallBack: (String, String, String) -> Unit) {

    val keyboardController = LocalSoftwareKeyboardController.current

    var locationName by rememberSaveable() {
        mutableStateOf("")
    }

    var locationLatitude by rememberSaveable() {
        mutableStateOf("")
    }

    var locationLongitude by rememberSaveable() {
        mutableStateOf("")
    }

    val heightTextFields by remember() {
        mutableStateOf(55.dp)
    }

    var textFieldSize by remember() {
        mutableStateOf(Size.Zero)
    }

    var expanded by rememberSaveable() {
        mutableStateOf(false)
    }
    val interactionSource = remember {
        MutableInteractionSource()
    }

    // Location Field
    Column(
        modifier = Modifier
            .padding(30.dp)
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = {
                    expanded = false
                }
            )
    ) {

        Text(
            modifier = Modifier.padding(start = 3.dp, bottom = 2.dp),
            text = "Location",
            fontSize = 16.sp,
            color = Color.Black,
            fontWeight = FontWeight.Medium
        )

        Column(modifier = Modifier.fillMaxWidth()) {

            Row(modifier = Modifier.fillMaxWidth()) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(heightTextFields)
                        .border(
                            width = 1.8.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(15.dp)
                        )
                        .onGloballyPositioned { coordinates ->
                            textFieldSize = coordinates.size.toSize()
                        },
                    value = locationName,
                    onValueChange = {
                        locationName = it
//                        placeCallBack(locationName,"","")
                        expanded = true
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = Color.Black
                    ),
                    textStyle = TextStyle(
                        color = Color.Black,
                        fontSize = 16.sp
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    singleLine = true,
                    trailingIcon = {
                        IconButton(onClick = { expanded = !expanded }) {
                            Icon(
                                modifier = Modifier.size(24.dp),
                                imageVector = Icons.Rounded.KeyboardArrowDown,
                                contentDescription = "arrow",
                                tint = Color.Black
                            )
                        }
                    },
                    keyboardActions = KeyboardActions(
                        onDone = {
                            placeCallBack(locationName,locationLatitude,locationLongitude)
                            Log.d("place_callback", "AutoComplete: onDone called, callback activated: category: name: ${locationName}, latitude:  ${locationLatitude}, longitude: ${locationLongitude}")
                            keyboardController?.hide()
                        }
                    )
                )
            }

            AnimatedVisibility(visible = expanded) {
                Card(
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                        .width(textFieldSize.width.dp),
                    elevation = 15.dp,
                    shape = RoundedCornerShape(10.dp)
                ) {

                    LazyColumn(
                        modifier = Modifier.heightIn(max = 150.dp),
                    ) {

                        if (locationName.isNotEmpty()) {
                            items(
                                placesListItem.filter {
                                    it.name.lowercase()
                                        .contains(locationName.lowercase()) || it.name.lowercase()
                                        .contains("others")
                                }
                            ) {
                                PlaceItems(title = "${it.name} - ${it.country}", lat = it.lat, lng = it.lng) { title,lat,lng ->
                                    locationName = title
                                    expanded = false
                                    locationLatitude = lat
                                    locationLongitude = lng
                                }
                            }
                        } else {
                            items(
                                placesListItem
                            ) {
                                PlaceItems(title = "${it.name} - ${it.country}", lat = it.lat, lng = it.lng) { title,lat,lng ->
                                    locationName = title
                                    expanded = false
                                    locationLatitude = lat
                                    locationLongitude = lng
                                }
                            }
                        }

                    }

                }
            }

        }

    }


}

@Composable
fun PlaceItems(
    title: String,
    lat: String,
    lng: String,
    onSelect: (String,String,String) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onSelect(title, lat, lng)
            }
            .padding(10.dp)
    ) {
        Text(text = title, fontSize = 16.sp)
    }

}




