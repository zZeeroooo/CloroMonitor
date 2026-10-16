package com.example.cloromonitor.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cloromonitor.data.model.EstadoCloro
import com.example.cloromonitor.ui.viewmodel.MainViewModel

@Composable
fun AlertasScreen(viewModel: MainViewModel) {
    val puntos by viewModel.puntos.collectAsStateWithLifecycle()
    val alertas = puntos.filter { it.estado != EstadoCloro.NORMAL }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text = "Alertas activas",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (alertas.isEmpty()) {
            Text(text = "No hay alertas activas")
        } else {
            LazyColumn {
                items(alertas) { punto ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "${punto.estado}: ${punto.granja} - ${punto.punto}",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(text = "Cloro libre: ${punto.nivelCloro} ppm")
                            Text(text = "Actualizado: ${punto.actualizado}")
                        }
                    }
                }
            }
        }
    }
}