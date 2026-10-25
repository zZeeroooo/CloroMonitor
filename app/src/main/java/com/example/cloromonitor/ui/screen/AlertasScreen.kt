package com.example.cloromonitor.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cloromonitor.R
import com.example.cloromonitor.data.model.EstadoCloro
import com.example.cloromonitor.data.model.PuntoMedicion
import com.example.cloromonitor.ui.viewmodel.MainViewModel

@Composable
@Preview(name = "Pequeña", widthDp = 400, heightDp = 800)
@Preview(name = "Grande", widthDp = 700, heightDp = 800)
fun AlertasScreen(viewModel: MainViewModel = viewModel()) {
    val puntos by viewModel.puntos.collectAsStateWithLifecycle()
    val alertas = puntos.filter { it.estado != EstadoCloro.NORMAL }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val columnas = if (maxWidth < 600.dp) 1 else 2

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Text(
                text = stringResource(R.string.alertas_titulo),
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(16.dp))

            if (alertas.isEmpty()) {
                Text(text = stringResource(R.string.alertas_vacio))
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(columnas),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(alertas) { punto ->
                        AlertaCard(punto)
                    }
                }
            }
        }
    }
}

@Composable
private fun AlertaCard(punto: PuntoMedicion) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = stringResource(
                    R.string.alertas_encabezado,
                    punto.estado.texto(),
                    punto.granja,
                    punto.punto
                ),
                style = MaterialTheme.typography.titleMedium
            )
            Text(text = stringResource(R.string.alertas_cloro, punto.nivelCloro))
            Text(text = stringResource(R.string.alertas_actualizado, punto.actualizado))
        }
    }
}