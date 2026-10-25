package com.example.cloromonitor.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "puntos")
data class PuntoMedicion(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val granja: String,
    val punto: String,
    val nivelCloro: Double,      // ppm
    val actualizado: String,     // "16-10-2026 09:30"
    val fotoUri: String? = null,
    val latitud: Double? = null,
    val longitud: Double? = null
) {
    val estado: EstadoCloro
        get() = when {
            nivelCloro in 0.5..1.5 -> EstadoCloro.NORMAL
            nivelCloro in 0.3..2.0 -> EstadoCloro.ADVERTENCIA
            else -> EstadoCloro.CRITICO
        }
}