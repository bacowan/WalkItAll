package com.example.walkitall

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.walkitall.ui.WalkItAllApp
import com.example.walkitall.ui.theme.WalkItAllTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WalkItAllTheme {
                WalkItAllApp()
            }
        }
    }
}