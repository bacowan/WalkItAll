package com.example.walkitall.ui.mappage

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.walkitall.ui.theme.WalkItAllTheme
import com.example.walkitall.utils.loadProperties
import com.maplibre.compose.MapView
import com.maplibre.compose.camera.CameraState
import com.maplibre.compose.camera.MapViewCamera
import com.maplibre.compose.rememberSaveableMapViewCamera


@Composable
fun MapPage(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val styleUrl = remember {
        val apiKey = try {
            val properties = loadProperties(context)
            properties.getProperty("MAPTILER_KEY")
        } catch (e: Exception) {
            // TODO: Exception handling
            null
        }
        apiKey?.let { "https://api.maptiler.com/maps/streets/style.json?key=$it" } ?: "https://demotiles.maplibre.org/style.json"
    }

    var mapViewCamera = rememberSaveableMapViewCamera(
        initialCamera = MapViewCamera(
            CameraState.Centered(
                latitude = 35.689487,
                longitude = 139.691711
            )
        )
    )
    MapView(
        modifier = modifier,
        styleUrl = styleUrl,
        camera = mapViewCamera
    )
}

@Preview(showBackground = true)
@Composable
fun MapViewPreview() {
    WalkItAllTheme {
        MapPage()
    }
}