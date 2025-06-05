package com.example.satchelbooksharing.ui.satchel.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.satchelbooksharing.model.satchel.Book
import com.example.satchelbooksharing.viewModel.satchel.LibraryViewModel

@Composable
fun BookCRUDScreen(
    onBookAdded: () -> Unit,
    libraryViewModel: LibraryViewModel = viewModel()
) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        var titleValue by remember{
            mutableStateOf("")
        }
        var authorValue by remember{
            mutableStateOf("")
        }
        var descriptionValue by remember{
            mutableStateOf("")
        }
        Column(verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
                    .padding(13.dp)
                    //.background(color = Color.Green)
                    .padding(2.dp, vertical = 10.dp)) {

            TextField(value = titleValue, onValueChange = {
                titleValue = it
            },
                label = { Text(text = "title")},
                singleLine = true
            )

            TextField(value = authorValue, onValueChange = {
                authorValue = it
            },
                label = { Text(text = "author")},
                singleLine = true
            )

            TextField(value = descriptionValue, onValueChange = {
                descriptionValue = it
            },
                label = { Text(text = "description")},
                maxLines = 4,
                modifier = Modifier.height(100.dp)

            )

        }
    }


}

@Preview(showBackground = true)
@Composable
fun BookCRUDPreview(){
    //BookCRUDScreen()
}