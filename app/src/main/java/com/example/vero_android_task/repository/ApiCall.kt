package com.example.vero_android_task.repository

import android.util.Log
import androidx.lifecycle.Observer
import com.example.vero_android_task.db.TaskClass
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import java.io.IOException

class ApiCall {

    private var accessToken = ""
    private var allTasksLists = emptyList<TaskClass>()

    private fun apiCall() {
        var apiResponse = ""
        val client = OkHttpClient()
        val mediaType = MediaType.parse("application/json")
        val body = RequestBody.create(
            mediaType,
            "{\n        \"username\":\"365\",\n        \"password\":\"1\"\n}"
        )
        val request = Request.Builder()
            .url("https://api.baubuddy.de/index.php/login")
            .post(body)
            .addHeader("Authorization", "Basic QVBJX0V4cGxvcmVyOjEyMzQ1NmlzQUxhbWVQYXNz")
            .addHeader("Content-Type", "application/json")
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("ErrorLog", "API call for auth token failed with exception $e")
            }

            override fun onResponse(call: Call, response: Response) {
                apiResponse = response.body()?.string().toString()
                val gson = Gson()
                val jsonMap: Map<String, Any> =
                    gson.fromJson(apiResponse, object : TypeToken<Map<String, Any>>() {}.type)
                val oauthMap: Map<String, Any> = gson.fromJson(
                    jsonMap["oauth"].toString(),
                    object : TypeToken<Map<String, Any>>() {}.type
                )
                accessToken = oauthMap["access_token"].toString()
                getTaskList(accessToken)
            }
        })
    }

    private fun getTaskList(accessToken: String) {
        var apiResponse = ""
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://api.baubuddy.de/dev/index.php/v1/tasks/select")
            .get()
            .addHeader("Authorization", "Bearer $accessToken")
            .addHeader("Content-Type", "application/json")
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("ErrorLog", "API call for task list failed with exception $e")
            }

            override fun onResponse(call: Call, response: Response) {
                apiResponse = response.body()?.string().toString()
                val gson = Gson()
                val taskListType = object : TypeToken<List<TaskClass>>() {}.type
                allTasksLists = gson.fromJson(apiResponse, taskListType)
            }
        })
    }

    fun getData() : List<TaskClass>{
        return allTasksLists
    }

}