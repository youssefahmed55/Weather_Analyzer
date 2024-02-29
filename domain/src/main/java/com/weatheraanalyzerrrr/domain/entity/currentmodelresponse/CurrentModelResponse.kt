package com.weatheraanalyzerrrr.domain.entity.currentmodelresponse

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.Locale

@Entity(tableName = "CurrentWeatherTable")
data class CurrentModelResponse(
    val base: String? = null,
    val clouds: Clouds? = null,
    val cod: Int? = null,
    val coord: Coord? = null,
    val dt: Long? = null,
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,
    val main: Main? = null,
    val name: String? = null,
    val sys: Sys? = null,
    val timezone: Int? = null,
    val visibility: Int? = null,
    val weather: List<Weather>? = null,
    val wind: Wind? = null
) {
    fun getDayFromTimeStamp(): String {
        if (dt == null)
            return ""

        val date = Date(dt * 1000)
        val sfd = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        return sfd.format(date)
    }
}