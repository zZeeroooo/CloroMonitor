package com.example.cloromonitor.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.cloromonitor.data.model.NuevoPuntoErrores
import com.example.cloromonitor.data.model.NuevoPuntoUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NuevoPuntoViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(NuevoPuntoUiState())
    val uiState: StateFlow<NuevoPuntoUiState> = _uiState.asStateFlow()

    private val _errores = MutableStateFlow(NuevoPuntoErrores())
    val errores: StateFlow<NuevoPuntoErrores> = _errores.asStateFlow()

    fun onGranjaChange(valor: String) {
        _uiState.update { it.copy(granja = valor) }
    }

    fun onPuntoChange(valor: String) {
        _uiState.update { it.copy(punto = valor) }
    }

    fun onNivelCloroChange(valor: String) {
        _uiState.update { it.copy(nivelCloro = valor) }
    }

    fun limpiarFormulario() {
        _uiState.value = NuevoPuntoUiState()
        _errores.value = NuevoPuntoErrores()
    }

    // existentes: nombres con formato "Granja - Punto" que ya están guardados
    fun validarFormulario(existentes: List<String>): Boolean {
        val estado = _uiState.value
        val granja = estado.granja.trim()
        val punto = estado.punto.trim()
        val nivel = estado.nivelCloro.replace(',', '.').toDoubleOrNull()

        val errorGranja = when {
            granja.isEmpty() -> "Ingresa el nombre de la granja"
            granja.length < 3 -> "Mínimo 3 caracteres"
            else -> null
        }

        val errorPunto = when {
            punto.isEmpty() -> "Ingresa el nombre del punto"
            punto.length < 3 -> "Mínimo 3 caracteres"
            existentes.any { it.equals("$granja - $punto", ignoreCase = true) } ->
                "Este punto ya existe en esa granja"
            else -> null
        }

        val errorNivel = when {
            estado.nivelCloro.isBlank() -> "Ingresa el nivel inicial de cloro"
            nivel == null -> "Ingresa un número válido"
            nivel < 0.0 || nivel > 5.0 -> "El nivel debe estar entre 0 y 5 ppm"
            else -> null
        }

        _errores.value = NuevoPuntoErrores(
            granja = errorGranja,
            punto = errorPunto,
            nivelCloro = errorNivel
        )

        return errorGranja == null && errorPunto == null && errorNivel == null
    }
}