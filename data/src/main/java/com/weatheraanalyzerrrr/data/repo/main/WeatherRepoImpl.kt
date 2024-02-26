package com.weatheraanalyzerrrr.data.repo.main

import com.weatheraanalyzerrrr.data.remote.main.MainApiService
import com.weatheraanalyzerrrr.data.room.main.WeatherDao
import com.weatheraanalyzerrrr.domain.entity.currentmodelresponse.CurrentModelResponse
import com.weatheraanalyzerrrr.domain.entity.hourlymodelresponse.Hourly
import com.weatheraanalyzerrrr.domain.repo.main.WeatherRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepoImpl(private val mainApiService: MainApiService, private val weatherDao: WeatherDao) :
    WeatherRepo {
    //Remote
    override suspend fun getCurrentCityNameAndWeatherFromRemote(
        lat: Double,
        lon: Double,
        lang: String
    ) = withContext(Dispatchers.IO) {
        return@withContext mainApiService.getCurrentCityNameAndWeatherRequest(lat, lon, lang)
    }

    override suspend fun getCurrentCityNameAndWeatherFromRoom() = withContext(Dispatchers.IO) {
        return@withContext weatherDao.getCurrentWeather()
    }

    override suspend fun getHourlyWeatherFromRemote(
        lat: Double,
        lon: Double,
        lang: String
    ) = withContext(Dispatchers.IO) {
        return@withContext mainApiService.getWeatherHourlyRequest(lat, lon, lang)
    }

    override suspend fun getHourlyWeatherFromRoom(): List<Hourly> = withContext(Dispatchers.IO) {
        return@withContext weatherDao.getHourlyWeather()
    }


    override suspend fun getCurrentWeatherByNameRequestFromRemote(
        cityName: String,
        lang: String
    ) = withContext(Dispatchers.IO) {
        return@withContext mainApiService.getCurrentWeatherByNameRequest(cityName, lang)
    }


    //Room
    override suspend fun insertCurrentWeatherDataBase(currentModelResponse: CurrentModelResponse) =
        withContext(Dispatchers.IO) {
            weatherDao.insertCurrentWeather(currentModelResponse)
        }

    override suspend fun insertHourlyWeatherDataBase(hourlyList: List<Hourly>) =
        withContext(Dispatchers.IO) {
            weatherDao.insertHourlyWeather(hourlyList)
        }

    override suspend fun deleteHourlyWeatherDataBase() = withContext(Dispatchers.IO) {
        weatherDao.deleteAllHourlyWeather()
    }


}