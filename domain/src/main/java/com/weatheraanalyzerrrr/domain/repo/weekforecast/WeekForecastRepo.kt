package com.weatheraanalyzerrrr.domain.repo.weekforecast


import com.weatheraanalyzerrrr.domain.entity.dailymodelresponse.DailyModelResponse


interface WeekForecastRepo {

    suspend fun getDailyWeatherFromRemote(
        lat: Double,
        lon: Double,
        lang: String
    ): DailyModelResponse

    suspend fun getDailyWeatherFromRoom(): DailyModelResponse?

    suspend fun insertDailyWeatherDataBase(dailyModelResponse: DailyModelResponse)


}