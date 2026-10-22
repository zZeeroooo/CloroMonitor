package com.example.cloromonitor.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cloromonitor.ui.viewmodel.ControlViewModel
import com.example.cloromonitor.ui.viewmodel.MainViewModel

@Composable
fun RegistroControlScreen(
    controlViewModel: ControlViewModel,
    mainViewModel: MainViewModel,
    onRegistrar: () -> Unit
) {
    val estado by controlViewModel.uiState.collectAsStateWithLifecycle()
    val errores by controlViewModel.errores.collectAsStateWithLifecycle()
    val puntos by mainViewModel.puntos.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        Text(
            text = "Registrar control",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Punto de medición",
            style = MaterialTheme.typography.titleMedium
        )
        puntos.forEach { punto ->
            val nombre = "${punto.granja} - ${punto.punto}"
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { controlViewModel.onPuntoChange(nombre) },
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = estado.punto == nombre,
                    onClick = { controlViewModel.onPuntoChange(nombre) }
                )
                Text(text = nombre)
            }
        }
        errores.punto?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = estado.nivelCloro,
            onValueChange = controlViewModel::onNivelCloroChange,
            label = { Text("Nivel de cloro libre (ppm)") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            isError = errores.nivelCloro != null,
            supportingText = { errores.nivelCloro?.let { Text(it) } },
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    controlViewModel.onAccionCorrectivaChange(!estado.accionCorrectiva)
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = estado.accionCorrectiva,
                onCheckedChange = controlViewModel::onAccionCorrectivaChange
            )
            Text(text = "Se aplicó una acción correctiva")
        }

        OutlinedTextField(
            value = estado.observacion,
            onValueChange = controlViewModel::onObservacionChange,
            label = { Text("Observación") },
            minLines = 3,
            isError = errores.observacion != null,
            supportingText = { errores.observacion?.let { Text(it) } },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (controlViewModel.validarFormulario()) onRegistrar()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrar control")
        }
    }
}