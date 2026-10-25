package com.example.cloromonitor.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Agriculture
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cloromonitor.ui.viewmodel.MainViewModel
import com.example.cloromonitor.ui.viewmodel.NuevoPuntoViewModel

@Composable
fun NuevoPuntoScreen(
    nuevoPuntoViewModel: NuevoPuntoViewModel,
    mainViewModel: MainViewModel,
    onGuardado: () -> Unit
) {
    val estado by nuevoPuntoViewModel.uiState.collectAsStateWithLifecycle()
    val errores by nuevoPuntoViewModel.errores.collectAsStateWithLifecycle()
    val puntos by mainViewModel.puntos.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        Text(
            text = "Agregar punto de medición",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = estado.granja,
            onValueChange = nuevoPuntoViewModel::onGranjaChange,
            label = { Text("Granja") },
            singleLine = true,
            leadingIcon = { Icon(Icons.Default.Agriculture, contentDescription = null) },
            trailingIcon = {
                if (errores.granja != null) {
                    Icon(
                        Icons.Default.Error,
                        contentDescription = "Error",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            },
            isError = errores.granja != null,
            supportingText = { errores.granja?.let { Text(it) } },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = estado.punto,
            onValueChange = nuevoPuntoViewModel::onPuntoChange,
            label = { Text("Nombre del punto") },
            singleLine = true,
            leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null) },
            trailingIcon = {
                if (errores.punto != null) {
                    Icon(
                        Icons.Default.Error,
                        contentDescription = "Error",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            },
            isError = errores.punto != null,
            supportingText = { errores.punto?.let { Text(it) } },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = estado.nivelCloro,
            onValueChange = nuevoPuntoViewModel::onNivelCloroChange,
            label = { Text("Nivel inicial de cloro (ppm)") },
            singleLine = true,
            leadingIcon = { Icon(Icons.Default.WaterDrop, contentDescription = null) },
            trailingIcon = {
                if (errores.nivelCloro != null) {
                    Icon(
                        Icons.Default.Error,
                        contentDescription = "Error",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            isError = errores.nivelCloro != null,
            supportingText = { errores.nivelCloro?.let { Text(it) } },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val existentes = puntos.map { "${it.granja} - ${it.punto}" }
                if (nuevoPuntoViewModel.validarFormulario(existentes)) {
                    mainViewModel.agregarPunto(
                        estado.granja,
                        estado.punto,
                        estado.nivelCloro
                    )
                    nuevoPuntoViewModel.limpiarFormulario()
                    onGuardado()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar punto")
        }
    }
}
