package com.example.cloromonitor.ui.viewmodel

import com.example.cloromonitor.R
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ControlViewModelTest {

    private lateinit var viewModel: ControlViewModel

    @Before
    fun setUp() {
        viewModel = ControlViewModel()
    }

    private fun llenarFormularioValido() {
        viewModel.onPuntoChange("Granja Norte - Galpón 1")
        viewModel.onNivelCloroChange("1.0")
    }

    @Test
    fun formularioVacio_noEsValido_yMuestraErroresPorCampo() {
        assertFalse(viewModel.validarFormulario())

        val errores = viewModel.errores.value
        assertEquals(R.string.error_seleccionar_punto, errores.punto)
        assertEquals(R.string.error_nivel_vacio, errores.nivelCloro)
        assertNull(errores.observacion)
    }

    @Test
    fun nivelNoNumerico_muestraError() {
        llenarFormularioValido()
        viewModel.onNivelCloroChange("abc")

        assertFalse(viewModel.validarFormulario())
        assertEquals(R.string.error_numero_invalido, viewModel.errores.value.nivelCloro)
    }

    @Test
    fun nivelFueraDeRango_muestraError() {
        llenarFormularioValido()
        viewModel.onNivelCloroChange("5.1")

        assertFalse(viewModel.validarFormulario())
        assertEquals(R.string.error_nivel_rango, viewModel.errores.value.nivelCloro)
    }

    @Test
    fun nivelConComaDecimal_esValido() {
        llenarFormularioValido()
        viewModel.onNivelCloroChange("1,2")

        assertTrue(viewModel.validarFormulario())
    }

    @Test
    fun accionCorrectivaConObservacionCorta_muestraError() {
        llenarFormularioValido()
        viewModel.onAccionCorrectivaChange(true)
        viewModel.onObservacionChange("corto")

        assertFalse(viewModel.validarFormulario())
        assertEquals(R.string.error_observacion_corta, viewModel.errores.value.observacion)
    }

    @Test
    fun accionCorrectivaConObservacionValida_esValido() {
        llenarFormularioValido()
        viewModel.onAccionCorrectivaChange(true)
        viewModel.onObservacionChange("Se agregó cloro al estanque")

        assertTrue(viewModel.validarFormulario())
    }

    @Test
    fun limpiarFormulario_reiniciaEstadoYErrores() {
        viewModel.validarFormulario()
        viewModel.limpiarFormulario()

        assertEquals("", viewModel.uiState.value.punto)
        assertNull(viewModel.errores.value.punto)
    }
}