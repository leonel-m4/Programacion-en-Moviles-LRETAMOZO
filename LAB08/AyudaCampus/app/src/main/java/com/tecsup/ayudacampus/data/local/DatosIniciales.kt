package com.tecsup.ayudacampus.data.local

import com.tecsup.ayudacampus.data.local.entity.Categoria
import com.tecsup.ayudacampus.data.local.entity.Comentario
import com.tecsup.ayudacampus.data.local.entity.Ticket
import com.tecsup.ayudacampus.data.local.entity.Usuario

object DatosIniciales {

    private const val HORA = 3600L * 1000L

    suspend fun sembrar(db: AyudaDatabase) {
        if (db.usuarioDao().contar() != 0) return

        val ahora = System.currentTimeMillis()

        db.categoriaDao().insertarTodas(
            listOf(
                Categoria(id = 1, nombre = "Hardware", icono = "pc"),
                Categoria(id = 2, nombre = "Software", icono = "code"),
                Categoria(id = 3, nombre = "Red / Wi-Fi", icono = "wifi"),
                Categoria(id = 4, nombre = "Cuenta", icono = "key"),
                Categoria(id = 5, nombre = "Impresión", icono = "printer")
            )
        )

        db.usuarioDao().insertarTodos(
            listOf(
                Usuario(id = 1, nombre = "Juan Pérez", correo = "juan@campus.test", clave = "1234", rol = "CLIENTE"),
                Usuario(id = 2, nombre = "Soporte TI", correo = "soporte@campus.test", clave = "admin", rol = "ADMIN")
            )
        )

        val tickets = listOf(
            Ticket(
                id = 1, clienteId = 1, categoriaId = 3,
                titulo = "WiFi no conecta en biblioteca",
                descripcion = "La red se cae cada 5 minutos.",
                ubicacion = "Biblioteca", prioridad = "ALTA", estado = "ABIERTO",
                fecha = ahora - 3 * HORA
            ),
            Ticket(
                id = 2, clienteId = 1, categoriaId = 4,
                titulo = "No puedo entrar al aula virtual",
                descripcion = "Sale error 403 al iniciar sesión.",
                ubicacion = "Casa", prioridad = "ALTA", estado = "EN_PROCESO",
                fecha = ahora - 5 * HORA
            ),
            Ticket(
                id = 3, clienteId = 1, categoriaId = 1,
                titulo = "Proyector sin imagen",
                descripcion = "El HDMI no es detectado por el proyector.",
                ubicacion = "Aula B-204", prioridad = "MEDIA", estado = "ABIERTO",
                fecha = ahora - 26 * HORA
            ),
            Ticket(
                id = 4, clienteId = 1, categoriaId = 2,
                titulo = "Instalar Android Studio",
                descripcion = "Falta instalarlo en 10 equipos.",
                ubicacion = "Laboratorio 3", prioridad = "BAJA", estado = "RESUELTO",
                fecha = ahora - 72 * HORA
            ),
            Ticket(
                id = 5, clienteId = 1, categoriaId = 5,
                titulo = "Impresora no imprime",
                descripcion = "Atasco de papel constante.",
                ubicacion = "Secretaría", prioridad = "MEDIA", estado = "CERRADO",
                fecha = ahora - 120 * HORA,
                fechaCierre = ahora - 100 * HORA, calificacion = 5
            ),
            Ticket(
                id = 6, clienteId = 1, categoriaId = 2,
                titulo = "Licencia de Office vencida",
                descripcion = "Pide activar el producto.",
                ubicacion = "Sala de profesores", prioridad = "BAJA", estado = "EN_PROCESO",
                fecha = ahora - 200 * HORA
            )
        )
        tickets.forEach { db.ticketDao().insertarTicket(it) }

        val comentarios = listOf(
            Comentario(
                ticketId = 1, autorId = 2, autorNombre = "Soporte TI", autorRol = "ADMIN",
                texto = "Revisando el router del segundo piso.", fecha = ahora - 2 * HORA
            ),
            Comentario(
                ticketId = 2, autorId = 2, autorNombre = "Soporte TI", autorRol = "ADMIN",
                texto = "Reiniciamos tu perfil, prueba de nuevo.", fecha = ahora - 4 * HORA
            ),
            Comentario(
                ticketId = 2, autorId = 1, autorNombre = "Juan Pérez", autorRol = "CLIENTE",
                texto = "Sigue igual, adjunto captura.", fecha = ahora - 3 * HORA
            ),
            Comentario(
                ticketId = 4, autorId = 2, autorNombre = "Soporte TI", autorRol = "ADMIN",
                texto = "Instalado en los 10 equipos.", fecha = ahora - 70 * HORA
            )
        )
        comentarios.forEach { db.ticketDao().insertarComentario(it) }
    }
}