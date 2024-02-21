package com.weatheraanalyzerrrr.domain.usecase.main

import com.weatheraanalyzerrrr.domain.repo.main.WeatherRepo

class GetCurrentCityNameAndWeatherR(private val weatherRepo: WeatherRepo) {
    suspend operator fun invoke() = weatherRepo.getCurrentCityNameAndWeatherFromRoom()
}