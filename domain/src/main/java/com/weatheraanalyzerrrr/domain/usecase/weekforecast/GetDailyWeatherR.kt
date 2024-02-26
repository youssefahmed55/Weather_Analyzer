package com.weatheraanalyzerrrr.domain.usecase.weekforecast


import com.weatheraanalyzerrrr.domain.repo.weekforecast.WeekForecastRepo

class GetDailyWeatherR (private val weekForecastRepo: WeekForecastRepo) {
    suspend operator fun invoke() =
        weekForecastRepo.getDailyWeatherFromRoom()
}