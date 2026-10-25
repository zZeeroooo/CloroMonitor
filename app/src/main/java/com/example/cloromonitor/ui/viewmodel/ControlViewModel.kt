package com.example.cloromonitor.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.cloromonitor.R
import com.example.cloromonitor.data.model.ControlErrores
import com.example.cloromonitor.data.model.ControlUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ControlViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ControlUiState())
    val uiState: StateFlow<ControlUiState> = _uiState.asStateFlow()

    private val _errores = MutableStateFlow(ControlErrores())
    val errores: StateFlow<ControlErrores> = _errores.asStateFlow()

    fun onPuntoChange(valor: String) {
        _uiState.update { it.copy(punto = valor) }
    }

    fun onNivelCloroChange(valor: String) {
        _uiState.update { it.copy(nivelCloro = valor) }
    }

    fun onAccionCorrectivaChange(valor: Boolean) {
        _uiState.update { it.copy(accionCorrectiva = valor) }
    }

    fun onObservacionChange(valor: String) {
        _uiState.update { it.copy(observacion = valor) }
    }

    fun onUbicacionChange(latitud: Double?, longitud: Double?) {
        _uiState.update { it.copy(latitud = latitud, longitud = longitud) }
    }

    fun limpiarFormulario() {
        _uiState.value = ControlUiState()
        _errores.value = ControlErrores()
    }

    fun validarFormulario(): Boolean {
        val estado = _uiState.value
        val nivel = estado.nivelCloro.replace(',', '.').toDoubleOrNull()

        val errorPunto = if (estado.punto.isBlank()) R.string.error_seleccionar_punto else null

        val errorNivel = when {
            estado.nivelCloro.isBlank() -> R.string.error_nivel_vacio
            nivel == null -> R.string.error_numero_invalido
            nivel < 0.0 || nivel > 5.0 -> R.string.error_nivel_rango
            else -> null
        }

        val errorObservacion = if (estado.accionCorrectiva && estado.observacion.trim().length < 10) {
            R.string.error_observacion_corta
        } else null

        _errores.value = ControlErrores(
            punto = errorPunto,
            nivelCloro = errorNivel,
            observacion = errorObservacion
        )

        return errorPunto == null && errorNivel == null && errorObservacion == null
    }
}