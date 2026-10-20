package com.tecsup.ayudacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.ayudacampus.data.local.entity.Usuario
import com.tecsup.ayudacampus.data.prefs.PreferenciasStore
import com.tecsup.ayudacampus.data.repository.AyudaRepository
import com.tecsup.ayudacampus.model.Rol
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SesionViewModel(
    private val repo: AyudaRepository,
    private val prefs: PreferenciasStore
) : ViewModel() {

    private val inicio = SharingStarted.WhileSubscribed(5000)

    private val _usuarioActual = MutableStateFlow<Usuario?>(null)
    val usuarioActual: StateFlow<Usuario?> = _usuarioActual.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    val modoOscuro: StateFlow<Boolean> = prefs.modoOscuro.stateIn(viewModelScope, inicio, false)
    val notificaciones: StateFlow<Boolean> = prefs.notificaciones.stateIn(viewModelScope, inicio, true)
    val recibirTickets: StateFlow<Boolean> = prefs.recibirTickets.stateIn(viewModelScope, inicio, true)
    val usuarioId: StateFlow<Int> = prefs.usuarioId.stateIn(viewModelScope, inicio, 0)

    fun login(correo: String, clave: String, rolSeleccionado: String) {
        if (correo.isBlank() || clave.isBlank()) {
            _error.value = "Completa correo y contraseña"
            return
        }
        viewModelScope.launch {
            val usuario = repo.login(correo.trim(), clave)
            when {
                usuario == null -> _error.value = "Correo o contraseña incorrectos"
                usuario.rol != rolSeleccionado -> _error.value = "Este usuario no pertenece al perfil elegido"
                else -> {
                    prefs.guardarSesion(usuario.rol, usuario.id)
                    _error.value = null
                    _usuarioActual.value = usuario
                }
            }
        }
    }

    fun limpiarError() {
        _error.value = null
    }

    fun guardarModoOscuro(b: Boolean) {
        viewModelScope.launch { prefs.guardarModoOscuro(b) }
    }

    fun guardarNotificaciones(b: Boolean) {
        viewModelScope.launch { prefs.guardarNotificaciones(b) }
    }

    fun guardarRecibirTickets(b: Boolean) {
        viewModelScope.launch { prefs.guardarRecibirTickets(b) }
    }

    fun cambiarPerfil(actual: Rol, onListo: (Rol) -> Unit) {
        viewModelScope.launch {
            val destino = if (actual == Rol.ADMIN) Rol.CLIENTE else Rol.ADMIN
            val usuario = if (destino == Rol.ADMIN) {
                repo.login("soporte@campus.test", "admin")
            } else {
                repo.login("juan@campus.test", "1234")
            }
            if (usuario != null) {
                prefs.guardarSesion(usuario.rol, usuario.id)
                onListo(destino)
            }
        }
    }

    fun cerrarSesion(onListo: () -> Unit) {
        viewModelScope.launch {
            prefs.cerrarSesion()
            _usuarioActual.value = null
            _error.value = null
            onListo()
        }
    }
}