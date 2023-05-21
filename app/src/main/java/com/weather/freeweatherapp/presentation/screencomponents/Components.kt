package com.weather.freeweatherapp.presentation.screencomponents

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import com.weather.freeweatherapp.data.model.WeatherAPIResponse
import com.weather.freeweatherapp.presentation.bottommenu.BottomMenuItem
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.toSize
import com.weather.freeweatherapp.data.model.PlacesListItem


@Composable
fun BottomNavigation(controller: NavHostController?, modifier: Modifier = Modifier) {

    val bottomMenuItemsList = prepareBottomMenu()

    val selectedItem = remember {
        mutableStateOf("Breeds")
    }

    Box(modifier = Modifier.fillMaxSize()) {

        BottomNavigation(
            modifier = Modifier
                .padding(1.dp)
                .fillMaxWidth()
                .padding(bottom = 0.dp, top = 5.dp)
                .heightIn(70.dp, 90.dp)
                .align(alignment = Alignment.BottomCenter)
        ) {

            bottomMenuItemsList.forEach { menuItem ->
                // adding each item
                BottomNavigationItem(
                    selected = (selectedItem.value == menuItem.label),
                    onClick = {
                        selectedItem.value = menuItem.label
                        //                        val routeObjScreen = if (menuItem.label.equals("Weather")) WeatherScreen else SettingsScreen
                        //                        controller.navigate(routeObjScreen.ROUTE_NAME)
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    placesListItem: List<PlacesListItem>,
    cityCallback: (String,String,String) -> Unit
){

//    val keyboardController = LocalSoftwareKeyboardController.current
//    val focusRequester = remember {
//        FocusRequester()
//    }
//
//
//    val hiddenTextField = remember {
//        mutableStateOf(true)
//    }
//
//
//    val textFieldWidth = remember {
//        Animatable(initialValue = 0f)
//    }
//
//    LaunchedEffect(hiddenTextField.value) {
//
//        if (hiddenTextField.value){
//
//            textFieldWidth.animateTo(
//                0f,
//                animationSpec = tween(
//                    durationMillis = 400,
//                    easing = FastOutSlowInEasing
//                )
//            )
//        }
//
//        if (!hiddenTextField.value){
//
//            textFieldWidth.animateTo(
//                300f,
//                animationSpec = tween(
//                    durationMillis = 400,
//                    easing = FastOutSlowInEasing
//                )
//            )
//
//        }
//
//    }
//
//    OutlinedTextField(value = searchCity.value,
//        onValueChange = {
//            searchCity.value = it
//        },
//        modifier = Modifier
//            .padding(5.dp)
//            .focusRequester(focusRequester),
//        trailingIcon = {
//
//            if (searchCity.value.isNotBlank()){
//                Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear icon",
//                    modifier = Modifier
//                        .width(50.dp)
//                        .height(50.dp)
//                        .padding(10.dp)
//                        .clickable {
//                            searchCity.value = ""
//                        })
//
//            }
//
//        }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done),
//        keyboardActions = KeyboardActions (
//            onDone = {
//                keyboardController?.hide()
//                cityCallback(searchCity.value)
//                focusRequester.freeFocus()
//            })
//    )

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
fun WeeklyWeather(weatherApiResponse: WeatherAPIResponse, modifier: Modifier = Modifier){

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(top = 5.dp, bottom = 10.dp)
        .verticalScroll(rememberScrollState())) {

        repeat(weatherApiResponse.hourly.time.size/24){ index ->
            CreateSingleDay(weatherApiResponse = weatherApiResponse,index)
        }

    }


}

@Composable
fun CreateSingleDay(weatherApiResponse: WeatherAPIResponse, index: Int) {


}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AutoComplete(placesListItem: List<PlacesListItem>, placeCallBack: (String,String,String) -> Unit) {

    val keyboardController = LocalSoftwareKeyboardController.current

    var locationName by remember {
        mutableStateOf("")
    }

    var locationLatitude by remember {
        mutableStateOf("")
    }

    var locationLongitude by remember {
        mutableStateOf("")
    }

    val heightTextFields by remember {
        mutableStateOf(55.dp)
    }

    var textFieldSize by remember {
        mutableStateOf(Size.Zero)
    }

    var expanded by remember {
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
                        placeCallBack(locationName,"","")
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
                onSelect(title,lat,lng)
            }
            .padding(10.dp)
    ) {
        Text(text = title, fontSize = 16.sp)
    }

}


//@OptIn(ExperimentalComposeUiApi::class)
//@Composable
//fun TopBar(
//    searchCity: MutableState<String>,
//    modifier: Modifier = Modifier,
//    cityCallback: (String) -> Unit
//){
////    val  searchCity: MutableState<String> = remember {
////        mutableStateOf("")
////    }
////    val cityCallback : (String) -> Unit = {""}
//
//    val keyboardController = LocalSoftwareKeyboardController.current
//    val focusRequester = remember {
//        FocusRequester()
//    }
//
//
//    val hiddenTextField = remember {
//        mutableStateOf(true)
//    }
//
//
//    val textFieldWidth = remember {
//        Animatable(initialValue = 0f)
//    }
//
//    LaunchedEffect(hiddenTextField.value) {
//
//        if (hiddenTextField.value){
//
//            textFieldWidth.animateTo(
//                0f,
//                animationSpec = tween(
//                    durationMillis = 400,
//                    easing = FastOutSlowInEasing
//                )
//            )
//        }
//
//        if (!hiddenTextField.value){
//
//            textFieldWidth.animateTo(
//                300f,
//                animationSpec = tween(
//                    durationMillis = 400,
//                    easing = FastOutSlowInEasing
//                )
//            )
//
//        }
//
//    }
//
//    OutlinedTextField(value = searchCity.value,
//        onValueChange = {
//            searchCity.value = it
//        },
//        modifier = Modifier
//            .padding(5.dp)
//            .focusRequester(focusRequester),
//        trailingIcon = {
//
//            if (searchCity.value.isNotBlank()){
//                Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear icon",
//                    modifier = Modifier
//                        .width(50.dp)
//                        .height(50.dp)
//                        .padding(10.dp)
//                        .clickable {
//                            searchCity.value = ""
//                        })
//
//            }
//
//        }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done),
//        keyboardActions = KeyboardActions (
//            onDone = {
//                keyboardController?.hide()
//                cityCallback(searchCity.value)
//                focusRequester.freeFocus()
//            })
//    )
//
//    Divider(modifier = Modifier.padding(top = 5.dp, bottom = 20.dp, start = 5.dp, end = 5.dp),
//        color = Color.LightGray,
//        thickness = 1.dp,
//        startIndent = 2.dp)
//
//
//}

