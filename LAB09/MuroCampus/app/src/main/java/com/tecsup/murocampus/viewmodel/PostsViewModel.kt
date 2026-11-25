package com.tecsup.murocampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.murocampus.data.PostRepository
import com.tecsup.murocampus.model.Comentario
import com.tecsup.murocampus.model.NuevoPost
import com.tecsup.murocampus.model.Post
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class DetalleUi(val post: Post, val comentarios: List<Comentario>)

class PostsViewModel(private val repo: PostRepository) : ViewModel() {

    private val _lista = MutableStateFlow<UiState<List<Post>>>(UiState.Cargando)
    val lista = _lista.asStateFlow()

    // Texto escrito en el buscador.
    private val _busqueda = MutableStateFlow("")
    val busqueda = _busqueda.asStateFlow()

    // Las publicaciones ya descargadas cuyo título contiene el texto buscado.
    val visibles: StateFlow<UiState<List<Post>>> = combine(_lista, _busqueda) { estado, texto ->
        if (estado is UiState.Exito && texto.isNotBlank()) {
            UiState.Exito(estado.datos.filter { it.title.contains(texto.trim(), ignoreCase = true) })
        } else {
            estado
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), UiState.Cargando)

    private val _detalle = MutableStateFlow<UiState<DetalleUi>>(UiState.Cargando)
    val detalle = _detalle.asStateFlow()

    // null significa Todos.
    private val _usuarioFiltro = MutableStateFlow<Int?>(null)
    val usuarioFiltro = _usuarioFiltro.asStateFlow()

    // null = aún no se envió.
    private val _creado = MutableStateFlow<UiState<Post>?>(null)
    val creado = _creado.asStateFlow()

    // null = no se pidió borrar; Exito lleva el id de la publicación borrada.
    private val _borrado = MutableStateFlow<UiState<Int>?>(null)
    val borrado = _borrado.asStateFlow()

    // null = aún no se guardó la edición; Exito lleva la respuesta del servidor.
    private val _editado = MutableStateFlow<UiState<Post>?>(null)
    val editado = _editado.asStateFlow()

    private var trabajoLista: Job? = null
    private var trabajoDetalle: Job? = null

    init { cargar() }

    fun cargar() {
        trabajoLista?.cancel()
        trabajoLista = viewModelScope.launch {
            _lista.value = UiState.Cargando
            val userId = _usuarioFiltro.value
            val resultado = if (userId == null) repo.listar() else repo.porUsuario(userId)
            resultado
                .onSuccess { _lista.value = UiState.Exito(it) }
                .onFailure { _lista.value = UiState.Error(mensajeDe(it)) }
        }
    }

    fun buscar(texto: String) {
        _busqueda.value = texto
    }

    fun filtrar(userId: Int?) {
        _usuarioFiltro.value = userId
        cargar()
    }

    fun abrir(id: Int) {
        trabajoDetalle?.cancel()
        _borrado.value = null
        trabajoDetalle = viewModelScope.launch {
            _detalle.value = UiState.Cargando
            // Dos llamadas a la vez: el post y sus comentarios.
            val (rPost, rComentarios) = coroutineScope {
                val post = async { repo.detalle(id) }
                val comentarios = async { repo.comentarios(id) }
                post.await() to comentarios.await()
            }
            val post = rPost.getOrElse {
                _detalle.value = UiState.Error(mensajeDe(it))
                return@launch
            }
            val comentarios = rComentarios.getOrElse {
                _detalle.value = UiState.Error(mensajeDe(it))
                return@launch
            }
            _detalle.value = UiState.Exito(DetalleUi(post, comentarios))
        }
    }

    fun publicar(titulo: String, texto: String) {
        viewModelScope.launch {
            _creado.value = UiState.Cargando
            repo.crear(NuevoPost(userId = 1, title = titulo, body = texto))
                .onSuccess { _creado.value = UiState.Exito(it) }
                .onFailure { _creado.value = UiState.Error(mensajeDe(it)) }
        }
    }

    fun borrar(id: Int) {
        viewModelScope.launch {
            _borrado.value = UiState.Cargando
            repo.borrar(id)
                .onSuccess {
                    // JSONPlaceholder no borra de verdad: se quita de la lista ya descargada.
                    val actual = _lista.value
                    if (actual is UiState.Exito) {
                        _lista.value = UiState.Exito(actual.datos.filterNot { it.id == id })
                    }
                    _borrado.value = UiState.Exito(id)
                }
                .onFailure { _borrado.value = UiState.Error(mensajeDe(it)) }
        }
    }

    fun editarTitulo(id: Int, titulo: String) {
        viewModelScope.launch {
            _editado.value = UiState.Cargando
            repo.editarTitulo(id, titulo)
                .onSuccess { respuesta ->
                    _editado.value = UiState.Exito(respuesta)
                    // Se actualiza el título en la lista y en el detalle ya descargados.
                    val actual = _lista.value
                    if (actual is UiState.Exito) {
                        _lista.value = UiState.Exito(
                            actual.datos.map { if (it.id == id) it.copy(title = respuesta.title) else it }
                        )
                    }
                    val detalleActual = _detalle.value
                    if (detalleActual is UiState.Exito && detalleActual.datos.post.id == id) {
                        val post = detalleActual.datos.post.copy(title = respuesta.title)
                        _detalle.value = UiState.Exito(detalleActual.datos.copy(post = post))
                    }
                }
                .onFailure { _editado.value = UiState.Error(mensajeDe(it)) }
        }
    }

    // Al abrir la pantalla de edición no se muestra el resultado anterior.
    fun limpiarEdicion() {
        _editado.value = null
    }

    // La pantalla avisa que ya mostró el resultado del borrado.
    fun limpiarBorrado() {
        _borrado.value = null
    }
}
