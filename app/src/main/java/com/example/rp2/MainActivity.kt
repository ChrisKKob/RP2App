package com.example.rp2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import com.example.rp2.ui.theme.RP2Theme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.rp2.view.HomeScreen
import com.example.rp2.view.ImageCaptureScreen
import com.example.rp2.view.ResultScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RP2Theme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "Home" ){
                    composable(
                        route = "Home"
                    ){
                        HomeScreen(navController)
                    }

                    composable(
                        route = "ImageCaptureScreen"
                    ){
                        ImageCaptureScreen(navController)
                    }

                    composable(
                        route = "ResultScreen/{text}",
                                arguments = listOf(navArgument("result") { type = NavType.StringType })
                    ){backStackEntry ->
                        val text = backStackEntry.arguments?.getString("text")
                        ResultScreen(navController, text ?: "")
                    }

                }

            }
        }
    }
}

