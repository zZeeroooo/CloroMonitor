package com.example.cloromonitor.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cloromonitor.R
import com.example.cloromonitor.data.model.EstadoCloro
import com.example.cloromonitor.ui.viewmodel.MainViewModel

@Composable
fun HistorialScreen(viewModel: MainViewModel) {
    val historial by viewModel.historial.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text = stringResource(R.string.historial_titulo),
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (historial.isEmpty()) {
            Text(text = stringResource(R.string.historial_vacio))
        } else {
            LazyColumn {
                items(historial, key = { it.id }) { medicion ->
                    val colorFondo = when (medicion.estado) {
                        EstadoCloro.NORMAL -> Color(0xFFC8E6C9)
                        EstadoCloro.ADVERTENCIA -> Color(0xFFFFF3C4)
                        EstadoCloro.CRITICO -> Color(0xFFFFCDD2)
                    }


                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        colors = CardDefaults.cardColors(containerColor = colorFondo)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "${medicion.granja} - ${medicion.punto}",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(text = stringResource(R.string.historial_cloro, medicion.nivelCloro))
                            Text(text = stringResource(R.string.historial_fecha, medicion.actualizado))
                            Text(text = stringResource(R.string.historial_fecha, medicion.actualizado))
                            if (medicion.accionCorrectiva) {
                                Text(
                                    text = stringResource(
                                        R.string.historial_accion,
                                        medicion.observacion
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}