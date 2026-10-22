package com.example.cloromonitor.data.model

data class ControlUiState(
    val punto: String = "",
    val nivelCloro: String = "",
    val accionCorrectiva: Boolean = false,
    val observacion: String = ""
)

data class ControlErrores(
    val punto: String? = null,
    val nivelCloro: String? = null,
    val observacion: String? = null
)