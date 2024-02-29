package com.weatheraanalyzerrrr.weatheranalyzer.ui.screens


sealed class Screen(val route: String) {
    object SplashScreen : Screen("splashScreen")
    object MainScreen : Screen("mainScreen")
    object WeekForecastScreen : Screen("weekForecastScreen")

}