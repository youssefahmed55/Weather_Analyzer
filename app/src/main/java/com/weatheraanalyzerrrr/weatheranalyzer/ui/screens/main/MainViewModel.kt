package com.weatheraanalyzerrrr.weatheranalyzer.ui.screens.main


import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.weatheraanalyzerrrr.domain.entity.ErrorResponse
import com.weatheraanalyzerrrr.domain.entity.currentmodelresponse.CurrentModelResponse
import com.weatheraanalyzerrrr.domain.entity.hourlymodelresponse.Hourly
import com.weatheraanalyzerrrr.domain.usecase.main.DeleteHourlyWeatherDataBaseR
import com.weatheraanalyzerrrr.domain.usecase.main.GetCurrentWeatherByName
import com.weatheraanalyzerrrr.domain.usecase.main.GetCurrentCityNameAndWeather
import com.weatheraanalyzerrrr.domain.usecase.main.GetCurrentCityNameAndWeatherR
import com.weatheraanalyzerrrr.domain.usecase.main.GetHourlyWeather
import com.weatheraanalyzerrrr.domain.usecase.main.GetHourlyWeatherR
import com.weatheraanalyzerrrr.domain.usecase.main.GetTheCurrentLocation
import com.weatheraanalyzerrrr.domain.usecase.main.InsertCurrentWeatherR
import com.weatheraanalyzerrrr.domain.usecase.main.InsertHourlyWeatherR
import com.weatheraanalyzerrrr.weatheranalyzer.ui.screens.ViewModelStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.util.Locale
import javax.inject.Inject

private const val TAG = "MainViewModel"

