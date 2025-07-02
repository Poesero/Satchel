package com.example.satchelbooksharing.ui.satchel.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.satchelbooksharing.model.satchel.Genre
import com.example.satchelbooksharing.ui.satchel.sharedElements.SatchelBodyContainer
import com.example.satchelbooksharing.viewModel.satchel.LibraryViewModel
import com.example.satchelbooksharing.viewModel.satchel.LibraryViewModelFactory

@Composable
fun NewBookScreen(
    onBookAdded: () -> Unit = {},
    libraryViewModel: LibraryViewModel,
    navController: NavController,
) {
    val isFormValid = libraryViewModel.isFormValid
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(top = 80.dp, bottom = 150.dp),
        contentAlignment = Alignment.Center
    ) {
        SatchelBodyContainer {
            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(13.dp)
                    .padding(2.dp, vertical = 10.dp)
            ) {
                TextField(
                    value = libraryViewModel.title,
                    onValueChange = { libraryViewModel.onTitleChanged(it) },
                    label = { Text(text = "title") },
                    singleLine = true
                )
                TextField(
                    value = libraryViewModel.author,
                    onValueChange = { libraryViewModel.onAuthorChanged(it) },
                    label = { Text(text = "author") },
                    singleLine = true
                )
                TextField(
                    value = libraryViewModel.description,
                    onValueChange = { libraryViewModel.onDescriptionChanged(it) },
                    label = { Text(text = "description") },
                    maxLines = 4,
                    modifier = Modifier.height(100.dp)
                )
                GenreDropdownMenu(
                    genreValue = libraryViewModel.genre,
                    onGenreSelected = { libraryViewModel.onGenreChanged(it) }
                )
                Row(modifier = Modifier
                    .fillMaxWidth(0.75f),
                    horizontalArrangement = Arrangement.SpaceBetween){
                    val context = LocalContext.current
                    Button(onClick = {
                        libraryViewModel.saveBook {
                            Toast.makeText(context, "New book added", Toast.LENGTH_SHORT).show()
                            navController.popBackStack()
                        }
                    },
                        enabled = isFormValid
                    ) {
                        Text(text = "Finish")
                    }
                    Button(onClick = { navController.popBackStack() }) {
                        Text(text = "Cancel")
                    }
                }
            }
        }
    }

}


@Composable
fun GenreDropdownMenu(
    genreValue: Genre,
    onGenreSelected: (Genre) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Box(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = genreValue.name,
            onValueChange = {},
            readOnly = true,
            label = { Text("Genre") },
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            Genre.entries.forEach { genre ->
                DropdownMenuItem(
                    text = { Text(genre.name) },
                    onClick = {
                        onGenreSelected(genre)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun previewDDM(){
    var genreValue by remember { mutableStateOf(Genre.OTHER) }

    GenreDropdownMenu(
        genreValue = genreValue,
        onGenreSelected = { genreValue = it }
    )
}