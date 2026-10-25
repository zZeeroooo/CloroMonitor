package com.example.cloromonitor.data.model

data class NuevoPuntoUiState(
    val granja: String = "",
    val punto: String = "",
    val nivelCloro: String = ""
)

data class NuevoPuntoErrores(
    val granja: String? = null,
    val punto: String? = null,
    val nivelCloro: String? = null
)