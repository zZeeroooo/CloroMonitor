package com.example.cloromonitor.util

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.cloromonitor.data.model.EstadoCloro
import com.example.cloromonitor.data.model.PuntoMedicion

object NotificacionHelper {

    private const val CANAL_ID = "alertas_cloro"

    @SuppressLint("MissingPermission")
    fun mostrarAlerta(context: Context, punto: PuntoMedicion) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(
                context, Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) return

        val manager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(
                NotificationChannel(
                    CANAL_ID,
                    "Alertas de cloro",
                    NotificationManager.IMPORTANCE_HIGH
                )
            )
        }

        val estadoTexto = when (punto.estado) {
            EstadoCloro.CRITICO -> "CRÍTICO"
            EstadoCloro.ADVERTENCIA -> "ADVERTENCIA"
            EstadoCloro.NORMAL -> "NORMAL"
        }

        val notificacion = NotificationCompat.Builder(context, CANAL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_alert)
            .setContentTitle("Alerta de cloro: ${punto.punto}")
            .setContentText("${punto.nivelCloro} ppm - estado $estadoTexto")
            .setAutoCancel(true)
            .build()

        manager.notify(punto.punto.hashCode(), notificacion)
    }
}