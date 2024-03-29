package com.weatheraanalyzerrrr.weatheranalyzer.data.weekforecast

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.weatheraanalyzerrrr.data.local.AppDatabase
import com.weatheraanalyzerrrr.data.local.weekforecast.WeekForecastDao
import com.weatheraanalyzerrrr.domain.entity.dailymodelresponse.DailyModelResponse
import com.weatheraanalyzerrrr.domain.entity.hourlymodelresponse.Daily
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class WeekForecastDaoTest {

    //Initialize hiltRule
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    //Initialize instantTaskExecutorRule
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    //Initialize database
    @Inject
    @Named("test_db")
    lateinit var database: AppDatabase

    //Initialize weekForecastDao
    @Inject
    @Named("test_weekForecastDao")
    lateinit var dao: WeekForecastDao

    @Before
    fun setup() {
        hiltRule.inject() //Inject
    }

    @After
    fun teardown() {
        database.close() //Close DataBase
    }


    @Test
    fun insertDailyWeather() = runBlocking {
        //Given dailyModel
        val dailyModel = DailyModelResponse(
            daily = listOf(
                Daily(clouds = 20),
                Daily(clouds = 10),
                Daily(clouds = 16)
            )
        )

        //When Insert dailyModel to DataBase
        dao.insertDailyWeather(dailyModel)

        //Then Check if It's The Same Expected Model We Inserted Is True
        val dailyModelFromDataBase = dao.getDailyWeather()
        assert(dailyModelFromDataBase.daily?.size == dailyModel.daily?.size)
        assert(dailyModelFromDataBase.daily?.get(0)?.clouds == dailyModel.daily?.get(0)?.clouds)
        assert(dailyModelFromDataBase.daily?.get(1)?.clouds == dailyModel.daily?.get(1)?.clouds)
        assert(dailyModelFromDataBase.daily?.get(2)?.clouds == dailyModel.daily?.get(2)?.clouds)
    }

}