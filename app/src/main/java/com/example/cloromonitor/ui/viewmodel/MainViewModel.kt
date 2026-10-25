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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import com.example.cloromonitor.data.model.EstadoCloro
import com.example.cloromonitor.util.NotificacionHelper

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
    fun registrarControl(
        punto: String,
        nivel: String,
        accionCorrectiva: Boolean,
        observacion: String,
        latitud: Double? = null,
        longitud: Double? = null
    ) {
        val base = puntos.value.firstOrNull { "${it.granja} - ${it.punto}" == punto } ?: return
        val granja = base.granja
        val nombrePunto = base.punto
        val nivelCloro = nivel.replace(',', '.').toDoubleOrNull() ?: return
        val fecha = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault()).format(Date())
        viewModelScope.launch {
            val nuevo = PuntoMedicion(
                granja = granja,
                punto = nombrePunto,
                nivelCloro = nivelCloro,
                actualizado = fecha,
                accionCorrectiva = accionCorrectiva,
                observacion = observacion.trim(),
                latitud = latitud,
                longitud = longitud
            )
            repositorio.registrar(nuevo)
            if (nuevo.estado != EstadoCloro.NORMAL) {
                NotificacionHelper.mostrarAlerta(getApplication<Application>(), nuevo)
            }
        }}
}