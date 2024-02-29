package com.weatheraanalyzerrrr.domain.repo.main

import android.location.Location
import com.weatheraanalyzerrrr.domain.entity.currentmodelresponse.CurrentModelResponse
import com.weatheraanalyzerrrr.domain.entity.hourlymodelresponse.Hourly
import com.weatheraanalyzerrrr.domain.entity.hourlymodelresponse.HourlyModelResponse


interface WeatherRepo {

    suspend fun getCurrentCityNameAndWeatherFromRemote(
        lat: Double,
        lon: Double,
        lang: String
    ): CurrentModelResponse

    suspend fun getCurrentCityNameAndWeatherFromRoom(): CurrentModelResponse

    suspend fun getHourlyWeatherFromRemote(
        lat: Double,
        lon: Double,
        lang: String
    ): HourlyModelResponse

    suspend fun getHourlyWeatherFromRoom(): List<Hourly>

    suspend fun insertCurrentWeatherDataBase(currentModelResponse: CurrentModelResponse)
    suspend fun insertHourlyWeatherDataBase(hourlyList: List<Hourly>)
    suspend fun deleteHourlyWeatherDataBase()

    suspend fun getTheCurrentLocation(): Location?


    suspend fun getCurrentWeatherByNameRequestFromRemote(
        cityName: String,
        lang: String
    ): CurrentModelResponse


}