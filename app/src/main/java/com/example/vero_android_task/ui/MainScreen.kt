package com.example.vero_android_task

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.example.vero_android_task.db.TaskClass
import com.example.vero_android_task.vm.AppViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalMaterialApi
@Composable
fun MainScreen(
    mainViewModel: AppViewModel,
    onNavigateToQR: () -> Unit
) {

    Log.d("Retrofit", "IN MAIN SCREEN")

    val searchText by mainViewModel.searchText.collectAsState()
    val taskList by mainViewModel.taskList.collectAsState()
    val refreshing by mainViewModel.refreshing.collectAsState()
    val refreshScope = rememberCoroutineScope()

    fun refresh() = refreshScope.launch {
        mainViewModel.setRefreshing(true)
        mainViewModel.setSearch("")
        mainViewModel.apiCall()
    }

    val state = rememberPullRefreshState(refreshing, ::refresh)

    Surface(Modifier.fillMaxSize()) {
        Column(Modifier.padding(10.dp)) {
            Row(
                Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    label = { Text(text = "Type to Search....") },
                    value = searchText,
                    singleLine = true,
                    onValueChange = {
                        mainViewModel.setSearch(it)
                    }
                )
                Image(
                    painter = painterResource(id = R.drawable.baseline_qr_code_scanner_24),
                    contentDescription = "QR Code",
                    modifier = Modifier
                        .height(40.dp)
                        .width(40.dp)
                        .clickable(
                            onClick = onNavigateToQR
                        )
                )
                Button(modifier = Modifier.weight(1f),
                    onClick = {
                        mainViewModel.search(searchText)
                    }) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_search_24),
                        contentDescription = "Search Button"
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Box(Modifier.pullRefresh(state)) {
                LazyColumn() {
                    mainViewModel.setRefreshing(false)
                    items(taskList) { currentTask ->
                        var color = Color.White
                        if (currentTask.colorCode != "") {
                            color = Color(currentTask.colorCode.toColorInt())
                        }
                        TaskDisplay(currentTask, color)
                    }
                }
                PullRefreshIndicator(refreshing, state, Modifier.align(Alignment.TopCenter))
            }
        }
    }
}

@Composable
fun TaskDisplay(currentTask: TaskClass, color: Color) {
    Surface(
        Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(10))
            .border(2.dp, Color.Black, RoundedCornerShape(10)),
        color = color
    ) {
        Column(Modifier.padding(10.dp)) {
            Text(text = "Task : ${currentTask.task}")
            Text(text = "Title : ${currentTask.title}")
            Text(text = "Description : ${currentTask.description}")
            Text(text = "Color Code : ${currentTask.colorCode}")
        }
    }
}
