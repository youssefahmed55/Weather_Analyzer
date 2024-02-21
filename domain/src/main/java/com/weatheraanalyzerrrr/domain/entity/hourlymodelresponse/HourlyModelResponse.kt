package com.weatheraanalyzerrrr.domain.entity.hourlymodelresponse

data class HourlyModelResponse(
    val current: Current ?= null,
    val daily: List<Daily> ?= null,
    val hourly: List<Hourly> ?= null,
    val lat: Double ?= null,
    val lon: Double ?= null,
    val timezone: String ?= null,
    val timezone_offset: Int ?= null
)