@OptIn(FlowPreview::class)
@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCurrentCityNameAndWeather: GetCurrentCityNameAndWeather,
    private val getHourlyWeather: GetHourlyWeather,
    private val getCurrentWeatherByName: GetCurrentWeatherByName,
    private val getCurrentCityNameAndWeatherR: GetCurrentCityNameAndWeatherR,
    private val getHourlyWeatherR: GetHourlyWeatherR,
    private val insertCurrentWeatherR: InsertCurrentWeatherR,
    private val insertHourlyWeatherR: InsertHourlyWeatherR,
    private val deleteHourlyWeatherDataBaseR: DeleteHourlyWeatherDataBaseR,
    private val getTheCurrentLocation: GetTheCurrentLocation
) : ViewModel() {

    //Initialize _currentWeatherModel
    private val _currentWeatherModel =
        MutableStateFlow<ViewModelStates<CurrentModelResponse>>(ViewModelStates.Loading)
    val currentWeatherModel = _currentWeatherModel.asStateFlow()

    //Initialize _hourlyModel
    private val _hourlyModel =
        MutableStateFlow<ViewModelStates<List<Hourly>>>(ViewModelStates.Loading)
    val hourlyModel = _hourlyModel.asStateFlow()

    //Initialize _textSearch
    private val _textSearch = MutableStateFlow("")
    val textSearch = _textSearch.asStateFlow()

    //Initialize _errorMessage
    private val _errorMessage = MutableStateFlow("")
    val errorMessage = _errorMessage.asStateFlow()

    fun getCurrentLocation() {
        viewModelScope.launch {
            val location = getTheCurrentLocation()
            if (location?.latitude != null) {
                getCurrentWeather(location.latitude, location.longitude) //Get Current Weather
                getHourly(location.latitude, location.longitude)         //Get Hourly Weather
            } else {
                getDefaultLocation()     //Get Default Location
            }
        }
    }

    // This function will make the textSearch value changes
    fun setSearchText(it: String) {
        _textSearch.value = it
        _errorMessage.value = ""
    }

    fun getDefaultLocation() {
        getCurrentWeather(30.0626, 31.2497)
        getHourly(30.0626, 31.2497)
    }

    init {
        observeSearchText()
    }

    private fun observeSearchText() {
        viewModelScope.launch {
            // As soon the textSearch flow changes,
            // if the user stops typing for 2000ms, the item will be emitted
            textSearch.debounce(2000).distinctUntilChanged().collect { query ->
                if (query.trim().isNotEmpty())
                    getCurrentWeatherByN(query) // Get Current Weather By City Name
                else
                    _errorMessage.value = ""
            }
        }
    }

    @VisibleForTesting
    internal fun getCurrentWeather(
        lat: Double,
        lon: Double,
        lang: String = Locale.getDefault().language
    ) {
        viewModelScope.launch {
            try {
                val data = getCurrentCityNameAndWeather(
                    lat,
                    lon,
                    lang
                )                                  // Get Current Weather
                insertCurrentWeatherR(data)                                                              // Insert Current Weather in Room
                _currentWeatherModel.update { ViewModelStates.Success(getCurrentCityNameAndWeatherR()) } // Get Current Weather From Room
            } catch (ex: Exception) {
                if (ex is HttpException) {
                    val error: ErrorResponse? = Gson().fromJson(
                        ex.response()?.errorBody()?.string(),
                        ErrorResponse::class.java
                    )
                    Log.e(TAG, "${error?.message}  //${error?.cod} ")
                    _currentWeatherModel.update {
                        ViewModelStates.Error(
                            error?.message ?: "",
                            getCurrentCityNameAndWeatherR()    // Get Current Weather From Room
                        )
                    }
                } else {
                    Log.e(TAG, "${ex.message}")
                    _currentWeatherModel.update {
                        ViewModelStates.Error(
                            "Please Check Internet Connection (Problem Get Data From Host)",
                            getCurrentCityNameAndWeatherR()    // Get Current Weather From Room
                        )
                    }
                }
            }

        }
    }

    @VisibleForTesting
    internal fun getHourly(lat: Double, lon: Double, lang: String = Locale.getDefault().language) {
        viewModelScope.launch {
            try {
                val data =
                    getHourlyWeather(lat, lon, lang)                           // Get Hourly Weather
                deleteHourlyWeatherDataBaseR()                                        // Delete Hourly Weather List From Room
                data.hourly?.let { insertHourlyWeatherR(it) }                         // Insert Hourly Weather List In Room
                _hourlyModel.update { ViewModelStates.Success(getHourlyWeatherR()) }  // Get Hourly Weather List From Room
            } catch (ex: Exception) {
                if (ex is HttpException) {
                    val error: ErrorResponse? = Gson().fromJson(
                        ex.response()?.errorBody()?.string(),
                        ErrorResponse::class.java
                    )
                    Log.e(TAG, "${error?.message}  //${error?.cod} ")
                    _hourlyModel.update {
                        ViewModelStates.Error(
                            error?.message ?: "",
                            getHourlyWeatherR()   // Get Hourly Weather List From Room
                        )
                    }
                } else {
                    Log.e(TAG, "${ex.message}")
                    _hourlyModel.update {
                        ViewModelStates.Error(
                            "Please Check Internet Connection (Problem Get Data From Host)",
                            getHourlyWeatherR()   // Get Hourly Weather List From Room
                        )
                    }
                }
            }

        }
    }

    @VisibleForTesting
    internal fun getCurrentWeatherByN(name: String, lang: String = Locale.getDefault().language) {
        viewModelScope.launch {
            try {
                val data =
                    getCurrentWeatherByName(name, lang)                  // Get Current Weather
                _currentWeatherModel.update { ViewModelStates.Success(data) }
                _hourlyModel.update {
                    ViewModelStates.Success(
                        data.coord?.lat?.let { it1 ->
                            data.coord?.lon?.let { it2 ->
                                getHourlyWeather(
                                    it1,
                                    it2,
                                    lang
                                ).hourly
                            }
                        }!!        // Get Hourly Weather
                    )
                }
            } catch (ex: Exception) {
                if (ex is HttpException) {
                    val error: ErrorResponse? = Gson().fromJson(
                        ex.response()?.errorBody()?.string(),
                        ErrorResponse::class.java
                    )
                    Log.e(TAG, "${error?.message}  //${error?.cod} ")
                    _errorMessage.value = error?.message ?: ""              //Set Error Value
                } else {
                    Log.e(TAG, "${ex.message}")
                    _errorMessage.value =
                        "Please Check Internet Connection (Problem Get Data From Host)"  //Set Error Value
                }
            }

        }

    }

}