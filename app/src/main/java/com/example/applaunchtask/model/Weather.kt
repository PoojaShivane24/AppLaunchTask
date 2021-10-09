package com.example.applaunchtask.model

data class Weather(val daily : List<Daily>)

data class Daily(val dt : Long, val temp : Temp, val humidity : Double, val wind_speed : Double, val weather : List<WeatherType>)

data class WeatherType(val description : String)

data class Temp(val day : Double, val min : Double, val max : Double, val night : Double, val eve : Double, val morn : Double)
