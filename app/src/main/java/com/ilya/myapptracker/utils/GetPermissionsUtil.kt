package com.ilya.myapptracker.utils

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import com.google.accompanist.permissions.*


@RequiresApi(Build.VERSION_CODES.Q)
@ExperimentalPermissionsApi

@Composable
fun GetPermissionsUtil(

){

    val finePerm = rememberPermissionState(permission = android.Manifest.permission.ACCESS_FINE_LOCATION,)
    val backgroundPerm = rememberPermissionState(permission = android.Manifest.permission.ACCESS_BACKGROUND_LOCATION,)
    val showDialog = remember {
        mutableStateOf(false)
    }

    if(!finePerm.status.isGranted){
        LaunchedEffect(key1 = true, block = {
            finePerm.launchPermissionRequest()
        })
    }

    if(!backgroundPerm.status.isGranted) {
        showDialog.value = true
        PermissionAlert(permissionState = backgroundPerm, showDialog =showDialog)
    }

    if(finePerm.status.isGranted && backgroundPerm.status.isGranted){
        showDialog.value = false
    }


}

