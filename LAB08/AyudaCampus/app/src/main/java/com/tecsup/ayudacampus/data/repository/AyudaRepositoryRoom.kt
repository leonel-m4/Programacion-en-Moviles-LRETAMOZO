package com.tecsup.ayudacampus.data.repository

import com.tecsup.ayudacampus.data.local.AyudaDatabase
import com.tecsup.ayudacampus.data.local.entity.Categoria
import com.tecsup.ayudacampus.data.local.entity.Comentario
import com.tecsup.ayudacampus.data.local.entity.Ticket
import com.tecsup.ayudacampus.data.local.entity.Usuario
import com.tecsup.ayudacampus.data.local.relation.TicketConDetalle
import com.tecsup.ayudacampus.model.ConteoCategoria
import com.tecsup.ayudacampus.model.EstadoTicket
import com.tecsup.ayudacampus.model.Prioridad
import kotlinx.coroutines.flow.Flow

class AyudaRepositoryRoom(private val db: AyudaDatabase) : AyudaRepository {

    override suspend fun login(correo: String, clave: String): Usuario? =
        db.usuarioDao().login(correo, clave)

    override fun observarCategorias(): Flow<List<Categoria>> =
        db.categoriaDao().observarTodas()

    override fun observarCategoriasActivas(): Flow<List<Categoria>> =
        db.categoriaDao().observarActivas()

    override suspend fun guardarCategoria(c: Categoria) {
        db.categoriaDao().insertar(c)
    }

    override suspend fun eliminarCategoria(c: Categoria) {
        db.categoriaDao().eliminar(c)
    }

    override suspend fun cambiarActiva(id: Int, activa: Boolean) {
        db.categoriaDao().cambiarActiva(id, activa)
    }

    override suspend fun crearTicket(
        clienteId: Int,
        categoriaId: Int,
        titulo: String,
        descripcion: String,
        ubicacion: String,
        prioridad: Prioridad
    ): Long = db.ticketDao().insertarTicket(
        Ticket(
            clienteId = clienteId,
            categoriaId = categoriaId,
            titulo = titulo,
            descripcion = descripcion,
            ubicacion = ubicacion,
            prioridad = prioridad.name,
            estado = EstadoTicket.ABIERTO.name,
            fecha = System.currentTimeMillis()
        )
    )

    override fun observarTicketsCliente(clienteId: Int): Flow<List<TicketConDetalle>> =
        db.ticketDao().observarPorCliente(clienteId)

    override fun observarTodosLosTickets(): Flow<List<TicketConDetalle>> =
        db.ticketDao().observarTodos()

    override fun observarTicket(id: Int): Flow<TicketConDetalle?> =
        db.ticketDao().observarUno(id)

    override suspend fun cambiarEstado(ticketId: Int, estado: EstadoTicket) {
        db.ticketDao().actualizarEstado(ticketId, estado.name)
    }

    override suspend fun cambiarPrioridad(ticketId: Int, prioridad: Prioridad) {
        db.ticketDao().actualizarPrioridad(ticketId, prioridad.name)
    }

    override suspend fun cerrarConCalificacion(ticketId: Int, calificacion: Int) {
        db.ticketDao().cerrar(ticketId, calificacion, System.currentTimeMillis())
    }

    override suspend fun reabrir(ticketId: Int) {
        db.ticketDao().reabrir(ticketId)
    }

    override suspend fun agregarComentario(ticketId: Int, autor: Usuario, texto: String) {
        db.ticketDao().insertarComentario(
            Comentario(
                ticketId = ticketId,
                autorId = autor.id,
                autorNombre = autor.nombre,
                autorRol = autor.rol,
                texto = texto
            )
        )
    }

    override fun observarCantidadPorEstado(estado: EstadoTicket): Flow<Int> =
        db.ticketDao().contarPorEstado(estado.name)

    override fun observarCantidadPorCategoria(): Flow<List<ConteoCategoria>> =
        db.ticketDao().contarPorCategoria()

    override fun observarAltasSinAtender(limite: Long): Flow<Int> =
        db.ticketDao().contarAltasSinAtender(limite)
}