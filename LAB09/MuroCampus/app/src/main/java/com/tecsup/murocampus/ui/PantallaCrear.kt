package com.tecsup.murocampus.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
fun PantallaCrear(vm: PostsViewModel) {
    val estado by vm.creado.collectAsStateWithLifecycle()

    var titulo by rememberSaveable { mutableStateOf("") }
    var contenido by rememberSaveable { mutableStateOf("") }

    // Si se publicó con éxito, se limpia el formulario.
    // Si hubo error, lo escrito se conserva para poder reintentar.
    LaunchedEffect(estado) {
        if (estado is UiState.Exito) {
            titulo = ""
            contenido = ""
        }
    }

    val enviando = estado == UiState.Cargando

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = titulo,
            onValueChange = { titulo = it },
            label = { Text("Título") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = contenido,
            onValueChange = { contenido = it },
            label = { Text("Contenido") },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        )
        Button(
            onClick = { vm.publicar(titulo, contenido) },
            enabled = titulo.isNotBlank() && contenido.isNotBlank() && !enviando
        ) {
            Text("Publicar")
        }

        when (val e = estado) {
            null -> Unit
            UiState.Cargando -> CircularProgressIndicator(Modifier.size(32.dp))
            is UiState.Exito -> Text(
                text = "Publicado con id ${e.datos.id}",
                color = TextoExito,
                fontWeight = FontWeight.Bold,
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
