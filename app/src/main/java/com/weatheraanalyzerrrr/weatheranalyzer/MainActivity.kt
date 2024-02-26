package com.weatheraanalyzerrrr.weatheranalyzer


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.weatheraanalyzerrrr.weatheranalyzer.ui.theme.WeatherAnalyzerTheme
import com.weatheraanalyzerrrr.weatheranalyzer.ui.theme.screens.SplashScreenAnimate
import com.weatheraanalyzerrrr.weatheranalyzer.ui.theme.screens.main.MainScreen
import com.weatheraanalyzerrrr.weatheranalyzer.ui.theme.screens.weekforecast.WeekForecast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAnalyzerTheme {
                Weather()
            }
        }
    }
}


@Composable
fun Weather() {
    val navHostController = rememberNavController()

    NavHost(navController = navHostController, startDestination = Screen.SplashScreen.route) {
        composable(Screen.SplashScreen.route) { SplashScreenAnimate(navHostController) }
        composable(Screen.MainScreen.route) { MainScreen(navHostController) }
        composable(
            "${Screen.WeekForecastScreen.route}/{currentWeather}",
            arguments = listOf(navArgument("currentWeather") { NavType.StringType }
            )
        ) { WeekForecast(navHostController) }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WeatherAnalyzerTheme {
        Weather()
    }
}

