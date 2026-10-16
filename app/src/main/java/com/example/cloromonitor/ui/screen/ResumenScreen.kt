package com.example.cloromonitor.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cloromonitor.data.model.EstadoCloro
import com.example.cloromonitor.ui.viewmodel.MainViewModel

@Composable
fun ResumenScreen(viewModel: MainViewModel) {
    val puntos by viewModel.puntos.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text = "Resumen de cloro",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        ResumenCard("Normales", puntos.count { it.estado == EstadoCloro.NORMAL })
        ResumenCard("En advertencia", puntos.count { it.estado == EstadoCloro.ADVERTENCIA })
        ResumenCard("Críticos", puntos.count { it.estado == EstadoCloro.CRITICO })
    }
}

@Composable
private fun ResumenCard(titulo: String, cantidad: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = titulo, style = MaterialTheme.typography.titleMedium)
            Text(text = "$cantidad puntos")
        }
    }
}