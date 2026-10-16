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
import com.example.cloromonitor.ui.viewmodel.MainViewModel

@Composable
fun PuntosScreen(viewModel: MainViewModel) {
    val puntos by viewModel.puntos.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text = "Puntos de medición",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(puntos) { punto ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "${punto.granja} - ${punto.punto}",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(text = "Cloro libre: ${punto.nivelCloro} ppm")
                        Text(text = "Estado: ${punto.estado}")
                        Text(text = "Actualizado: ${punto.actualizado}")
                    }
                }
            }
        }
    }
}