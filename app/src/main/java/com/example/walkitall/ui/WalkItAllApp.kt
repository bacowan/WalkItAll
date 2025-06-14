package com.example.walkitall.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.walkitall.ui.mapview.MapView
import com.example.walkitall.ui.theme.WalkItAllTheme

@Composable
fun WalkItAllApp(modifier: Modifier = Modifier) {
    Scaffold(
        bottomBar = {
            BottomBar(
                onMapClicked = {},
                onEditClicked = {},
                modifier)
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        MapView(Modifier.Companion.padding(innerPadding))
    }
}

@Preview(showBackground = true)
@Composable
fun WalkItAllAppPreview() {
    WalkItAllTheme {
        WalkItAllApp()
    }
}