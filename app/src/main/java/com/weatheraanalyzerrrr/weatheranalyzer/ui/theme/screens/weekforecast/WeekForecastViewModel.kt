package com.weatheraanalyzerrrr.weatheranalyzer.ui.theme.screens.weekforecast

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

private const val TAG = "WeekForecastViewModel"

@HiltViewModel
class WeekForecastViewModel @Inject constructor(savedStateHandle: SavedStateHandle) : ViewModel() {

    private val _cityNameState = MutableStateFlow("")
    val cityNameState = _cityNameState.asStateFlow()

    private val argsName: String = savedStateHandle["cityName"] ?: ""
    private val argsLat: String? = savedStateHandle["lat"]
    private val argsLong: String? = savedStateHandle["long"]

    init {
        _cityNameState.update { argsName }

        if (argsLat != null && argsLong != null) {
            Log.d(TAG, "$argsLat $argsLong")
        }
    }


}