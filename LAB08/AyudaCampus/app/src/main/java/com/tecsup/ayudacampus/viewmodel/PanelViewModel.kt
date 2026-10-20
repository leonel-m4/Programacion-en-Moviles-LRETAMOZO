package com.tecsup.ayudacampus.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.ayudacampus.data.repository.AyudaRepository
import com.tecsup.ayudacampus.model.EstadoTicket
import com.tecsup.ayudacampus.ui.components.colorEstado
import com.tecsup.ayudacampus.ui.components.graficos.DatoGrafico
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class PanelViewModel(repo: AyudaRepository) : ViewModel() {

    private val inicio = SharingStarted.WhileSubscribed(5000)

    private val flowAbiertos = repo.observarCantidadPorEstado(EstadoTicket.ABIERTO)
    private val flowEnProceso = repo.observarCantidadPorEstado(EstadoTicket.EN_PROCESO)
    private val flowResueltos = repo.observarCantidadPorEstado(EstadoTicket.RESUELTO)
    private val flowCerrados = repo.observarCantidadPorEstado(EstadoTicket.CERRADO)

    val abiertos: StateFlow<Int> = flowAbiertos.stateIn(viewModelScope, inicio, 0)
    val enProceso: StateFlow<Int> = flowEnProceso.stateIn(viewModelScope, inicio, 0)
    val resueltos: StateFlow<Int> = flowResueltos.stateIn(viewModelScope, inicio, 0)

    val porEstado: StateFlow<List<DatoGrafico>> =
        combine(flowAbiertos, flowEnProceso, flowResueltos, flowCerrados) { a, e, r, c ->
            listOf(
                DatoGrafico("Abierto", a.toFloat(), colorEstado(EstadoTicket.ABIERTO.name)),
                DatoGrafico("En proceso", e.toFloat(), colorEstado(EstadoTicket.EN_PROCESO.name)),
                DatoGrafico("Resuelto", r.toFloat(), colorEstado(EstadoTicket.RESUELTO.name)),
                DatoGrafico("Cerrado", c.toFloat(), colorEstado(EstadoTicket.CERRADO.name))
            )
        }.stateIn(viewModelScope, inicio, emptyList())

    private val paleta = listOf(
        Color(0xFF0891B2),
        Color(0xFF7C3AED),
        Color(0xFFD97706),
        Color(0xFF16A34A),
        Color(0xFFDC2626),
        Color(0xFF2563EB)
    )

    val porCategoria: StateFlow<List<DatoGrafico>> = repo.observarCantidadPorCategoria()
        .map { lista ->
            lista.mapIndexed { i, c ->
                DatoGrafico(c.categoria, c.total.toFloat(), paleta[i % paleta.size])
            }
        }
        .stateIn(viewModelScope, inicio, emptyList())

    val altasSinAtender: StateFlow<Int> =
        repo.observarAltasSinAtender(System.currentTimeMillis() - 3600L * 1000L)
            .stateIn(viewModelScope, inicio, 0)
}