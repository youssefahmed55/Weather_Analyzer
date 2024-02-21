package com.weatheraanalyzerrrr.domain.usecase.main

import com.weatheraanalyzerrrr.domain.entity.hourlymodelresponse.Hourly
import com.weatheraanalyzerrrr.domain.entity.hourlymodelresponse.HourlyModelResponse
import com.weatheraanalyzerrrr.domain.repo.main.WeatherRepo

class InsertHourlyWeatherR(private val weatherRepo: WeatherRepo) {
    suspend operator fun invoke(hourlyModelResponse: List<Hourly>) =
        weatherRepo.insertHourlyWeatherDataBase(hourlyModelResponse)
}