package com.example.vero_android_task

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.vero_android_task.ui.theme.VeroAndroidTaskTheme
import com.example.vero_android_task.utils.Constants
import com.example.vero_android_task.vm.AppViewModel


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            VeroAndroidTaskTheme {
                val navController = rememberNavController()
                val viewModel: AppViewModel = viewModel()
                NavHost(
                    navController = navController,
                    startDestination = Constants.MAIN_SCREEN_ROUTE
                ) {
                    composable(route = Constants.MAIN_SCREEN_ROUTE) {
                        MainScreen(
                            viewModel,
                            onNavigateToQR = {
                            navController.navigate(Constants.QR_SCANNER_ROUTE)
                        })
                    }
                    composable(route = Constants.QR_SCANNER_ROUTE) {
                        QRScannerScreen(
                            viewModel,
                            onNavigateToMain = {
                            navController.navigate(Constants.MAIN_SCREEN_ROUTE) }
                        )
                    }
                }
            }
        }
    }
}
