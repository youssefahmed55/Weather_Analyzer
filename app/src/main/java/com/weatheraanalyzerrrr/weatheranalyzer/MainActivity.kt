package com.weatheraanalyzerrrr.weatheranalyzer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.weatheraanalyzerrrr.weatheranalyzer.ui.theme.WeatherAnalyzerTheme
import com.weatheraanalyzerrrr.weatheranalyzer.ui.theme.screens.SplashScreenAnimate

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

    NavHost(navController = navHostController, startDestination = "splash") {
        composable("splash") { SplashScreenAnimate(navHostController) }

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WeatherAnalyzerTheme {
        Weather()
    }
}