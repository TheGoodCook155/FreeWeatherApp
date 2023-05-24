package com.weather.freeweatherapp.utils

fun convertDate(input: String): List<String>{

    //2023-05-24T02:00

    val time = input.substring(11,input.length) + "h"
    val date = input.substring(0,10)
    val dateArr = date.split("-")

    val dateRecreated = "${dateArr[2]}:${dateArr[1]}:${dateArr[0]}"

    return listOf(time,dateRecreated)

}