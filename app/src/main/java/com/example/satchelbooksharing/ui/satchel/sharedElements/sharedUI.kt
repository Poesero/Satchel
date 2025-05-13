package com.example.satchelbooksharing.ui.satchel.sharedElements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.satchelbooksharing.ui.satchel.ui.theme.SatchelTheme

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
fun Footer (
    navController: NavController = rememberNavController(),
    currentDestination: String,
    ){
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
            AppButton(onClick = {navController.navigate("home") }) {
                Icon(Icons.Default.Home, contentDescription = null)
                Text("Inicio")
            }
            AppButton(onClick = {navController.navigate("profile")}) {
                Icon(Icons.Default.Person, contentDescription = null)
                Text("perfil")
            }
            AppButton(onClick = {navController.navigate("profile")}) {
                Icon(Icons.Default.Person, contentDescription = null)
                Text("perfil2")
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
    SatchelTheme {
        Footer(navController = rememberNavController(), currentDestination = "home")
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