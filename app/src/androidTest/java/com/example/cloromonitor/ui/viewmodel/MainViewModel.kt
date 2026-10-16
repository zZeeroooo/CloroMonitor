package com.example.cloromonitor.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.cloromonitor.data.model.PuntoMedicion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {

    private val _puntos = MutableStateFlow(
        listOf(
            PuntoMedicion(1, "Granja Norte", "Galpón 1", 0.8, "16-10-2026 09:30"),
            PuntoMedicion(2, "Granja Norte", "Galpón 2", 0.4, "16-10-2026 09:32"),
            PuntoMedicion(3, "Granja Sur", "Estanque", 2.4, "16-10-2026 09:35"),
            PuntoMedicion(4, "Granja Sur", "Galpón 3", 1.1, "16-10-2026 09:40")
        )
    )
    val puntos: StateFlow<List<PuntoMedicion>> = _puntos.asStateFlow()
}