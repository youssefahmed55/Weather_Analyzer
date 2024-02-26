package com.weatheraanalyzerrrr.data.remote.main

import com.weatheraanalyzerrrr.data.BuildConfig
import com.weatheraanalyzerrrr.data.Constants.excludeHourly
import com.weatheraanalyzerrrr.data.Constants.units
import com.weatheraanalyzerrrr.domain.entity.currentmodelresponse.CurrentModelResponse
import com.weatheraanalyzerrrr.domain.entity.hourlymodelresponse.HourlyModelResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface MainApiService {

    //Get Current City Name And Weather
    @GET("weather?units=$units&appid=${BuildConfig.API_KEY}")
    suspend fun getCurrentCityNameAndWeatherRequest(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("lang") lang: String
    ): CurrentModelResponse

    //Get Weather Degree Hourly
    @GET("onecall?units=$units&appid=${BuildConfig.API_KEY}&exclude=$excludeHourly")
    suspend fun getWeatherHourlyRequest(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("lang") lang: String
    ): HourlyModelResponse


    //Get Current Weather By City Name
    @GET("weather?units=$units&appid=${BuildConfig.API_KEY}")
    suspend fun getCurrentWeatherByNameRequest(
        @Query("q") cityName: String,
        @Query("lang") lang: String
    ): CurrentModelResponse

}