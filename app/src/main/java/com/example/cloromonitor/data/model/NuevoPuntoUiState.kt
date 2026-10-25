package com.example.cloromonitor.data.model

import androidx.annotation.StringRes

data class NuevoPuntoUiState(
    val granja: String = "",
    val punto: String = "",
    val nivelCloro: String = ""
)

data class NuevoPuntoErrores(
    @StringRes val granja: Int? = null,
    @StringRes val punto: Int? = null,
    @StringRes val nivelCloro: Int? = null
)