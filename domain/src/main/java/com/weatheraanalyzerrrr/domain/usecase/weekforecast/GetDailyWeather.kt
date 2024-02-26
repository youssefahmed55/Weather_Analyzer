package com.weatheraanalyzerrrr.domain.usecase.weekforecast


import com.weatheraanalyzerrrr.domain.repo.weekforecast.WeekForecastRepo

class GetDailyWeather  (private val weekForecastRepo: WeekForecastRepo) {
    suspend operator fun invoke(
        lat: Double,
        lon: Double,
        lang: String
    ) =
        weekForecastRepo.getDailyWeatherFromRemote(lat,lon,lang)
}