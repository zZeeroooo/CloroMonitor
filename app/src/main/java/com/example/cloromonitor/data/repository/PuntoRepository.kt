package com.example.cloromonitor.data.repository

import com.example.cloromonitor.data.local.PuntoDao
import com.example.cloromonitor.data.model.PuntoMedicion
import kotlinx.coroutines.flow.Flow

class PuntoRepository(private val dao: PuntoDao) {

    val puntos: Flow<List<PuntoMedicion>> = dao.observarPuntos()

    suspend fun registrar(punto: PuntoMedicion) = dao.insertar(punto)

    suspend fun cargarDatosInicialesSiHaceFalta() {
        if (dao.contar() == 0) {
            dao.insertarTodos(
                listOf(
                    PuntoMedicion(granja = "Granja Norte", punto = "Galpón 1", nivelCloro = 0.8, actualizado = "16-10-2026 09:30"),
                    PuntoMedicion(granja = "Granja Norte", punto = "Galpón 2", nivelCloro = 0.4, actualizado = "16-10-2026 09:32"),
                    PuntoMedicion(granja = "Granja Sur", punto = "Estanque", nivelCloro = 2.4, actualizado = "16-10-2026 09:35"),
                    PuntoMedicion(granja = "Granja Sur", punto = "Galpón 3", nivelCloro = 1.1, actualizado = "16-10-2026 09:40")
                )
            )
        }
    }
}