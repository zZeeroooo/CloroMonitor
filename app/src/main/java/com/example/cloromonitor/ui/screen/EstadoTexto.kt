package com.example.cloromonitor.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.cloromonitor.R
import com.example.cloromonitor.data.model.EstadoCloro

@Composable
fun EstadoCloro.texto(): String = when (this) {
    EstadoCloro.NORMAL -> stringResource(R.string.estado_normal)
    EstadoCloro.ADVERTENCIA -> stringResource(R.string.estado_advertencia)
    EstadoCloro.CRITICO -> stringResource(R.string.estado_critico)
}