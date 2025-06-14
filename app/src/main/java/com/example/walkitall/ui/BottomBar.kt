package com.example.walkitall.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.walkitall.ui.theme.WalkItAllTheme

@Composable
fun BottomBar(
    onMapClicked: () -> Unit,
    onEditClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    BottomAppBar(
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = modifier.fillMaxWidth()
        ) {
            IconButton(onClick = onMapClicked) {
                Icon(
                    imageVector = Icons.Filled.Map,
                    contentDescription = "Map"
                )
            }

            IconButton(onClick = onEditClicked) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "Edit"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppBottomBarPreview() {
    WalkItAllTheme {
        BottomBar(
            onMapClicked = { /* TODO: Handle map click for preview */ },
            onEditClicked = { /* TODO: Handle edit click for preview */ }
        )
    }
}