package com.example.cloromonitor.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.cloromonitor.data.local.AppDatabase
import com.example.cloromonitor.data.model.PuntoMedicion
import com.example.cloromonitor.data.repository.PuntoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repositorio = PuntoRepository(
        AppDatabase.getInstance(application).puntoDao()
    )

    // Todas las mediciones guardadas (historial)
    val historial: StateFlow<List<PuntoMedicion>> = repositorio.puntos
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Última medición de cada punto
    val puntos: StateFlow<List<PuntoMedicion>> = historial
        .map { lista -> lista.distinctBy { it.granja to it.punto } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        viewModelScope.launch {
            repositorio.cargarDatosInicialesSiHaceFalta()
        }
    }
}