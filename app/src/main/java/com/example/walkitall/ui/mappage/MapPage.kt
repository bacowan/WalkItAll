package com.example.walkitall.ui.mappage

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.walkitall.ui.theme.WalkItAllTheme
import com.example.walkitall.utils.loadProperties
import com.google.android.gms.location.LocationServices
import com.maplibre.compose.MapView
import com.maplibre.compose.camera.CameraState
import com.maplibre.compose.camera.MapViewCamera
import com.maplibre.compose.rememberSaveableMapViewCamera


@Composable
fun MapPage(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    var isWalking by remember { mutableStateOf(false) }
    var location by remember { mutableStateOf<Location?>(null) }
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    val styleUrl = remember { getStyleUrl(context) }

    var mapViewCamera = rememberSaveableMapViewCamera(
        initialCamera = MapViewCamera(
            CameraState.Centered(
                latitude = 35.689487,
                longitude = 139.691711
            )
        )
    )

    // Get the initial location based on the last known location
    LaunchedEffect(Unit) {
        val coarseLocationGranted = ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (coarseLocationGranted) {
            fusedLocationClient.lastLocation.addOnSuccessListener { initialLocation: Location? ->
                location = initialLocation
            }
        }
    }


    Box(modifier = modifier.fillMaxSize()) {
        MapView(
            modifier = Modifier.fillMaxSize(),
            styleUrl = styleUrl,
            camera = mapViewCamera
        )

        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            onClick = { isWalking = !isWalking }
        ) {
            if (isWalking) {
                Icon(
                    imageVector = Icons.Filled.Stop,
                    contentDescription = "Stop"
                )
            }
            else {
                Icon(
                    imageVector = Icons.Filled.PlayArrow,
                    contentDescription = "Start"
                )
            }
        }
    }

    if (isWalking) {
        LocationPermissionRequester()
    }
}

private fun getStyleUrl(context: Context): String {
    val apiKey = try {
        val properties = loadProperties(context)
        properties.getProperty("MAPTILER_KEY")
    } catch (_: Exception) {
        // TODO: Exception handling
        null
    }
    return apiKey?.let { "https://api.maptiler.com/maps/streets/style.json?key=$it" }
        ?: "https://demotiles.maplibre.org/style.json"
}

@Preview(showBackground = true)
@Composable
fun MapViewPreview() {
    WalkItAllTheme {
        MapPage()
    }
}