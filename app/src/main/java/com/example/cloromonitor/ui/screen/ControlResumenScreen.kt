package com.example.cloromonitor.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cloromonitor.R
import com.example.cloromonitor.ui.viewmodel.ControlViewModel

@Composable
fun ControlResumenScreen(
    controlViewModel: ControlViewModel,
    onVolver: () -> Unit
) {
    val estado by controlViewModel.uiState.collectAsStateWithLifecycle()
    val lat = estado.latitud
    val lon = estado.longitud

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text = stringResource(R.string.control_resumen_titulo),
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = stringResource(R.string.control_resumen_punto, estado.punto))
                Text(text = stringResource(R.string.control_resumen_cloro, estado.nivelCloro))
                Text(
                    text = stringResource(
                        R.string.control_resumen_accion,
                        stringResource(if (estado.accionCorrectiva) R.string.si else R.string.no)
                    )
                )
                Text(
                    text = stringResource(
                        R.string.control_resumen_observacion,
                        estado.observacion.ifBlank {
                            stringResource(R.string.control_resumen_sin_observaciones)
                        }
                    )
                )
                Text(
                    text = if (lat != null && lon != null)
                        stringResource(R.string.control_resumen_ubicacion, lat, lon)
                    else
                        stringResource(R.string.control_resumen_sin_ubicacion)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onVolver,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.control_resumen_volver))
        }
    }
}