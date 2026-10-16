package com.example.cloromonitor.data.model

data class PuntoMedicion(
    val id: Int,
    val granja: String,
    val punto: String,
    val nivelCloro: Double,      // ppm
    val actualizado: String      // "16-10-2026 09:30"
) {
    val estado: EstadoCloro
        get() = when {
            nivelCloro in 0.5..1.5 -> EstadoCloro.NORMAL
            nivelCloro in 0.3..2.0 -> EstadoCloro.ADVERTENCIA
            else -> EstadoCloro.CRITICO
        }
}