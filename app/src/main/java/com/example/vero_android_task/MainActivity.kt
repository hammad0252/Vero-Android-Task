package com.example.vero_android_task

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModel
import com.example.vero_android_task.ui.theme.VeroAndroidTaskTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            VeroAndroidTaskTheme {
                MainScreen()
            }
        }
    }


}
