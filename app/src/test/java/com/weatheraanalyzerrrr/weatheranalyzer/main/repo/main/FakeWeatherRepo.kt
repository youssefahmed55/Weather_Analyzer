package com.weatheraanalyzerrrr.weatheranalyzer.main.repo.main

import android.location.Location
import com.weatheraanalyzerrrr.domain.entity.currentmodelresponse.Coord
import com.weatheraanalyzerrrr.domain.entity.currentmodelresponse.CurrentModelResponse
import com.weatheraanalyzerrrr.domain.entity.hourlymodelresponse.Hourly
import com.weatheraanalyzerrrr.domain.entity.hourlymodelresponse.HourlyModelResponse
import com.weatheraanalyzerrrr.domain.repo.main.WeatherRepo
import kotlinx.coroutines.flow.MutableStateFlow

class FakeWeatherRepo : WeatherRepo {

    val currentWeatherData = MutableStateFlow(CurrentModelResponse())
    val hourlyListData = MutableStateFlow<List<Hourly>>(emptyList())

    override suspend fun getCurrentCityNameAndWeatherFromRemote(
        lat: Double,
        lon: Double,
        lang: String
    ): CurrentModelResponse {
        return CurrentModelResponse(coord = Coord(lat,lon))
    }

    override suspend fun getCurrentWeatherByNameRequestFromRemote(
        cityName: String,
        lang: String
    ): CurrentModelResponse {
        return CurrentModelResponse(name = cityName)
    }


    override suspend fun getHourlyWeatherFromRemote(
        lat: Double,
        lon: Double,
        lang: String
    ): HourlyModelResponse {
        return HourlyModelResponse(lat=lat, lon = lon)
    }
    override suspend fun getCurrentCityNameAndWeatherFromRoom(): CurrentModelResponse {
        return currentWeatherData.value
    }
    override suspend fun getHourlyWeatherFromRoom(): List<Hourly> {
        return hourlyListData.value
    }

    override suspend fun insertCurrentWeatherDataBase(currentModelResponse: CurrentModelResponse) {
        currentWeatherData.value = currentModelResponse
    }

    override suspend fun insertHourlyWeatherDataBase(hourlyList: List<Hourly>) {
        hourlyListData.value = hourlyList
    }

    override suspend fun deleteHourlyWeatherDataBase() {
        hourlyListData.value = emptyList()
    }

    override suspend fun getTheCurrentLocation(): Location? {
        val location = Location("London")
        location.latitude = -0.1257
        location.longitude = 51.5085
        return location
    }


}