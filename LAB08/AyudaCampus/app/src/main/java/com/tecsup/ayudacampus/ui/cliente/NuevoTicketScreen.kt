package com.tecsup.ayudacampus.ui.cliente

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tecsup.ayudacampus.model.Prioridad
import com.tecsup.ayudacampus.ui.components.AyudaButton
import com.tecsup.ayudacampus.ui.components.AyudaTextField
import com.tecsup.ayudacampus.ui.components.iconoCategoria
import com.tecsup.ayudacampus.viewmodel.NuevoTicketViewModel

@Composable
fun NuevoTicketScreen(
    vm: NuevoTicketViewModel,
    onCreado: () -> Unit,
    recibirTickets: Boolean = true
) {
    val categorias by vm.categorias.collectAsState()
    val categoriaId by vm.categoriaId.collectAsState()
    val titulo by vm.titulo.collectAsState()
    val ubicacion by vm.ubicacion.collectAsState()
    val prioridad by vm.prioridad.collectAsState()
    val descripcion by vm.descripcion.collectAsState()
    val errores by vm.errores.collectAsState()

    val prioridades = listOf(
        "Baja" to Prioridad.BAJA,
        "Media" to Prioridad.MEDIA,
        "Alta" to Prioridad.ALTA
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                "Nuevo Ticket de Soporte",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            if (!recibirTickets) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        "Mesa de ayuda cerrada temporalmente",
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Card(
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text("Categoría", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                    Row(
                        modifier = Modifier.horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        categorias.forEach { c ->
                            FilterChip(
                                selected = categoriaId == c.id,
                                onClick = { vm.elegirCategoria(c.id) },
                                label = { Text(c.nombre) },
                                leadingIcon = {
                                    Icon(
                                        iconoCategoria(c.icono),
                                        contentDescription = null,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            )
                        }
                    }
                    errores.categoria?.let {
                        Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
                    }

                    AyudaTextField(
                        value = titulo,
                        onValueChange = vm::cambiarTitulo,
                        label = "Título del problema",
                        error = errores.titulo
                    )

                    AyudaTextField(
                        value = ubicacion,
                        onValueChange = vm::cambiarUbicacion,
                        label = "Ubicación (Aula / Lab / Pabellón)"
                    )

                    Text("Prioridad", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        prioridades.forEach { (nombre, valor) ->
                            FilterChip(
                                selected = prioridad == valor,
                                onClick = { vm.cambiarPrioridad(valor) },
                                label = { Text(nombre) }
                            )
                        }
                    }

                    AyudaTextField(
                        value = descripcion,
                        onValueChange = vm::cambiarDescripcion,
                        label = "Descripción detallada del inconveniente",
                        error = errores.descripcion,
                        singleLine = false
                    )

                    AyudaButton(
                        text = "Enviar Solicitud",
                        enabled = recibirTickets,
                        onClick = { vm.enviar(onCreado) }
                    )
                }
            }
        }
    }
}
