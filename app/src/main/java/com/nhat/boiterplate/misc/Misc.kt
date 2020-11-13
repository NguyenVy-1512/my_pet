package com.nhat.boiterplate.misc

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.model.LatLng
import com.nhat.presentation.model.PetShopView

fun PetShopView.buildLatLng() = LatLng(latitude, longitude)

fun Array<String>.checkPermission(context: Context) = all {
    ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
}