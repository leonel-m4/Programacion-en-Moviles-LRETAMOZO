package com.tecsup.ayudacampus.ui.cliente

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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.tecsup.ayudacampus.model.EstadoTicket
import com.tecsup.ayudacampus.ui.components.AyudaButton
import com.tecsup.ayudacampus.ui.components.AyudaTextField
import com.tecsup.ayudacampus.ui.components.BurbujaComentario
import com.tecsup.ayudacampus.ui.components.EstadoChip
import com.tecsup.ayudacampus.ui.components.Estrellas
import com.tecsup.ayudacampus.ui.components.PrioridadChip
import com.tecsup.ayudacampus.ui.components.StepperTicket
import com.tecsup.ayudacampus.viewmodel.TicketDetalleViewModel

@Composable
fun DetalleTicketScreen(vm: TicketDetalleViewModel) {
    val detalle by vm.ticket.collectAsState()
    val d = detalle ?: return
    val t = d.ticket

    var texto by remember { mutableStateOf("") }
    var calificacion by remember { mutableIntStateOf(0) }
    var comentarioFinal by remember { mutableStateOf("") }

    val resuelto = t.estado == EstadoTicket.RESUELTO.name
    val cerrado = t.estado == EstadoTicket.CERRADO.name

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
                        Text("${d.categoria.nombre} · ${t.ubicacion}", style = MaterialTheme.typography.bodyMedium)
                        Text(t.descripcion, style = MaterialTheme.typography.bodyMedium)
                        StepperTicket(estado = t.estado)
                    }
                }

                if (resuelto) {
                    item {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF16A34A).copy(alpha = 0.15f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Text("Tu ticket fue resuelto", style = MaterialTheme.typography.titleMedium)
                                Text("Califica la atención para cerrarlo", style = MaterialTheme.typography.bodySmall)
                                Estrellas(valor = calificacion, onCambiar = { calificacion = it })
                                AyudaTextField(
                                    value = comentarioFinal,
                                    onValueChange = { comentarioFinal = it },
                                    label = "Comentario final (opcional)"
                                )
                                AyudaButton(
                                    text = "Confirmar y cerrar",
                                    enabled = calificacion > 0,
                                    onClick = { vm.cerrar(calificacion, comentarioFinal) }
                                )
                                OutlinedButton(
                                    onClick = { vm.reabrir() },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text("Reabrir ticket")
                                }
                            }
                        }
                    }
                }

                if (cerrado) {
                    item {
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text("Calificación recibida", style = MaterialTheme.typography.titleSmall)
                            Estrellas(valor = t.calificacion ?: 0)
                        }
                    }
                }

                item {
                    Text("Conversación", style = MaterialTheme.typography.titleMedium)
                }

                items(d.comentarios.sortedBy { it.fecha }, key = { it.id }) { c ->
                    BurbujaComentario(comentario = c, propio = c.autorId == d.cliente.id)
                }
            }

            if (!cerrado) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AyudaTextField(
                        value = texto,
                        onValueChange = { texto = it },
                        label = "Escribe un comentario",
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(
                        onClick = {
                            vm.comentar(texto)
                            texto = ""
                        },
                        enabled = texto.isNotBlank()
                    ) {
                        Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Enviar")
                    }
                }
            }
        }
    }
}