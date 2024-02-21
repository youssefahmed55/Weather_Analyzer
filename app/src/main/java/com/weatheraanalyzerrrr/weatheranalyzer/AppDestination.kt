package com.weatheraanalyzerrrr.weatheranalyzer


sealed class Screen(val route: String) {
    object SplashScreen : Screen("splashScreen")
    object MainScreen : Screen("mainScreen")
    object WeekForecastScreen : Screen("weekForecastScreen")

}