package com.weatheraanalyzerrrr.weatheranalyzer.ui.theme.screens

sealed class ViewModelStates<out T> {

    data class Success<T>(val data: T) : ViewModelStates<T>()
    data class Error<T>(val message: String, val data: T?) : ViewModelStates<T>()
    object Loading : ViewModelStates<Nothing>()
}
