package com.tecsup.mibodega.ui.cliente.modelo

data class Pedido(
    val numero: Int,
    val total: Double,
    val tipoEntrega: String,
    val direccion: String
)
