package com.weatheraanalyzerrrr.domain.usecase.weekforecast


import com.weatheraanalyzerrrr.domain.entity.dailymodelresponse.DailyModelResponse
import com.weatheraanalyzerrrr.domain.repo.weekforecast.WeekForecastRepo

class InsertDailyWeatherR(private val weekForecastRepo: WeekForecastRepo) {
    suspend operator fun invoke(dailyModelResponse: DailyModelResponse) =
        weekForecastRepo.insertDailyWeatherDataBase(dailyModelResponse)
}