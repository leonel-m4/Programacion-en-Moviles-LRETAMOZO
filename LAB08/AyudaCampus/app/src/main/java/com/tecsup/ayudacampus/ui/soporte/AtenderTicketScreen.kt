package com.tecsup.ayudacampus.ui.soporte

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tecsup.ayudacampus.model.EstadoTicket
import com.tecsup.ayudacampus.model.Prioridad
import com.tecsup.ayudacampus.model.Rol
import com.tecsup.ayudacampus.ui.components.AyudaButton
import com.tecsup.ayudacampus.ui.components.AyudaTextField
import com.tecsup.ayudacampus.ui.components.BurbujaComentario
import com.tecsup.ayudacampus.ui.components.EstadoChip
import com.tecsup.ayudacampus.ui.components.PrioridadChip
import com.tecsup.ayudacampus.viewmodel.AtenderViewModel

@Composable
fun AtenderTicketScreen(vm: AtenderViewModel, onGuardado: () -> Unit) {
    val detalle by vm.ticket.collectAsState()
    val estado by vm.estado.collectAsState()
    val prioridad by vm.prioridad.collectAsState()
    val d = detalle ?: return
    val t = d.ticket

    var texto by remember { mutableStateOf("") }
    val cerrado = t.estado == EstadoTicket.CERRADO.name

    val estados = listOf(
        "Abierto" to EstadoTicket.ABIERTO,
        "En proceso" to EstadoTicket.EN_PROCESO,
        "Resuelto" to EstadoTicket.RESUELTO,
        "Cerrado" to EstadoTicket.CERRADO
    )
    val prioridades = listOf(
        "Baja" to Prioridad.BAJA,
        "Media" to Prioridad.MEDIA,
        "Alta" to Prioridad.ALTA
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(Modifier.fillMaxSize().imePadding()) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Column(
                        modifier = Modifier.padding(top = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("#${t.id} · ${t.titulo}", style = MaterialTheme.typography.headlineSmall)
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            EstadoChip(t.estado)
                            PrioridadChip(t.prioridad)
                        }
                        Text("Cliente: ${d.cliente.nombre} (${d.cliente.correo})", style = MaterialTheme.typography.bodyMedium)
                        Text("${d.categoria.nombre} · ${t.ubicacion}", style = MaterialTheme.typography.bodyMedium)
                        Text(t.descripcion, style = MaterialTheme.typography.bodyMedium)
                    }
                }

                item {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("Estado", style = MaterialTheme.typography.titleSmall)
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            estados.forEach { (nombre, valor) ->
                                FilterChip(
                                    selected = estado == valor,
                                    enabled = !cerrado && valor != EstadoTicket.CERRADO,
                                    onClick = { vm.cambiarEstado(valor) },
                                    label = { Text(nombre) }
                                )
                            }
                        }
                        Text("Prioridad", style = MaterialTheme.typography.titleSmall)
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            prioridades.forEach { (nombre, valor) ->
                                FilterChip(
                                    selected = prioridad == valor,
                                    onClick = { vm.cambiarPrioridad(valor) },
                                    label = { Text(nombre) }
                                )
                            }
                        }
                    }
                }

                item { Text("Conversación", style = MaterialTheme.typography.titleMedium) }

                items(d.comentarios.sortedBy { it.fecha }, key = { it.id }) { c ->
                    BurbujaComentario(comentario = c, propio = c.autorRol == Rol.ADMIN.name)
                }
            }

            if (!cerrado) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AyudaTextField(
                        value = texto,
                        onValueChange = { texto = it },
                        label = "Responder al cliente",
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(
                        onClick = {
                            vm.responder(texto)
                            texto = ""
                        },
                        enabled = texto.isNotBlank()
                    ) {
                        Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Enviar")
                    }
                }
            }

            AyudaButton(
                text = "Guardar cambios",
                onClick = { vm.guardar(onGuardado) },
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
            )
        }
    }
}