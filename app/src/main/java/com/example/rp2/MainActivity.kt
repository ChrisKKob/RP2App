package com.example.rp2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rp2.ui.theme.RP2Theme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rp2.view.HomeScreen
import com.example.rp2.view.ImageCaptureScreen
import com.example.rp2.view.ResultScreen
import com.example.rp2.viewModel.AppViewModel


class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<AppViewModel> (
        factoryProducer = {
            object : ViewModelProvider.Factory{
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return AppViewModel() as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RP2Theme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "HomeScreen" ){
                    composable(
                        route = "HomeScreen"
                    ){
                        HomeScreen(navController, viewModel)
                    }

                    composable(
                        route = "ImageCaptureScreen"
                    ){
                        ImageCaptureScreen(navController, viewModel)
                    }

                    composable(
                        route = "ResultScreen"
                    ){
                        ResultScreen(navController, viewModel)
                    }

                }

            }
        }
    }
}

