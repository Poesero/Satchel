package com.example.satchelbooksharing.ui.satchel.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.satchelbooksharing.model.satchel.BookRequest
import com.example.satchelbooksharing.ui.satchel.sharedElements.Footer
import com.example.satchelbooksharing.ui.satchel.sharedElements.PrestamosSwipeView
import com.example.satchelbooksharing.ui.satchel.sharedElements.SatchelBodyContainer
import com.example.satchelbooksharing.ui.satchel.ui.theme.SatchelGrey
import com.example.satchelbooksharing.ui.satchel.ui.theme.SatchelWhite
import com.example.satchelbooksharing.viewModel.satchel.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

@Composable
fun ProfileScreen(navController: NavController) {
    val viewModel: ProfileViewModel = viewModel()
    val solicitudes = viewModel.requestsPendientes.value
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.cargarRequestsPendientes()
        viewModel.cargarPrestamos()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        ProfileHeader()

        SolicitudesPendientesSection(
            solicitudes = solicitudes,
            onAceptar = { solicitud ->
                coroutineScope.launch {
                    val requesterId = viewModel.aceptarRequest(solicitud)
                    if (requesterId != null) {
                        val chatId = "${solicitud.bookId}_$requesterId"
                        navController.navigate("chat_screen/$chatId")
                    }
                }
            },
            onRechazar = { solicitud ->
                coroutineScope.launch {
                    viewModel.rechazarRequest(solicitud)
                }
            }
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(12.dp)
        ) {
            SatchelBodyContainer(Modifier.weight(1f)) {
                SatchelBodyContainer(Modifier.weight(1f)) {
                    PrestamosSwipeView(
                        prestados = viewModel.prestamosDado.value,
                        recibidos = viewModel.prestamosRecibido.value,
                        navController = navController
                    )

                }
            }
        }

        Footer(navController = navController)
    }
}

@Composable
fun ProfileHeader() {
    var menuExpanded by remember { mutableStateOf(false) }

    BackHandler(enabled = menuExpanded) {
        menuExpanded = false
    }

    Surface(
        color = Color.Transparent,
        tonalElevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = SatchelWhite,
                    shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp),
                ),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box {
                Button(
                    onClick = { menuExpanded = true },
                    modifier = Modifier.fillMaxHeight(),
                    colors = ButtonDefaults.buttonColors(SatchelWhite)
                ) {
                    Icon(Icons.Default.Settings, contentDescription = null)
                }

                DropdownMenu(
                    expanded = menuExpanded,
                    onDismissRequest = { menuExpanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Cerrar sesión") },
                        onClick = {
                            menuExpanded = false
                            FirebaseAuth.getInstance().signOut()
                            exitProcess(0)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun SolicitudesPendientesSection(
    solicitudes: List<BookRequest>,
    onAceptar: (BookRequest) -> Unit,
    onRechazar: (BookRequest) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(SatchelGrey)
                .clickable { expanded = !expanded }
                .padding(16.dp)
        ) {
            Text("Solicitudes pendientes: (${solicitudes.size})")
        }
        if (expanded) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp)

            ) {
                if (solicitudes.isEmpty()) {
                    Text("No hay solicitudes pendientes.")
                } else {
                    solicitudes.forEach { solicitud ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            elevation = CardDefaults.cardElevation(0.dp),
                            colors = CardDefaults.cardColors(containerColor = SatchelGrey)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text("📘 Libro: ${solicitud.bookTitle}")
                                Text("🙋 Solicitante: ${solicitud.requesterName}")
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    modifier = Modifier.padding(top = 8.dp)
                                ) {
                                    Button(
                                        onClick = { onAceptar(solicitud) },
                                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary)
                                    ) {
                                        Text("Aceptar")
                                    }
                                    Button(
                                        onClick = { onRechazar(solicitud) },
                                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primaryContainer)
                                    ) {
                                        Text("Rechazar")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
