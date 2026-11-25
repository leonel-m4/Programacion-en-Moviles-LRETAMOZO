package com.tecsup.murocampus.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tecsup.murocampus.ui.theme.TextoExito
import com.tecsup.murocampus.ui.theme.VerdeExito
import com.tecsup.murocampus.viewmodel.PostsViewModel
import com.tecsup.murocampus.viewmodel.UiState

@Composable
fun PantallaEditar(vm: PostsViewModel, id: Int, tituloActual: String) {
    val estado by vm.editado.collectAsStateWithLifecycle()

    var titulo by rememberSaveable(id) { mutableStateOf(tituloActual) }
    val enviando = estado == UiState.Cargando

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Editar la publicación $id",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = titulo,
            onValueChange = { titulo = it },
            label = { Text("Título") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = { vm.editarTitulo(id, titulo.trim()) },
            enabled = titulo.isNotBlank() && !enviando
        ) {
            Text("Guardar")
        }

        when (val e = estado) {
            null -> Unit
            UiState.Cargando -> CircularProgressIndicator(Modifier.size(32.dp))
            is UiState.Exito -> Text(
                text = "Respuesta del servidor (PATCH)\nid ${e.datos.id} · usuario ${e.datos.userId}\n${e.datos.title}",
                color = TextoExito,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(VerdeExito, RoundedCornerShape(8.dp))
                    .border(BorderStroke(1.dp, TextoExito), RoundedCornerShape(8.dp))
                    .padding(12.dp)
            )
            is UiState.Error -> Text(
                text = e.mensaje,
                color = MaterialTheme.colorScheme.onErrorContainer,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.errorContainer, RoundedCornerShape(8.dp))
                    .padding(12.dp)
            )
        }
    }
}
