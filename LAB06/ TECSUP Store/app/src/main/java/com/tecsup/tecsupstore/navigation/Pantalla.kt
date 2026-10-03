package com.tecsup.tecsupstore.navigation

sealed class Pantalla(
    val ruta: String
) {
    object Inicio : Pantalla("inicio")
    object Pedidos : Pantalla("pedidos")
    object Favoritos : Pantalla("favoritos")
    object Perfil : Pantalla("perfil")
}
