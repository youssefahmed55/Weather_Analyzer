package com.weatheraanalyzerrrr.data.repo.weekforecast

import com.weatheraanalyzerrrr.data.remote.weekforecast.WeekForecastApiService
import com.weatheraanalyzerrrr.data.room.weekforecast.WeekForecastDao
import com.weatheraanalyzerrrr.domain.entity.dailymodelresponse.DailyModelResponse
import com.weatheraanalyzerrrr.domain.repo.weekforecast.WeekForecastRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeekForecastRepoImpl(
    private val weekForecastApiService: WeekForecastApiService,
    private val weekForecastDao: WeekForecastDao
) :
    WeekForecastRepo {
    //Remote
    override suspend fun getDailyWeatherFromRemote(
        lat: Double,
        lon: Double,
        lang: String
    ) = withContext(Dispatchers.IO) {
        return@withContext weekForecastApiService.getWeatherOfSevenDaysRequest(lat, lon, lang)
    }

    //Room
    override suspend fun getDailyWeatherFromRoom() = withContext(Dispatchers.IO) {
        return@withContext weekForecastDao.getDailyWeather()
    }

    override suspend fun insertDailyWeatherDataBase(dailyModelResponse: DailyModelResponse) =
        withContext(Dispatchers.IO) {
            weekForecastDao.insertDailyWeather(dailyModelResponse)
        }


}