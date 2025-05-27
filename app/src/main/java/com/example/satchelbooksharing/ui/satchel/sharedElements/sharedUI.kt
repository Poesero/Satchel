package com.example.satchelbooksharing.ui.satchel.sharedElements

import com.example.satchelbooksharing.model.satchel.Book
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import com.example.satchelbooksharing.R
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.testing.TestNavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.satchelbooksharing.ui.satchel.ui.theme.SatchelTheme
/*
@Composable
fun TestUi(){
    Column {
        Header("Header")
        SatchelBodyContainer()
        Footer()
    }
}
 */

@Composable
fun Header (title: String) {
    Surface(
        tonalElevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HeaderPreview() {
    Header(title="String")
}

@Composable
fun Footer (navController: NavHostController) {
    Surface(
        tonalElevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment  = Alignment.CenterVertically
        ) {
            AppButton(onClick = {navController.navigate("library") }) {
                Icon(Icons.AutoMirrored.Filled.List, contentDescription = null)
                Text("Biblioteca")
            }
            AppButton(onClick = {navController.navigate("home")}) {
                Icon(Icons.Default.Home, contentDescription = null)
                Text("Inicio")
            }
            AppButton(onClick = {navController.navigate("search")}) {
                Icon(Icons.Default.Search, contentDescription = null)
                Text("Buscar")
            }
            /*
            Text("© Satchel 2025", fontSize = 14.sp)
            Text("Hecho con 💙", fontSize = 14.sp)
            */
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FooterPreview() {
    val navController = TestNavHostController(LocalContext.current)
    SatchelTheme {
        Footer(navController = navController)
    }
}

@Composable
fun AppButton (
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    content : @Composable RowScope.() -> Unit
    ) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
            contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
        )
    ) {
        content()
    }
}

@Composable
fun SearchField() {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    return OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        value = text,
        leadingIcon = { Icon(imageVector = Icons.Default.Search,
            contentDescription = "searchIcon") },
        onValueChange = {
            text = it
        },
        placeholder = { Text (text = "Que libro estamos buscando?") }
    )
}

@Preview (showBackground = true)
@Composable
fun SearchPreview(){
    SearchField()
}

@Composable
fun SatchelBodyContainer(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Box (
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        Column (
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize(),
            content = content
        )
    }
}

@Composable
fun BookCard(book: Book){
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(modifier = Modifier
            .width(200.dp)
            .height(260.dp),
            elevation = CardDefaults.cardElevation(7.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.DarkGray
            )
        ) {
            Column (modifier = Modifier.fillMaxSize()){
                val painter = rememberAsyncImagePainter(
                    model = book.imageUri ?: R.drawable.empty_image
                )
                Image(painter = painter,
                    contentDescription = "null",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    contentScale = ContentScale.Crop
                )
                Text(text = book.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.White,
                    modifier = Modifier
                        .padding(horizontal = 6.dp)
                        .padding(top = 6.dp),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis

                )

                Text(text = book.author,
                    fontSize = 13.sp,
                    color = Color.LightGray,
                    modifier = Modifier
                        .padding(horizontal = 6.dp)
                        .padding(top = 2.dp)
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewBookCard() {
    val exampleBook = Book()
    BookCard(book = exampleBook)

}


/*
@Composable
fun CajasAbril(){

    Box(contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()) {


        Column (modifier = Modifier.padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)){

            Box(modifier = Modifier
                .size(400.dp, 100.dp)
                .background(Color.DarkGray),
                contentAlignment = Alignment.Center) {

                Box(modifier = Modifier
                    .size(400.dp, 50.dp)
                    .background(Color.Green),
                    contentAlignment = Alignment.Center) {
                }
            }

            Box(modifier = Modifier
                .size(400.dp, 100.dp)
                .background(Color.DarkGray),
                contentAlignment = Alignment.Center) {

                Box(modifier = Modifier
                    .size(400.dp, 50.dp)
                    .background(Color.Green),
                    contentAlignment = Alignment.Center) {
                }

            }
        }



    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBoxes(){
    CajasAbril()
}
*/