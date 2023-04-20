package com.example.vero_android_task

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import java.io.IOException

class AppViewModel (): ViewModel(){
    var authToken by mutableStateOf("")

    init {
        viewModelScope.launch {
            apiCall()
        }
    }

    private fun apiCall(){
        var apiResponse = ""
        val client = OkHttpClient()
        val mediaType = MediaType.parse("application/json")
        val body = RequestBody.create(
            mediaType,
            "{\n        \"username\":\"365\",\n        \"password\":\"1\"\n}"
        )
        Log.d("Retrofit", "About to send a request")
        val request = Request.Builder()
            .url("https://api.baubuddy.de/index.php/login")
            .post(body)
            .addHeader("Authorization", "Basic QVBJX0V4cGxvcmVyOjEyMzQ1NmlzQUxhbWVQYXNz")
            .addHeader("Content-Type", "application/json")
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                Log.d("Retrofit", "$e")
            }

            override fun onResponse(call: Call?, response: Response?) {
                apiResponse = response?.body()?.string().toString()
                Log.d("Retrofit", "Response Body is $apiResponse")
                val gson = Gson()
                val tutorialMap: Map<String, Any> = gson.fromJson(apiResponse, object : TypeToken<Map<String, Any>>() {}.type)
                val oauth : Map<String, Any> = gson.fromJson(tutorialMap["oauth"].toString(), object : TypeToken<Map<String, Any>>() {}.type)
                Log.d("Retrofit", "Response Body is ${oauth["access_token"]}")
                authToken = oauth["access_token"].toString()
                Log.d("Retrofit", "Auth Token is $authToken")
            }
        })
    }
}