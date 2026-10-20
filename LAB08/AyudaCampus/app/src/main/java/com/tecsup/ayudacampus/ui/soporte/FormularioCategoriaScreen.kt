package com.tecsup.ayudacampus.ui.soporte

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
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
import com.tecsup.ayudacampus.data.local.entity.Categoria
import com.tecsup.ayudacampus.ui.components.AyudaButton
import com.tecsup.ayudacampus.ui.components.AyudaTextField
import com.tecsup.ayudacampus.ui.components.iconoCategoria
import com.tecsup.ayudacampus.viewmodel.CategoriasViewModel

@Composable
fun FormularioCategoriaScreen(
    vm: CategoriasViewModel,
    categoriaId: Int?,
    onListo: () -> Unit
) {
    val categorias by vm.categorias.collectAsState()
    val existente = categoriaId?.let { id -> categorias.firstOrNull { it.id == id } }

    var nombre by remember(existente?.id) { mutableStateOf(existente?.nombre ?: "") }
    var icono by remember(existente?.id) { mutableStateOf(existente?.icono ?: "tag") }
    var activa by remember(existente?.id) { mutableStateOf(existente?.activa ?: true) }
    var error by remember { mutableStateOf<String?>(null) }

    val iconos = listOf("pc", "code", "wifi", "key", "printer", "tag")

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                if (categoriaId == null) "Nueva categoría" else "Editar categoría",
                style = MaterialTheme.typography.headlineSmall
            )

            AyudaTextField(
                value = nombre,
                onValueChange = {
                    nombre = it
                    error = null
                },
                label = "Nombre",
                error = error
            )

            Text("Icono", style = MaterialTheme.typography.titleSmall)
            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                iconos.forEach { i ->
                    FilterChip(
                        selected = icono == i,
                        onClick = { icono = i },
                        label = { Text(i) },
                        leadingIcon = {
                            Icon(iconoCategoria(i), contentDescription = null, modifier = Modifier.size(18.dp))
                        }
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Activa", style = MaterialTheme.typography.titleSmall)
                Switch(checked = activa, onCheckedChange = { activa = it })
            }

            Text("Vista previa", style = MaterialTheme.typography.titleSmall)
            Card(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(
                        iconoCategoria(icono),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(28.dp)
                    )
                    Column {
                        Text(
                            nombre.ifBlank { "Nombre de la categoría" },
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(if (activa) "Activa" else "Inactiva", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }

            AyudaButton(
                text = "Guardar",
                onClick = {
                    if (nombre.isBlank()) {
                        error = "El nombre es obligatorio"
                    } else {
                        vm.guardar(
                            Categoria(
                                id = existente?.id ?: 0,
                                nombre = nombre.trim(),
                                icono = icono,
                                activa = activa
                            )
                        )
                        onListo()
                    }
                }
            )
        }
    }
}