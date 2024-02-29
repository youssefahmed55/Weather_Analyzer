package com.weatheraanalyzerrrr.domain.entity.hourlymodelresponse

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.weatheraanalyzerrrr.domain.entity.currentmodelresponse.Weather
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.Locale

@Entity(tableName = "HourlyTable")
data class Hourly(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val clouds: Int? = null,
    val dew_point: Double? = null,
    val dt: Long? = null,
    val feels_like: Double? = null,
    val humidity: Int? = null,
    val pop: Double? = null,
    val pressure: Int? = null,
    val rain: Rain? = null,
    val temp: Double? = null,
    val uvi: Double? = null,
    val visibility: Int? = null,
    val weather: List<Weather>? = null,
    val wind_deg: Int? = null,
    val wind_gust: Double? = null,
    val wind_speed: Double? = null
) {

    fun getTimeFromTimeStamp(): String {
        if (dt == null)
            return ""

        val date = Date(dt * 1000)
        val sfd = SimpleDateFormat("HH:mm aa", Locale.getDefault())
        return sfd.format(date)
    }
}