package com.weatheraanalyzerrrr.domain.entity.hourlymodelresponse


import com.weatheraanalyzerrrr.domain.entity.currentmodelresponse.Weather
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.Locale


data class Daily(
    val clouds: Int? = null,
    val dew_point: Double? = null,
    val dt: Long? = null,
    val feels_like: FeelsLike? = null,
    val humidity: Int? = null,
    val moon_phase: Double? = null,
    val moonrise: Int? = null,
    val moonset: Int? = null,
    val pop: Double? = null,
    val pressure: Int? = null,
    val rain: Double? = null,
    val sunrise: Int? = null,
    val sunset: Int? = null,
    val temp: Temp? = null,
    val uvi: Double? = null,
    val weather: List<Weather>? = null,
    val wind_deg: Int? = null,
    val wind_gust: Double? = null,
    val wind_speed: Double? = null
) {

    fun getDayFromTimeStamp(): String {
        if (dt == null)
            return ""

        val date = Date(dt * 1000)
        val sfd = SimpleDateFormat("EEEE", Locale.getDefault())
        return sfd.format(date)
    }
}