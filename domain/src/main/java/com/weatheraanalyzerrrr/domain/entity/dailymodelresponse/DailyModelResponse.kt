package com.weatheraanalyzerrrr.domain.entity.dailymodelresponse

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.weatheraanalyzerrrr.domain.entity.hourlymodelresponse.Daily

@Entity(tableName = "DailyTable")
data class DailyModelResponse(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,
    val daily: List<Daily>? = null,
    val lat: Double? = null,
    val lon: Double? = null,
    val timezone: String? = null,
    val timezone_offset: Int? = null
)