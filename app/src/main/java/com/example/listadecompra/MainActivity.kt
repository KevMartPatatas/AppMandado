package com.example.listadecompra

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val viewModel: CompraViewModel = viewModel()

            NavHost(navController = navController, startDestination = "categorias") {
                // PANTALLA 1: Categorías
                composable("categorias") {
                    PantallaCategorias(viewModel, onCategoriaClick = { cat ->
                        navController.navigate("productos/$cat")
                    }, onVerResumen = {
                        navController.navigate("resumen")
                    })
                }
                // PANTALLA 2: Selección de productos
                composable(
                    "productos/{categoria}",
                    arguments = listOf(navArgument("categoria") { type = NavType.StringType })
                ) { backStackEntry ->
                    val cat = backStackEntry.arguments?.getString("categoria") ?: ""
                    PantallaSeleccion(viewModel, cat, onVolver = { navController.popBackStack() })
                }
                // PANTALLA 3: Resumen final
                composable("resumen") {
                    PantallaResumen(viewModel, onVolver = { navController.popBackStack() })
                }
            }
        }
    }
}

// --- COMPONENTES DE LAS PANTALLAS ---

@Composable
fun PantallaCategorias(vm: CompraViewModel, onCategoriaClick: (String) -> Unit, onVerResumen: () -> Unit) {
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Selecciona una Categoría", style = MaterialTheme.typography.headlineMedium)
        LazyColumn(Modifier.weight(1f)) {
            items(vm.categorias) { cat ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp).clickable { onCategoriaClick(cat) }) {
                    Text(cat, modifier = Modifier.padding(16.dp))
                }
            }
        }
        Button(onClick = onVerResumen, modifier = Modifier.fillMaxWidth()) {
            Text("Ver Carrito / Resumen")
        }
    }
}

@Composable
fun PantallaSeleccion(vm: CompraViewModel, categoria: String, onVolver: () -> Unit) {
    val productosFiltrados = vm.listaProductos.filter { it.categoria == categoria }
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Productos de $categoria", style = MaterialTheme.typography.headlineMedium)
        LazyColumn(Modifier.weight(1f)) {
            items(productosFiltrados) { producto ->
                Row(Modifier.fillMaxWidth().padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(producto.nombre)
                    Checkbox(checked = producto.seleccionado, onCheckedChange = { vm.alternarSeleccion(producto) })
                }
            }
        }
        Button(onClick = onVolver) { Text("Volver") }
    }
}

@Composable
fun PantallaResumen(vm: CompraViewModel, onVolver: () -> Unit) {
    val seleccionados = vm.listaProductos.filter { it.seleccionado }
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Tu Lista de Compra", style = MaterialTheme.typography.headlineMedium)
        LazyColumn(Modifier.weight(1f)) {
            items(seleccionados) { prod ->
                Text("• ${prod.nombre} (${prod.categoria})", modifier = Modifier.padding(8.dp))
            }
        }
        Button(onClick = onVolver) { Text("Seguir Comprando") }
    }
}