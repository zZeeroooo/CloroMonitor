package com.example.cloromonitor.data.model

import androidx.annotation.StringRes

data class ControlUiState(
    val punto: String = "",
    val nivelCloro: String = "",
    val accionCorrectiva: Boolean = false,
    val observacion: String = "",
    val latitud: Double? = null,
    val longitud: Double? = null
)

data class ControlErrores(
    @StringRes val punto: Int? = null,
    @StringRes val nivelCloro: Int? = null,
    @StringRes val observacion: Int? = null
)