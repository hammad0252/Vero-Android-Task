package com.example.vero_android_task

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    mainViewModel: AppViewModel = viewModel()
) {
    var text by remember {
        mutableStateOf(TextFieldValue(""))
    }

    val taskList = remember {
        mutableStateListOf<TaskClass>()
    }

    val authToken by remember {
        mutableStateOf(mainViewModel.authToken)
    }

    Surface(Modifier.fillMaxSize()) {
        Column(Modifier.padding(10.dp)) {
            Row(
                Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    label = { Text(text = "Type to search....") },
                    value = text,
                    onValueChange = {
                        text = it
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
                        //TODO
                    }) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_search_24),
                        contentDescription = "Search Button"
                    )
                }
            }
            LazyColumn() {
                items(taskList) { currentTask ->
                    var color = Color.White
                    if (currentTask.colorCode != "") {
                        color = Color(currentTask.colorCode.toInt())
                    }
                    TaskDisplay(currentTask, color)
                }
            }
            Text(text = authToken)
        }
    }
}

@Composable
fun TaskDisplay(currentTask: TaskClass, color: Color) {
    Surface(Modifier.padding(10.dp), color = color) {
        Text(text = currentTask.task)
        Text(text = currentTask.title)
        Text(text = currentTask.description)
        Text(text = currentTask.colorCode)
    }
}

/*@Preview
@Composable
fun SimpleComposablePreview() {
    MainScreen()
}*/
