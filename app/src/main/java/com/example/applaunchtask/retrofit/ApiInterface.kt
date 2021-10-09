package com.example.applaunchtask.retrofit

import com.example.applaunchtask.model.Weather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("data/2.5/onecall?lat=12.9082847623315&lon=77.65197822993314&units=imperial&appid=b143bb707b2ee117e62649b358207d3e")
    fun getWeatherData() : Call<Weather>
//        @Query("lat") lat : Double,
//    @Query("lon") lon : Double,
//    @Query("units") units : String,
//    @Query("appid") appid : String) : Call<Weather>

}