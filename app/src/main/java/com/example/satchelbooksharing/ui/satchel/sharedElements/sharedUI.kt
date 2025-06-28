package com.example.satchelbooksharing.ui.satchel.sharedElements

import android.net.Uri
import android.service.autofill.OnClickAction
import com.example.satchelbooksharing.model.satchel.Book
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import com.example.satchelbooksharing.R
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import androidx.navigation.testing.TestNavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.satchelbooksharing.ui.satchel.navigation.ScreensRoute
import com.example.satchelbooksharing.ui.satchel.ui.theme.SatchelTheme
import kotlinx.coroutines.launch
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.derivedStateOf
import com.example.satchelbooksharing.model.satchel.BookRequest
import com.google.android.gms.auth.api.phone.SmsCodeAutofillClient.PermissionState

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
fun Header (navController: NavController) {
    Surface(

        tonalElevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
    ) {
        Row (modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color.Cyan,
                shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp), ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AppButton(onClick = {navController.navigate(ScreensRoute.ScreenProfileRoute.route)}) {
                Icon(Icons.Default.Person, contentDescription = null)

            }
            AppButton(onClick = {navController.navigate(ScreensRoute.ScreenLibraryRoute.route)}) {
                Icon(Icons.Default.Notifications, contentDescription = null)

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HeaderPreview() {
    Header(rememberNavController())
}

@Composable
fun Footer (navController: NavController) {
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
            AppButton(onClick = {navController.navigate(ScreensRoute.ScreenLibraryRoute.route)}) {
                Icon(Icons.AutoMirrored.Filled.List, contentDescription = null)

            }
            AppButton(onClick = {navController.navigate(ScreensRoute.ScreenHomeRoute.route)}) {
                Icon(Icons.Default.Home, contentDescription = null)

            }
            AppButton(onClick = {navController.navigate(ScreensRoute.ScreenSearchRoute.route)}) {
                Icon(Icons.Default.Search, contentDescription = null)

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
    content : @Composable RowScope.() -> Unit
    ) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxHeight()
            .padding(2.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = Color.Black)
        )
    {
        content()
    }
}

@Composable
fun SearchField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    onSearch: () -> Unit
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        value = value,
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "searchIcon"
            )
        },
        onValueChange = onValueChange,
        placeholder = { Text (text = "¿Que libro estamos buscando?") },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearch()
            }
        )
    )
}

@Preview (showBackground = true)
@Composable
fun SearchPreview(){
    //SearchField()
}

@Composable
fun SatchelBodyContainer(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .clip(RoundedCornerShape(24.dp))
            .background(Color.LightGray.copy(alpha = 0.5f))
            .padding(horizontal = 1.dp, vertical = 10.dp)
    ) {
        content()
    }
}

@Composable
fun BookCard(book: Book, onClick: () -> Unit){
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(modifier = Modifier
            .width(270.dp)
            .height(290.dp)
            .clickable { onClick() },
            elevation = CardDefaults.cardElevation(7.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.DarkGray
            )
        ) {
            Column (modifier = Modifier.fillMaxSize()){
                val painter = if(!book.imageUri.isNullOrEmpty()) {
                    rememberAsyncImagePainter(model = Uri.parse(book.imageUri))
                } else {
                    painterResource(id = R.drawable.empty_image)
                }
                Image(painter = painter,
                    contentDescription = null,
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

                Text(text = book.description,
                    fontSize = 13.sp,
                    color = Color.LightGray,
                    modifier = Modifier
                        .padding(horizontal = 6.dp)
                        .padding(top = 2.dp),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewBookCard() {
    val exampleBook = Book()
    //BookCard(book = exampleBook)

}

@Composable
fun RequestToggleButton(
    requested: Boolean,
    onToggle: (Boolean) -> Unit
) {
    val toggleState = remember { mutableStateOf(requested) }

    Badge(modifier = Modifier.clickable {
        toggleState.value = !toggleState.value
        onToggle(toggleState.value)
    }) {
        Text(text = if (toggleState.value) "Cancelar Pedido" else "Pedir")
    }
}


@Composable
fun PrestamosSwipeView(
    prestados: List<BookRequest>,
    recibidos: List<BookRequest>
) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Prestados", "Recibidos")

    Column {
        TabRow(selectedTabIndex = selectedTab) {
            tabs.forEachIndexed { index, title ->
                Tab(selected = selectedTab == index, onClick = { selectedTab = index }) {
                    Text(title, modifier = Modifier.padding(16.dp))
                }
            }
        }

        val lista = if (selectedTab == 0) prestados else recibidos
        if (lista.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No hay préstamos en esta sección.")
            }
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(lista) { request ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text("📖 ${request.bookTitle}")
                            Text(if (selectedTab == 0) "📤 Prestado a: ${request.requesterName}" else "📥 Prestado por: ${request.ownerName}")
                        }
                    }
                }
            }
        }
    }
}
