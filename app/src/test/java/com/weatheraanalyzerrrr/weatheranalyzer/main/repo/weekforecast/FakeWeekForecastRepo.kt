package com.weatheraanalyzerrrr.weatheranalyzer.main.repo.weekforecast



import com.weatheraanalyzerrrr.domain.entity.dailymodelresponse.DailyModelResponse
import com.weatheraanalyzerrrr.domain.repo.weekforecast.WeekForecastRepo
import kotlinx.coroutines.flow.MutableStateFlow

class FakeWeekForecastRepo : WeekForecastRepo {

    val currentWeatherData = MutableStateFlow(DailyModelResponse())

    override suspend fun getDailyWeatherFromRemote(
        lat: Double,
        lon: Double,
        lang: String
    ): DailyModelResponse {
        return DailyModelResponse(lat = lat, lon = lon)
    }

    override suspend fun getDailyWeatherFromRoom(): DailyModelResponse? {
        return currentWeatherData.value
    }

    override suspend fun insertDailyWeatherDataBase(dailyModelResponse: DailyModelResponse) {
        currentWeatherData.value = dailyModelResponse
    }


}