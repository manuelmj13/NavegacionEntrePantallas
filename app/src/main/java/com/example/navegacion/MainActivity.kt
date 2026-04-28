package com.example.navegacion // Asegúrate de que este sea TU package real

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(
                colorScheme = lightColorScheme(
                    primary = Color(0xFF4CAF50), // Un verde naturaleza bonito
                    secondary = Color(0xFF2E7D32),
                    tertiary = Color(0xFFFFC107)
                )
            ) {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    NavegacionApp()
                }
            }
        }
    }
}

@Composable
fun NavegacionApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "inicio") {
        composable("inicio") { PantallaInicio(navController) }
        composable("detalle") { PantallaDetalle(navController) }
        composable("perfil") { PantallaPerfil(navController) }
    }
}

// Reutilizamos un diseño de tarjeta para que sea "bonito"
@Composable
fun TarjetaInformativa(titulo: String, descripcion: String, icono: ImageVector, colorIcono: Color) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icono, contentDescription = null, tint = colorIcono, modifier = Modifier.size(40.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(titulo, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(descripcion, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
            }
        }
    }
}

// --- PANTALLA 1: INICIO ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaInicio(navController: NavHostController) {
    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text("Mundo Natura", fontWeight = FontWeight.ExtraBold) }) }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(Icons.Default.Eco, contentDescription = null, modifier = Modifier.size(80.dp), tint = Color(0xFF4CAF50))
            Text("¡Explora lo desconocido!", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = { navController.navigate("detalle") },
                modifier = Modifier.height(50.dp).width(200.dp)
            ) {
                Icon(Icons.Default.ArrowForward, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Comenzar Viaje")
            }
        }
    }
}

// --- PANTALLA 2: DETALLE ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaDetalle(navController: NavHostController) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Destinos Sugeridos") }) }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            TarjetaInformativa("Bosques Tropicales", "Humedad alta y mucha vida.", Icons.Default.Forest, Color.Green)
            TarjetaInformativa("Montañas Rocosas", "Aire puro y vistas increíbles.", Icons.Default.Landscape, Color.Gray)

            Spacer(modifier = Modifier.weight(1.0f))

            Row(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
                OutlinedButton(onClick = { navController.popBackStack() }) { Text("Volver") }
                Button(onClick = { navController.navigate("perfil") }) { Text("Ver mi Perfil") }
            }
        }
    }
}

// --- PANTALLA 3: PERFIL ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaPerfil(navController: NavHostController) {
    Scaffold { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).background(MaterialTheme.colorScheme.primary.copy(alpha = 0.05f)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // "Foto" de perfil
            Surface(modifier = Modifier.size(100.dp), shape = RoundedCornerShape(50.dp), color = MaterialTheme.colorScheme.primary) {
                Icon(Icons.Default.Person, contentDescription = null, tint = Color.White, modifier = Modifier.padding(20.dp))
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text("Manuel Jirón", fontWeight = FontWeight.Bold, fontSize = 24.sp)
            Text("Explorador Nivel 10", color = Color.Gray)

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = { navController.navigate("inicio") { popUpTo("inicio") { inclusive = true } } },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text("Cerrar Sesión")
            }
        }
    }
}