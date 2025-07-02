package com.example.satchelbooksharing.ui.satchel.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.satchelbooksharing.model.satchel.Message
import com.example.satchelbooksharing.ui.satchel.sharedElements.Footer
import com.example.satchelbooksharing.ui.satchel.sharedElements.OwnerInfoPill
import com.example.satchelbooksharing.ui.satchel.ui.theme.SatchelWhite
import com.example.satchelbooksharing.ui.satchel.ui.theme.SatchelYellow2
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

@Composable
fun ChatScreen(navController: NavController, chatId: String) {
    val currentUserId = FirebaseAuth.getInstance().currentUser?.uid ?: return
    val db = Firebase.firestore
    val messages = remember { mutableStateListOf<Message>() }
    var newMessage by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    var otherUserPhotoUrl by remember { mutableStateOf<String?>(null) }
    var otherUserName by remember { mutableStateOf<String?>(null) }
    var otherUserId by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(chatId) {
        db.collection("chats").document(chatId).collection("messages")
            .orderBy("timestamp", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, _ ->
                if (snapshot != null && !snapshot.isEmpty) {
                    val nuevos = snapshot.documents.mapNotNull { doc ->
                        doc.toObject(Message::class.java)
                    }
                    messages.clear()
                    messages.addAll(nuevos)
                }
            }
    }

    LaunchedEffect(chatId) {
        val messagesSnapshot = db.collection("chats").document(chatId)
            .collection("messages")
            .orderBy("timestamp")
            .get()
            .await()

        val messageDocs = messagesSnapshot.documents
        val otherId = messageDocs.firstOrNull {
            it.getString("senderId") != currentUserId
        }?.getString("senderId")

        if (otherId != null) {
            otherUserId = otherId

            val userDoc = db.collection("profiles").document(otherId).get().await()
            otherUserPhotoUrl = userDoc.getString("photoUrl")
            otherUserName = userDoc.getString("name") ?: "Usuario"
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 48.dp)
        ) {
            if (otherUserId != null && otherUserName != null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, bottom = 8.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    OwnerInfoPill(
                        ownerId = otherUserId!!,
                        ownerName = otherUserName!!,
                        ownerPhotoUrl = otherUserPhotoUrl,
                        navController = navController
                    )
                }
            }

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
                    .background(SatchelYellow2),
                state = listState
            ) {
                items(messages) { msg ->
                    val isMe = msg.senderId == currentUserId
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = if (isMe) Arrangement.End else Arrangement.Start
                    ) {
                        Text(
                            text = msg.text,
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier
                                .padding(4.dp)
                                .background(
                                    color = if (isMe) Color(0xFFD0F0C0) else Color.LightGray,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(8.dp)
                        )
                    }
                }
            }

            LaunchedEffect(messages.size) {
                listState.animateScrollToItem(messages.size)
            }

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
                    val text = newMessage.trim()
                    if (text.isNotEmpty()) {
                        val message = mapOf(
                            "senderId" to currentUserId,
                            "text" to text,
                            "timestamp" to System.currentTimeMillis()
                        )
                        db.collection("chats").document(chatId)
                            .collection("messages")
                            .add(message)
                        newMessage = ""

                        db.collection("chats").document(chatId).update(
                            mapOf(
                                "lastMessage" to text,
                                "lastTimestamp" to System.currentTimeMillis()
                            )
                        )
                    }
                }) {
                    Text("Enviar")
                }
            }

            Footer(navController = navController)
        }
    }
}
