package com.example.cloromonitor.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.cloromonitor.data.model.PuntoMedicion
import kotlinx.coroutines.flow.Flow

@Dao
interface PuntoDao {

    @Query("SELECT * FROM puntos ORDER BY id DESC")
    fun observarPuntos(): Flow<List<PuntoMedicion>>

    @Query("SELECT COUNT(*) FROM puntos")
    suspend fun contar(): Int

    @Insert
    suspend fun insertar(punto: PuntoMedicion)

    @Insert
    suspend fun insertarTodos(puntos: List<PuntoMedicion>)
}