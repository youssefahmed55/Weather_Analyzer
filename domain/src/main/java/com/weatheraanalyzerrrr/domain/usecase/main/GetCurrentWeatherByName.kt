package com.weatheraanalyzerrrr.domain.usecase.main

import com.weatheraanalyzerrrr.domain.repo.main.WeatherRepo

class GetCurrentWeatherByName(private val weatherRepo: WeatherRepo) {
    suspend operator fun invoke(
        cityName: String,
        lang: String
    ) = weatherRepo.getCurrentWeatherByNameRequestFromRemote(cityName, lang)
}