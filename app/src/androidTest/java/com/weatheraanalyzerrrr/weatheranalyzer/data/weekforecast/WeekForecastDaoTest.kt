package com.weatheraanalyzerrrr.weatheranalyzer.data.weekforecast

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.weatheraanalyzerrrr.data.local.AppDatabase
import com.weatheraanalyzerrrr.data.local.main.WeatherDao
import com.weatheraanalyzerrrr.data.local.weekforecast.WeekForecastDao
import com.weatheraanalyzerrrr.domain.entity.currentmodelresponse.CurrentModelResponse
import com.weatheraanalyzerrrr.domain.entity.dailymodelresponse.DailyModelResponse
import com.weatheraanalyzerrrr.domain.entity.hourlymodelresponse.Daily
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WeekForecastDaoTest {
    @JvmField
    @Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase
    private lateinit var dao: WeekForecastDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.weekForecastDao()
    }

    @After
    fun teardown(){
        database.close()
    }


    @Test
    fun insertDailyWeather() = runBlocking {
        val dailyModel = DailyModelResponse(daily = listOf(Daily(clouds = 20),Daily(clouds = 10),Daily(clouds = 16)))
        dao.insertDailyWeather(dailyModel)
        val dailyModelFromDataBase = dao.getDailyWeather()

        assert(dailyModelFromDataBase.daily?.size ==  dailyModel.daily?.size)
        assert(dailyModelFromDataBase.daily?.get(0)?.clouds ==  dailyModel.daily?.get(0)?.clouds)
        assert(dailyModelFromDataBase.daily?.get(1)?.clouds ==  dailyModel.daily?.get(1)?.clouds)
        assert(dailyModelFromDataBase.daily?.get(2)?.clouds ==  dailyModel.daily?.get(2)?.clouds)
    }

}