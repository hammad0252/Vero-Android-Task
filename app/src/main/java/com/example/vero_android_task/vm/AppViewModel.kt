package com.example.vero_android_task.vm

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.vero_android_task.db.TaskClass
import com.example.vero_android_task.db.TaskDatabase
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

class AppViewModel(application: Application) : AndroidViewModel(application) {

    private var accessToken = ""
    private var allTasksLists = emptyList<TaskClass>()
    private var searchTasks = mutableListOf<TaskClass>()
    private val _taskList = MutableStateFlow(emptyList<TaskClass>())
    val taskList = _taskList.asStateFlow()
    private val _searchText = MutableStateFlow("")
    var searchText = _searchText.asStateFlow()
    private val _refreshing = MutableStateFlow(false)
    var refreshing = _refreshing.asStateFlow()
    private val context = getApplication<Application>().applicationContext
    private lateinit var db : TaskDatabase

    init {
        viewModelScope.launch {
            getData()
        }
    }

    private fun getData(){
        db = Room.databaseBuilder(
            context,
            TaskDatabase::class.java, "tasks"
        ).build()

        viewModelScope.launch {
            allTasksLists = db.taskDao().getAll()
            _taskList.value = allTasksLists
        }

        if (allTasksLists.isEmpty()){
            apiCall()
        }
    }

    fun setSearch(search : String){
        _searchText.value = search
    }

    fun setRefreshing (refreshing : Boolean){
        _refreshing.value = refreshing
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

    fun apiCall() {
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
                writeTasksToDB(allTasksLists)
                _taskList.value = allTasksLists
            }
        })
    }

    private fun writeTasksToDB(allTasksLists: List<TaskClass>) {
        db.clearAllTables()
        for (i in allTasksLists.indices){
            db.taskDao().insert(allTasksLists[i])
        }
    }
}