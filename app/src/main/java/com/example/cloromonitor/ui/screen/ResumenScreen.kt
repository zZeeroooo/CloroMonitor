package com.example.cloromonitor.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cloromonitor.R
import com.example.cloromonitor.data.model.EstadoCloro
import com.example.cloromonitor.ui.viewmodel.MainViewModel

@Composable
@Preview(name = "Pequeña", widthDp = 400, heightDp = 800)
@Preview(name = "Grande", widthDp = 700, heightDp = 800)
fun ResumenScreen(viewModel: MainViewModel = viewModel()) {
    val puntos by viewModel.puntos.collectAsStateWithLifecycle()
    val normales = puntos.count { it.estado == EstadoCloro.NORMAL }
    val advertencia = puntos.count { it.estado == EstadoCloro.ADVERTENCIA }
    val criticos = puntos.count { it.estado == EstadoCloro.CRITICO }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        if (maxWidth < 600.dp) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Encabezado()
                Spacer(modifier = Modifier.height(16.dp))
                Tarjetas(normales, advertencia, criticos)
            }
        } else {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Encabezado()
                }
                Spacer(modifier = Modifier.width(32.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Tarjetas(normales, advertencia, criticos)
                }
            }
        }
    }
}

@Composable
private fun Encabezado() {
    Image(
        painter = painterResource(id = R.drawable.logo_cloro),
        contentDescription = "Logo CloroMonitor",
        modifier = Modifier.size(120.dp)
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = "Resumen de cloro",
        style = MaterialTheme.typography.headlineMedium
    )
}

@Composable
private fun Tarjetas(normales: Int, advertencia: Int, criticos: Int) {
    ResumenCard("Normales", normales)
    ResumenCard("En advertencia", advertencia)
    ResumenCard("Críticos", criticos)
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