package com.weatheraanalyzerrrr.weatheranalyzer.ui.theme.screens.weekforecast


import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.weatheraanalyzerrrr.domain.entity.ErrorResponse
import com.weatheraanalyzerrrr.domain.entity.currentmodelresponse.CurrentModelResponse
import com.weatheraanalyzerrrr.domain.entity.dailymodelresponse.DailyModelResponse
import com.weatheraanalyzerrrr.domain.usecase.weekforecast.GetDailyWeather
import com.weatheraanalyzerrrr.domain.usecase.weekforecast.GetDailyWeatherR
import com.weatheraanalyzerrrr.domain.usecase.weekforecast.InsertDailyWeatherR
import com.weatheraanalyzerrrr.weatheranalyzer.ui.theme.screens.ViewModelStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.util.Locale
import javax.inject.Inject

private const val TAG = "WeekForecastViewModel"

@HiltViewModel
class WeekForecastViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getDailyResponseWeather: GetDailyWeather,
    private val getDailyResponseWeatherR: GetDailyWeatherR,
    private val insertDailyResponseWeatherR: InsertDailyWeatherR
) : ViewModel() {

    private val _cityNameState = MutableStateFlow("")
    val cityNameState = _cityNameState.asStateFlow()

    private val _dailyModel =
        MutableStateFlow<ViewModelStates<DailyModelResponse>>(ViewModelStates.Loading)
    val dailyModel = _dailyModel.asStateFlow()

    private val argsCurrent: String? = savedStateHandle["currentWeather"]


    init {
        Log.d(TAG, ": $argsCurrent ")
        argsCurrent?.let {

            val currentWeatherTypeData: CurrentModelResponse =
                Gson().fromJson(it, CurrentModelResponse::class.java)
            Log.d(TAG, ": $currentWeatherTypeData ")
            _cityNameState.update { currentWeatherTypeData.name ?: "" }
            val lat = currentWeatherTypeData.coord?.lat
            val long = currentWeatherTypeData.coord?.lon

            if (lat != null && long != null) {
                Log.d(TAG, "$lat $long")
                getDailyWeather(lat, long)
            }
        }

    }

    private fun getDailyWeather(
        lat: Double,
        lon: Double,
        lang: String = Locale.getDefault().language
    ) {
        viewModelScope.launch {
            try {
                val data = getDailyResponseWeather(lat, lon, lang)
                insertDailyResponseWeatherR(data)
                _dailyModel.update { ViewModelStates.Success(getDailyResponseWeatherR()!!) }
            } catch (ex: Exception) {
                if (ex is HttpException) {
                    val error: ErrorResponse? = Gson().fromJson(
                        ex.response()?.errorBody()?.string(),
                        ErrorResponse::class.java
                    )
                    Log.e(TAG, "${error?.message}  //${error?.cod} ")
                    _dailyModel.update {
                        ViewModelStates.Error(
                            error?.message ?: "",
                            getDailyResponseWeatherR()
                        )
                    }
                } else {
                    Log.e(TAG, "${ex.message}")
                    val data = getDailyResponseWeatherR()
                    _dailyModel.update {

                        if (data?.lat == lat && data.lon == lon) {
                            ViewModelStates.Error(
                                "Please Check Internet Connection (Problem Get Data From Host)",
                                data
                            )

                        } else {
                            ViewModelStates.Error(
                                "Please Check Internet Connection (Problem Get Data From Host)",
                                null
                            )
                        }
                    }


                }


            }
        }

    }
}


