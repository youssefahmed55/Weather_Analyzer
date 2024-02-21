package com.weatheraanalyzerrrr.domain.usecase.main

import com.weatheraanalyzerrrr.domain.entity.currentmodelresponse.CurrentModelResponse
import com.weatheraanalyzerrrr.domain.repo.main.WeatherRepo

class InsertCurrentWeatherR(private val weatherRepo: WeatherRepo) {
    suspend operator fun invoke(currentModelResponse: CurrentModelResponse) =
        weatherRepo.insertCurrentWeatherDataBase(currentModelResponse)
}