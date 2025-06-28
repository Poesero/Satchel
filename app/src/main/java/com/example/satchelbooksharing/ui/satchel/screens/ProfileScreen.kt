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
import com.example.satchelbooksharing.viewModel.satchel.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlin.system.exitProcess

@Composable
fun ProfileScreen(navController: NavController) {
    val viewModel: ProfileViewModel = viewModel()
    val solicitudes = viewModel.requestsPendientes.value

    LaunchedEffect(Unit) {
        viewModel.cargarRequestsPendientes()
        viewModel.cargarPrestamos()
    }


    Column (
        modifier = Modifier.fillMaxSize()
    ) {
        ProfileHeader()

        SolicitudesPendientesSection(
            solicitudes = solicitudes,
            onAceptar = { viewModel.aceptarRequest(it) },
            onRechazar = { viewModel.rechazarRequest(it) }
        )


        Column(
            modifier = Modifier
                .weight(1f)
                .padding(24.dp)
        ) {
            SatchelBodyContainer(Modifier.weight(1f)) {
                SatchelBodyContainer(Modifier.weight(1f)) {
                    PrestamosSwipeView(
                        prestados = viewModel.prestamosDado.value,
                        recibidos = viewModel.prestamosRecibido.value
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
        tonalElevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = Color.Cyan,
                    shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp),
                ),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box {
                Button(
                    onClick = { menuExpanded = true },
                    modifier = Modifier.fillMaxHeight()
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

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)) {

        // Contenedor clickable
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                .clickable { expanded = !expanded }
                .padding(16.dp)
        ) {
            Text("Solicitudes pendientes: (${solicitudes.size})")
        }
        if (expanded) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                if (solicitudes.isEmpty()) {
                    Text("No hay solicitudes pendientes.")
                } else {
                    solicitudes.forEach { solicitud ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            elevation = CardDefaults.cardElevation(2.dp)
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
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
                                    ) {
                                        Text("Aceptar")
                                    }
                                    Button(
                                        onClick = { onRechazar(solicitud) },
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF44336))
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
