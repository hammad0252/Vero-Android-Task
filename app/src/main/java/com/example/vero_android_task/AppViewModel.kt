package com.example.vero_android_task

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import java.io.IOException

class AppViewModel() : ViewModel() {

    private var accessToken = ""
    private var allTasksLists = emptyList<TaskClass>()
    private var searchTasks = mutableListOf<TaskClass>()
    private val _taskList = MutableStateFlow(emptyList<TaskClass>())
    val taskList = _taskList.asStateFlow()
    private val _searchText = MutableStateFlow("")
    var searchText = _searchText.asStateFlow()

    init {
        viewModelScope.launch {
            apiCall()
        }
    }

    fun setSearch(search : String){
        _searchText.value = search
    }

    fun search(searchText: String) {
        searchTasks.clear()
        if (searchText != "") {
            for (item in allTasksLists) {
                for (prop in item.iterator()) {
                    val propertyText = prop.second.toString().split(" ")
                    for (word in propertyText) {
                        if (searchText.lowercase() == word.lowercase()) {
                            if (!searchTasks.contains(item)) {
                                searchTasks.add(item)
                            }
                        }
                    }
                }
            }
            _taskList.value = searchTasks.toList()
        } else {
            _taskList.value = allTasksLists
        }
    }

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
            override fun onFailure(call: Call?, e: IOException?) {
                Log.d("ErrorLog", "API call for auth token failed with exception $e")
            }

            override fun onResponse(call: Call?, response: Response?) {
                apiResponse = response?.body()?.string().toString()
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
            override fun onFailure(call: Call?, e: IOException?) {
                Log.d("ErrorLog", "API call for task list failed with exception $e")
            }

            override fun onResponse(call: Call?, response: Response?) {
                apiResponse = response?.body()?.string().toString()
                val gson = Gson()
                val taskListType = object : TypeToken<List<TaskClass>>() {}.type
                allTasksLists = gson.fromJson(apiResponse, taskListType)
                _taskList.value = allTasksLists
            }
        })
    }
}