package com.weather.freeweatherapp.presentation.screencomponents

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.Animatable
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
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.toSize
import com.weather.freeweatherapp.R
import com.weather.freeweatherapp.data.model.daily.Daily
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
            backgroundColor = MaterialTheme.colors.secondary,
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
                    unselectedContentColor = MaterialTheme.colors.primaryVariant,
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



@SuppressLint("StateFlowValueCalledInComposition", "SuspiciousIndentation",
    "UnusedMaterialScaffoldPaddingParameter"
)
@Composable
fun WeatherToday(daily: Daily?) {


    val isExpanded = remember{
        mutableStateOf(true)
    }


    val windRotationValue = daily?.daily?.winddirection10mDominant?.get(0)?.toFloat() ?: 0.0.toFloat()

    val height = remember {
        androidx.compose.animation.core.Animatable(330f)
    }

    LaunchedEffect(isExpanded.value) {

        if (isExpanded.value){

            height.animateTo(
                330f,
                animationSpec = tween(
                    durationMillis = 400,
                    easing = FastOutSlowInEasing
                )
            )
        }

        if (!isExpanded.value){

            height.animateTo(
                0f,
                animationSpec = tween(
                    durationMillis = 400,
                    easing = FastOutSlowInEasing
                )
            )

        }

    }


            Card(modifier = Modifier
                .padding(start = 5.dp, end = 5.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
                backgroundColor = MaterialTheme.colors.primarySurface,
                contentColor = MaterialTheme.colors.onBackground
            ) {

                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(height = height.value.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.temperature),
                                contentDescription = "temperature icon",
                                modifier = Modifier
                                    .height(60.dp)
                                    .width(60.dp)
                                    .padding(start = 5.dp, end = 20.dp),
                                contentScale = ContentScale.Fit
                            )

                            Text(
                                text = "${daily?.daily?.temperature2mMin?.get(0)?: "n/a"} °C - ${daily?.daily?.temperature2mMax?.get(0)?: "n/a"} °C",
                                textAlign = TextAlign.Center
                            )
                        }


//                    uvIndexMax=[7.7
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.sunny),
                                contentDescription = "UV icon",
                                modifier = Modifier
                                    .height(60.dp)
                                    .width(60.dp)
                                    .padding(start = 5.dp, end = 20.dp),
                                contentScale = ContentScale.Fit
                            )

                            Text(
                                text = "${daily?.daily?.uvIndexMax?.get(0)?: "n/a"}",
                                textAlign = TextAlign.Center
                            )
                        }

//            precipitationHours=[8.0
//                    showersSum=[3.0
//                    winddirection10mDominant=[239

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.wet),
                                contentDescription = "precipitation hours",
                                modifier = Modifier
                                    .height(60.dp)
                                    .width(60.dp)
                                    .padding(start = 5.dp, end = 20.dp),
                                contentScale = ContentScale.Fit
                            )

                            Text(
                                text = "${daily?.daily?.precipitationHours?.get(0)?: "n/a"} h",
                                textAlign = TextAlign.Center
                            )
                        }

//            showersSum=[3.0
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.rain_and_thunder),
                                contentDescription = "Shower sum icon",
                                modifier = Modifier
                                    .height(60.dp)
                                    .width(60.dp)
                                    .padding(start = 5.dp, end = 20.dp),
                                contentScale = ContentScale.Fit
                            )

                            Text(
                                text = "${daily?.daily?.showersSum?.get(0)?: "n/a"} mm",
                                textAlign = TextAlign.Center
                            )
                        }

                        //                    winddirection10mDominant=[239
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.wind),
                                contentDescription = "Wind direction icon",
                                modifier = Modifier
                                    .height(60.dp)
                                    .width(60.dp)
                                    .padding(start = 5.dp, end = 20.dp),
                                contentScale = ContentScale.Fit
                            )

                            Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = "wind direction",
                                modifier = Modifier
                                    .rotate(windRotationValue!!)
                                    .height(50.dp)
                                    .width(50.dp))
                        }

