package com.weatheraanalyzerrrr.domain.usecase.main

import com.weatheraanalyzerrrr.domain.repo.main.WeatherRepo

class GetCurrentCityNameAndWeather(private val weatherRepo: WeatherRepo) {
    suspend operator fun invoke(
        lat: Double,
        lon: Double,
        lang: String
    ) = weatherRepo.getCurrentCityNameAndWeatherFromRemote(lat, lon, lang)
}