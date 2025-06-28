package com.example.satchelbooksharing.ui.satchel.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.satchelbooksharing.ui.satchel.sharedElements.Footer

@Composable
fun ChatScreen(navController: NavController, chatId: String) {
    Column(modifier = Modifier.fillMaxSize()) {
        // Header con texto de chatId
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Text("Chat con ID: $chatId", style = MaterialTheme.typography.titleMedium)
        }

        // Simulación de mensajes
        val messages = remember {
            mutableStateListOf(
                "Hola, ¿seguís queriendo el libro?",
                "Sí, me interesa mucho. ¿Dónde podríamos encontrarnos?"
            )
        }
        var newMessage by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            messages.forEach { msg ->
                Text(
                    text = msg,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.LightGray, shape = RoundedCornerShape(8.dp))
                        .padding(8.dp)
                )
            }
        }

        // Campo para enviar mensaje
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = newMessage,
                onValueChange = { newMessage = it },
                placeholder = { Text("Escribe un mensaje...") }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                if (newMessage.isNotBlank()) {
                    messages.add("Tú: $newMessage")
                    newMessage = ""
                }
            }) {
                Text("Enviar")
            }
        }

        Footer(navController = navController)
    }
}
