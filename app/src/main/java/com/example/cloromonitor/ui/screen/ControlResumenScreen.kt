package com.example.cloromonitor.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cloromonitor.ui.viewmodel.ControlViewModel

@Composable
fun ControlResumenScreen(
    controlViewModel: ControlViewModel,
    onVolver: () -> Unit
) {
    val estado by controlViewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text = "Control registrado",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Punto: ${estado.punto}")
                Text(text = "Cloro libre: ${estado.nivelCloro} ppm")
                Text(
                    text = "Acción correctiva: " +
                            if (estado.accionCorrectiva) "Sí" else "No"
                )
                Text(
                    text = "Observación: " +
                            estado.observacion.ifBlank { "Sin observaciones" }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onVolver,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver al resumen")
        }
    }
}