//Column with rows ends here
                    }

                    Column {
                        val imageVector: ImageVector =
                            if (isExpanded.value == true) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown
                        Icon(imageVector = imageVector, contentDescription = "Expand/Colapse icon",
                            modifier = Modifier
                                .height(60.dp)
                                .width(60.dp)
                                .clickable {
                                    Log.d("collapse_event", "WeatherToday: Clicked!")
                                    isExpanded.value = !isExpanded.value
                                })
                    }
                    //outer column ends here
                }
        }

}

//@Preview
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HourlyWeatherToday(weatherApiResponse: WeatherAPIResponse?){

    //Alt + 248 °

        val listState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()

    Log.d("hourly_Weather", "HourlyWeatherToday: data size: ${weatherApiResponse?.hourly?.time?.size}")

    Scaffold(backgroundColor = MaterialTheme.colors.background) {

        if (weatherApiResponse == null){
            Log.d("hourly_Weather", "HourlyWeatherToday: weatherApiResponse null block")
            Card(modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
                backgroundColor = MaterialTheme.colors.primarySurface,
                contentColor = MaterialTheme.colors.onBackground,
                shape = RoundedCornerShape(10.dp),
                elevation = 5.dp
            ) {

                Column {

                    Column(modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth()
                        .fillMaxHeight(0.2f),
                        horizontalAlignment = Alignment.CenterHorizontally) {

                        val convertedDate = "n/a"
                        //hour
                        Text(text = "${convertedDate}",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold)
                    }
                    Column(modifier = Modifier
                        .padding(start = 15.dp, bottom = 5.dp, end = 5.dp, top = 5.dp)
                        .fillMaxWidth()
                        .fillMaxHeight(0.8f),
                        horizontalAlignment = Alignment.Start) {

                        Text(text = "Temperature: n/a")

                        Text("Rain: n/a")

                        Text(text = "Relative humidity: n/a" )

                        Text(text = "Snowfall: n/a")

                        Text(text = "Wind speed: n/a")

                        //wind direction
                        Row(modifier = Modifier.padding(end = 20.dp),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "Wind direction: ")

                            Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = "wind direction",
                                modifier = Modifier
                                    .height(50.dp)
                                    .width(50.dp))
                        }
                    }
                }
            }
        }else{
            LazyColumn(state = listState){

                //scroll to first item when the view recomposes
                coroutineScope.launch {
                    listState.scrollToItem(0)
                }

                //weatherApiResponse.hourly.time.size
                items(weatherApiResponse.hourly?.time?.size!!){ index ->

                    val windRotationValue = weatherApiResponse.hourly.winddirection180m.get(index).toFloat()?: 0.0.toFloat()

                    Card(modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth()
                        .wrapContentHeight(),
                        backgroundColor = MaterialTheme.colors.primarySurface,
                        contentColor = MaterialTheme.colors.onBackground,
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

                                Text(text = "Temperature: ${weatherApiResponse.hourly.temperature2m.get(index)?: "n/a"} °C")

                                Text("Rain: ${weatherApiResponse.hourly.rain.get(index)?: "n/a"}mm")

                                Text(text = "Relative humidity: ${
                                    weatherApiResponse.hourly.relativehumidity2m.get(index)?: "n/a"} %" )

                                Text(text = "Snowfall: ${weatherApiResponse.hourly.snowfall.get(index)?: "n/a"} mm")

                                Text(text = "Wind speed: ${weatherApiResponse.hourly.windspeed10m.get(index)?: "n/a"} km/h")

                                //wind direction
                                Row(modifier = Modifier.padding(end = 20.dp),
                                    horizontalArrangement = Arrangement.SpaceAround,
                                    verticalAlignment = Alignment.CenterVertically) {
                                    Text(text = "Wind direction: ")

                                    Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = "wind direction",
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

}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
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
                .padding(5.dp)
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
                        backgroundColor = MaterialTheme.colors.background,
                        contentColor = Color.Black,
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
                                        placeCallBack(locationName,locationLatitude,locationLongitude)
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
                                        placeCallBack(locationName,locationLatitude,locationLongitude)
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




