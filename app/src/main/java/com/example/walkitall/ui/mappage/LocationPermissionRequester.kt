package com.example.walkitall.ui.mappage

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

enum class RequestStage { SHOW_RATIONALE, REQUEST_FOREGROUND, REQUEST_BACKGROUND, COMPLETE }

@Composable
fun LocationPermissionRequester() {
    val context = LocalContext.current
    val activity = LocalActivity.current
    // turns false when the user confirms the dialog
    var requestStage by remember { mutableStateOf(RequestStage.SHOW_RATIONALE) }
    var hasForegroundLocation by remember { mutableStateOf<Boolean?>(null) }
    var hasBackgroundLocation by remember { mutableStateOf<Boolean?>(null) }

    val foregroundPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permission ->
            if (permission[Manifest.permission.ACCESS_COARSE_LOCATION] == true) {
                // move to asking for background location permissions
                requestStage = RequestStage.REQUEST_BACKGROUND
            }
            else {
                // don't bother asking for background location if we don't have foreground
                requestStage = RequestStage.COMPLETE
            }
        }
    )
    val backgroundPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = {
            // We don't use the permissions result for now.
            // If we want to in the future, do it here with the result value.
            requestStage = RequestStage.COMPLETE
        }
    )

    // All permissions begin as "null". This loads them in as true or false.
    val loadPermissions = {
        hasForegroundLocation = ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        hasBackgroundLocation = ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_BACKGROUND_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    LaunchedEffect(Unit) { loadPermissions() }

    // check if permissions are loaded
    if (activity != null &&
        //hasFineLocation != null &&
        hasForegroundLocation != null &&
        hasBackgroundLocation != null) {

        when (requestStage) {
            RequestStage.SHOW_RATIONALE -> {
                val shouldShowForegroundRationale = ActivityCompat.shouldShowRequestPermissionRationale(
                    activity,
                    Manifest.permission.ACCESS_COARSE_LOCATION)
                val shouldShowBackgroundRationale = ActivityCompat.shouldShowRequestPermissionRationale(
                    activity,
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION)

                if (shouldShowForegroundRationale || shouldShowBackgroundRationale) {
                    // If we need to explain, then show a dialog to explain the rationale
                    PermissionRationale(
                        // if accepted, then get ready to request the permissions
                        onConfirm = {
                            requestStage = if (hasForegroundLocation == true) {
                                // skip the foreground request if we already have it
                                RequestStage.REQUEST_BACKGROUND
                            } else {
                                RequestStage.REQUEST_FOREGROUND
                            }
                        },
                        // canceling will just end the process
                        onDismiss = { requestStage = RequestStage.COMPLETE }
                    )
                }
                else {
                    // Otherwise we can go strait to requesting permissions
                    requestStage = if (hasForegroundLocation == true) {
                        // skip the foreground request if we already have it
                        RequestStage.REQUEST_BACKGROUND
                    } else {
                        RequestStage.REQUEST_FOREGROUND
                    }
                }
            }

            RequestStage.REQUEST_FOREGROUND -> {
                // request both types of foreground permissions
                foregroundPermissionLauncher.launch(arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ))
            }

            RequestStage.REQUEST_BACKGROUND -> {
                // request background permissions
                backgroundPermissionLauncher.launch(arrayOf(
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
                ))
            }

            RequestStage.COMPLETE -> {
                // Nothing should display in this case
            }
        }
    }
}