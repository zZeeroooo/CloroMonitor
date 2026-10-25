package com.example.cloromonitor.util

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper

object UbicacionHelper {

    @SuppressLint("MissingPermission")
    fun obtenerUbicacion(context: Context, onResultado: (Location?) -> Unit) {
        val manager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val proveedores = listOf(
            LocationManager.GPS_PROVIDER,
            LocationManager.NETWORK_PROVIDER
        ).filter { manager.isProviderEnabled(it) }

        if (proveedores.isEmpty()) {
            onResultado(null)
            return
        }

        // 1) Intenta usar la última ubicación conocida
        val ultima = proveedores
            .mapNotNull { manager.getLastKnownLocation(it) }
            .maxByOrNull { it.time }
        if (ultima != null) {
            onResultado(ultima)
            return
        }

        // 2) Si no hay, pide una ubicación nueva
        val listener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                onResultado(location)
            }

            override fun onProviderEnabled(provider: String) {}

            override fun onProviderDisabled(provider: String) {}

            @Deprecated("Deprecated in Java")
            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
        }
        manager.requestSingleUpdate(proveedores.first(), listener, Looper.getMainLooper())
    }
}