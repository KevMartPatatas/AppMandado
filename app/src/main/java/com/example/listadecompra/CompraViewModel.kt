package com.example.listadecompra

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

data class Producto(val nombre: String, val categoria: String, var seleccionado: Boolean = false)

class CompraViewModel : ViewModel() {
    // Lista maestra de productos
    val listaProductos = mutableStateListOf(
        Producto("Platano", "Frutas"),
        Producto("Manzana", "Frutas"),
        Producto("Naranja", "Frutas"),
        Producto("Uva", "Frutas"),
        Producto("Lechuga", "Verduras"),
        Producto("Espinaca", "Verduras"),
        Producto("Brocoli", "Verduras"),
        Producto("Berenjena", "Verduras"),
        Producto("Jabón", "Higiene"),
        Producto("Shampoo", "Higiene"),
        Producto("Desodorante", "Higiene"),
        Producto("Pasta dental", "Higiene"),
        Producto("Arroz", "Cereales"),
        Producto("Avena", "Cereales"),
        Producto("Trigo", "Cereales"),
        Producto("Maíz", "Cereales")
    )

    // Categorías disponibles
    val categorias = listOf("Frutas", "Verduras", "Higiene", "Cereales")

    // Función para cambiar el estado de selección
    fun alternarSeleccion(producto: Producto) {
        val index = listaProductos.indexOf(producto)
        if (index != -1) {
            listaProductos[index] = listaProductos[index].copy(seleccionado = !producto.seleccionado)
        }
    }
}