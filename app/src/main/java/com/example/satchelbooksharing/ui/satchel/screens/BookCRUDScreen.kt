package com.example.satchelbooksharing.ui.satchel.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.satchelbooksharing.ui.satchel.sharedElements.Header
import com.example.satchelbooksharing.ui.satchel.sharedElements.SatchelBodyContainer

@Composable
fun BookCRUDScreen() {

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
        Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
            TextField(value = titleValue, onValueChange = {
                titleValue = it
            },
                label = { Text(text = "title")}
            )

            TextField(value = authorValue, onValueChange = {
                authorValue = it
            },
                label = { Text(text = "author")}
            )

            TextField(value = descriptionValue, onValueChange = {
                descriptionValue = it
            },
                label = { Text(text = "description")}
            )

        }
    }


}

@Preview(showBackground = true)
@Composable
fun BookCRUDPreview(){
    BookCRUDScreen()
}