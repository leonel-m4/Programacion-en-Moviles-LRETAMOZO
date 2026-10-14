import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun CategoriasScreen(vm: CategoriasViewModel, onEditar: (Int) -> Unit) {
    val categorias by vm.categorias.collectAsState()
    LazyColumn {
        items(categorias, key = { it.id }) { c ->
            CategoriaItem(c, onToggle = { vm.alternar(c) }, onEditar = { onEditar(c.id) })
        }
    }
}