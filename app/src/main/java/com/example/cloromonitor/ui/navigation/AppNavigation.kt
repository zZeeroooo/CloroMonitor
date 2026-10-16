package com.example.cloromonitor.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cloromonitor.ui.screen.AlertasScreen
import com.example.cloromonitor.ui.screen.PuntosScreen
import com.example.cloromonitor.ui.screen.ResumenScreen
import com.example.cloromonitor.ui.viewmodel.MainViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val viewModel: MainViewModel = viewModel()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    // NUEVO: estado del drawer y scope para abrir/cerrar
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val titulo = when (currentRoute) {
        AppRoutes.RESUMEN -> "Resumen"
        AppRoutes.PUNTOS -> "Puntos de medición"
        AppRoutes.ALERTAS -> "Alertas"
        else -> "CloroMonitor"
    }

    val irA: (String) -> Unit = { ruta ->
        navController.navigate(ruta) { launchSingleTop = true }
    }

    // NUEVO: ModalNavigationDrawer envuelve todo el Scaffold
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    text = "CloroMonitor",
                    modifier = Modifier.padding(24.dp)
                )
                NavigationDrawerItem(
                    selected = currentRoute == AppRoutes.RESUMEN,
                    onClick = {
                        irA(AppRoutes.RESUMEN)
                        scope.launch { drawerState.close() }
                    },
                    icon = { Icon(Icons.Default.Home, contentDescription = null) },
                    label = { Text("Resumen") }
                )
                NavigationDrawerItem(
                    selected = currentRoute == AppRoutes.PUNTOS,
                    onClick = {
                        irA(AppRoutes.PUNTOS)
                        scope.launch { drawerState.close() }
                    },
                    icon = { Icon(Icons.Default.LocationOn, contentDescription = null) },
                    label = { Text("Puntos de medición") }
                )
                NavigationDrawerItem(
                    selected = currentRoute == AppRoutes.ALERTAS,
                    onClick = {
                        irA(AppRoutes.ALERTAS)
                        scope.launch { drawerState.close() }
                    },
                    icon = { Icon(Icons.Default.Warning, contentDescription = null) },
                    label = { Text("Alertas") }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(titulo) },
                    // NUEVO: botón ☰ que abre el drawer
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Abrir menú")
                        }
                    }
                )
            },
            bottomBar = {
                NavigationBar {
                    NavigationBarItem(
                        selected = currentRoute == AppRoutes.RESUMEN,
                        onClick = { irA(AppRoutes.RESUMEN) },
                        icon = { Icon(Icons.Default.Home, contentDescription = "Resumen") },
                        label = { Text("Resumen") }
                    )
                    NavigationBarItem(
                        selected = currentRoute == AppRoutes.PUNTOS,
                        onClick = { irA(AppRoutes.PUNTOS) },
                        icon = { Icon(Icons.Default.LocationOn, contentDescription = "Puntos") },
                        label = { Text("Puntos") }
                    )
                    NavigationBarItem(
                        selected = currentRoute == AppRoutes.ALERTAS,
                        onClick = { irA(AppRoutes.ALERTAS) },
                        icon = { Icon(Icons.Default.Warning, contentDescription = "Alertas") },
                        label = { Text("Alertas") }
                    )
                }
            }
        ) { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = AppRoutes.RESUMEN,
                modifier = Modifier.padding(paddingValues)
            ) {
                composable(AppRoutes.RESUMEN) { ResumenScreen(viewModel) }
                composable(AppRoutes.PUNTOS) { PuntosScreen(viewModel) }
                composable(AppRoutes.ALERTAS) { AlertasScreen(viewModel) }
            }
        }
    }
}