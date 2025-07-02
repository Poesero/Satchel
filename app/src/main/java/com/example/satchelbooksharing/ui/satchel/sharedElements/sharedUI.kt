package com.example.satchelbooksharing.ui.satchel.sharedElements

import android.content.Context
import android.net.Uri
import android.service.autofill.OnClickAction
import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import com.example.satchelbooksharing.navigation.ScreensRoute
import com.example.satchelbooksharing.ui.satchel.ui.theme.SatchelTheme
import kotlinx.coroutines.launch
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalConfiguration
import com.example.satchelbooksharing.model.satchel.BookRequest
import com.example.satchelbooksharing.ui.satchel.ui.theme.SatchelGrey
import com.example.satchelbooksharing.ui.satchel.ui.theme.SatchelOrange
import com.example.satchelbooksharing.ui.satchel.ui.theme.SatchelOrange2
import com.example.satchelbooksharing.ui.satchel.ui.theme.SatchelWhite
import com.example.satchelbooksharing.ui.satchel.ui.theme.SatchelYellow
import com.example.satchelbooksharing.ui.satchel.ui.theme.SatchelYellow2
import com.google.android.gms.auth.api.phone.SmsCodeAutofillClient.PermissionState
import kotlinx.coroutines.delay

@Composable
fun Header (navController: NavController) {
    Surface(
        color = Color.Transparent,
        tonalElevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
    ) {
        Row (modifier = Modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.tertiary,
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
        color = MaterialTheme.colorScheme.primary,
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
            .padding(5.dp)
            .background(MaterialTheme.colorScheme.tertiary),
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
fun SatchelBodyContainer(modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(10.dp,10.dp)
            .clip(RoundedCornerShape(50.dp))
            .background(SatchelYellow2)
            .padding(horizontal = 5.dp, vertical = 5.dp)
    ) {
        Box(modifier = Modifier
            .padding(horizontal = 7.dp, vertical = 7.dp)
            .clip(RoundedCornerShape(20.dp))) {
            content()
        }
    }
}

@Composable
fun BookCard(book: Book, onClick: () -> Unit){
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(modifier = Modifier
            .width(265.dp)
            .height(280.dp)
            .clickable { onClick() },
            elevation = CardDefaults.cardElevation(3.dp),
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

    Badge(modifier = Modifier
        .padding(end = 3.dp)
        .clickable {
        toggleState.value = !toggleState.value
        onToggle(toggleState.value)
    }) {
        Text(text = if (toggleState.value) "Cancelar Pedido" else "Pedir")
    }
}


@Composable
fun PrestamosSwipeView(
    prestados: List<BookRequest>,
    recibidos: List<BookRequest>,
    navController: NavController
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
                    val chatId = "${request.bookId}_${request.requesterId}"
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable {
                                navController.navigate("chat_screen/$chatId")
                            },
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text("📖 ${request.bookTitle}")
                            Text(
                                if (selectedTab == 0)
                                    "📤 Prestado a: ${request.requesterName}"
                                else
                                    "📥 Prestado por: ${request.ownerName}"
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ImageCarousel(
    imageList: List<Int>,
    context: Context = LocalContext.current
) {
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val imageWidth = screenWidth * 0.85f
    val sidePadding = (screenWidth - imageWidth) / 2

    val currentIndex by remember {
        derivedStateOf { listState.firstVisibleItemIndex }
    }

    LaunchedEffect(Unit) {
        while (true) {
            delay(3000)
            val nextIndex = (currentIndex + 1) % imageList.size
            scope.launch {
                listState.animateScrollToItem(nextIndex)
            }
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        LazyRow(
            state = listState,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentPadding = PaddingValues(horizontal = sidePadding),
            horizontalArrangement = Arrangement.spacedBy(40.dp),
            flingBehavior = rememberSnapFlingBehavior(listState)
        ) {
            items(imageList.size) { index ->
                Image(
                    painter = painterResource(id = imageList[index]),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(imageWidth)
                        .height(200.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .clickable {
                            Toast.makeText(
                                context,
                                "No se pudo acceder al destino, intente más tarde",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            repeat(imageList.size) { index ->
                val isSelected = index == currentIndex
                Box(
                    modifier = Modifier
                        .size(if (isSelected) 12.dp else 8.dp)
                        .clip(CircleShape)
                        .background(
                            if (isSelected) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                        )
                )
            }
        }
    }
}

@Composable
fun SatchelBadge(content: @Composable () -> Unit) {
    Box(modifier = Modifier.padding(end = 3.dp)) {
        Badge(
            containerColor = SatchelGrey,
            contentColor = MaterialTheme.colorScheme.onTertiary
        ) {
            content()
        }
    }
}

@Composable
fun FadingImageSimple(
    imageRes: Int,
    isVisible: Boolean,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit
) {
    val alpha by animateFloatAsState(
        targetValue = if (isVisible) 0f else 1f,
        animationSpec = tween(durationMillis = 2899),
        label = ""
    )

    Image(
        painter = painterResource(id = imageRes),
        contentDescription = null,
        modifier = modifier.alpha(alpha),
        contentScale = contentScale
    )
}

