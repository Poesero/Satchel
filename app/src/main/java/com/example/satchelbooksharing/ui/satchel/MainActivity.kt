package com.example.satchelbooksharing.ui.satchel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.satchelbooksharing.ui.satchel.navigation.SatchelNavGraph
import com.example.satchelbooksharing.ui.satchel.ui.theme.SatchelTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SatchelTheme {
                Surface(modifier = Modifier.fillMaxSize()){
                    val navController = rememberNavController()
                    SatchelNavGraph(navController = navController)
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewMain(){
    SatchelTheme {
        Surface {
            val navController = rememberNavController()
            SatchelNavGraph(navController = navController)
        }
    }
}



