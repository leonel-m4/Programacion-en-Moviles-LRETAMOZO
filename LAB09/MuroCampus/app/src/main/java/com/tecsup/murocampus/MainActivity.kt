package com.tecsup.murocampus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tecsup.murocampus.data.PostRepositoryRemote
import com.tecsup.murocampus.data.remote.RetrofitClient
import com.tecsup.murocampus.ui.PantallaCrear
import com.tecsup.murocampus.ui.PantallaDetalle
import com.tecsup.murocampus.ui.PantallaEditar
import com.tecsup.murocampus.ui.PantallaLista
import com.tecsup.murocampus.ui.theme.MuroCampusTheme
import com.tecsup.murocampus.viewmodel.PostsViewModel
import com.tecsup.murocampus.viewmodel.PostsViewModelFactory

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val fabrica = PostsViewModelFactory(PostRepositoryRemote(RetrofitClient.api))

        setContent {
            MuroCampusTheme {
                val vm: PostsViewModel = viewModel(factory = fabrica)

                // null = se ve la lista; un id = se ve el detalle de esa publicación.
                var idSeleccionado by rememberSaveable { mutableStateOf<Int?>(null) }
                // true = se ve la pantalla para crear una publicación.
                var creando by rememberSaveable { mutableStateOf(false) }
                // Título con el que se abre la edición; null = no se está editando.
                var tituloEditar by rememberSaveable { mutableStateOf<String?>(null) }

                BackHandler(enabled = idSeleccionado != null || creando) {
                    when {
                        creando -> creando = false
                        tituloEditar != null -> tituloEditar = null
                        else -> idSeleccionado = null
                    }
                }

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("MuroCampus") },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                titleContentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        )
                    },
                    floatingActionButton = {
                        if (idSeleccionado == null && !creando) {
                            FloatingActionButton(onClick = { creando = true }) {
                                Text("+", style = MaterialTheme.typography.headlineSmall)
                            }
                        }
                    }
                ) { espacio ->
                    Box(Modifier.fillMaxSize().padding(espacio)) {
                        val id = idSeleccionado
                        val titulo = tituloEditar
                        if (creando) {
                            PantallaCrear(vm = vm)
                        } else if (id != null && titulo != null) {
                            PantallaEditar(vm = vm, id = id, tituloActual = titulo)
                        } else if (id == null) {
                            PantallaLista(
                                vm = vm,
                                onAbrir = { idPost ->
                                    vm.abrir(idPost)
                                    idSeleccionado = idPost
                                }
                            )
                        } else {
                            PantallaDetalle(
                                vm = vm,
                                id = id,
                                onBorrado = { idSeleccionado = null },
                                onEditar = { tituloPost ->
                                    vm.limpiarEdicion()
                                    tituloEditar = tituloPost
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
