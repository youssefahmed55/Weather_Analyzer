package com.weatheraanalyzerrrr.domain.usecase.main

import com.weatheraanalyzerrrr.domain.repo.main.WeatherRepo

class GetHourlyWeather(private val weatherRepo: WeatherRepo) {
    suspend operator fun invoke(
        lat: Double,
        lon: Double,
        lang: String
    ) = weatherRepo.getHourlyWeatherFromRemote(lat, lon, lang)
}