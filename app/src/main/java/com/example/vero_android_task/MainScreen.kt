package com.example.vero_android_task

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    mainViewModel: AppViewModel = viewModel()
) {
    val searchText by mainViewModel.searchText.collectAsState()

    val taskList by mainViewModel.taskList.collectAsState()

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
                        .clickable {
                            //TODO
                        }
                )
                Button(modifier = Modifier.weight(1f),
                    onClick = {
                        mainViewModel.search(searchText.toString())
                    }) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_search_24),
                        contentDescription = "Search Button"
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn() {
                items(taskList) { currentTask ->
                    var color = Color.White
                    if (currentTask.colorCode != "") {
                        color = Color(currentTask.colorCode.toColorInt())
                    }
                    TaskDisplay(currentTask, color)
                }
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

/*@Preview
@Composable
fun SimpleComposablePreview() {
    MainScreen()
}*/
