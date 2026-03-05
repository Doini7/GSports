package com.example.gesports.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.gesports.ui.login.screens.auth.LoginScreen
import com.example.gesports.ui.login.backend.ges_user.pista.GesFacilitiesViewModel
import com.example.gesports.ui.login.backend.ges_user.reserva.GesReservationViewModel
import com.example.gesports.ui.login.backend.ges_user.equipo.GesTeamViewModel
import com.example.gesports.ui.login.backend.ges_user.pista.GesFacilitiesViewModelFactory
import com.example.gesports.ui.login.backend.ges_user.reserva.GesReservationViewModelFactory
import com.example.gesports.ui.login.backend.ges_user.equipo.GesTeamViewModelFactory
import com.example.gesports.ui.login.backend.ges_user.usuario.GesUserViewModel
import com.example.gesports.ui.login.backend.ges_user.usuario.GesUserViewModelFactory
import com.example.gesports.ui.login.screens.home.HomeScreen
import com.example.gesports.ui.login.screens.dashboard.AdminScreen
import com.example.gesports.ui.login.screens.home.ConfirmBookingScreen
import com.example.gesports.ui.login.screens.dashboard.form.FormFacilityScreen
import com.example.gesports.ui.login.screens.dashboard.form.FormTeamScreen
import com.example.gesports.ui.login.screens.dashboard.form.FormUserScreen
import com.example.gesports.ui.login.screens.dashboard.GesFacilityScreen
import com.example.gesports.ui.login.screens.dashboard.GesReserveScreen
import com.example.gesports.ui.login.screens.dashboard.GesTeamScreen
import com.example.gesports.ui.login.screens.dashboard.GesUserScreen
import com.example.gesports.ui.login.screens.home.HistoryScreen
import com.example.gesports.ui.login.screens.auth.RegisterScreen
import com.example.gesports.ui.login.screens.home.ReserveUserScreen
import com.example.gesports.ui.login.screens.home.MapScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val context = LocalContext.current

    // ViewModels con sus respectivos Factories
    val viewModel: GesUserViewModel = viewModel(
        factory = GesUserViewModelFactory(context.applicationContext)
    )
    val gesReservationViewModel: GesReservationViewModel = viewModel(
        factory = GesReservationViewModelFactory(context.applicationContext)
    )
    val facilityViewModel: GesFacilitiesViewModel = viewModel(
        factory = GesFacilitiesViewModelFactory(context.applicationContext)
    )
    val teamViewModel: GesTeamViewModel = viewModel(
        factory = GesTeamViewModelFactory(context.applicationContext)
    )

    // Estado del usuario logueado
    val userState by viewModel.loginResult

    NavHost(
        navController = navController,
        startDestination = "login",
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {

        composable("login") { LoginScreen(navController, viewModel) }
        composable("register") { RegisterScreen(navController, viewModel) }
        composable("dashboard") { AdminScreen(navController = navController, onLogout = {
            viewModel.logout()
            navController.navigate("login") {
                popUpTo(0) { inclusive = true }
            }
        }) }

        composable("ges_user") { GesUserScreen(navController = navController, viewModel = viewModel) }
        composable("ges_team") { GesTeamScreen(navController = navController, viewModel = teamViewModel) }
        composable("ges_facility") { GesFacilityScreen(navController = navController, viewModel = facilityViewModel) }
        composable("ges_reserve") { GesReserveScreen(navController = navController, viewModel = gesReservationViewModel) }

        // Formularios
        composable(
            route = "facility_form/{facilityId}",
            arguments = listOf(navArgument("facilityId") { type = NavType.IntType })
        ) { backStackEntry ->
            val facilityId = backStackEntry.arguments?.getInt("facilityId") ?: 0
            FormFacilityScreen(navController = navController, facilityId = facilityId)
        }

        composable(
            route = "team_form/{teamId}",
            arguments = listOf(navArgument("teamId") { type = NavType.IntType })
        ) { backStackEntry ->
            val teamId = backStackEntry.arguments?.getInt("teamId") ?: 0
            FormTeamScreen(navController = navController, equipoId = teamId)
        }

        composable(
            route = "user_form/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            FormUserScreen(navController, id)
        }

        // Pantallas de Usuario / Home
        composable(
            route = "home/{nombre}",
            arguments = listOf(navArgument("nombre") { type = NavType.StringType })
        ) { backStackEntry ->
            val nombre = backStackEntry.arguments?.getString("nombre")

            HomeScreen(
                navController = navController,
                nombre = nombre,
                onLogout = {
                    viewModel.logout()
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
        composable("reserve_user") {
            ReserveUserScreen(navController = navController, facilityViewModel = facilityViewModel)
        }

        composable("map_screen") {
            MapScreen(
                navController = navController,
                onLogout = {
                    viewModel.logout()
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable("history") {
            val currentUserId = userState?.id ?: 0
            val userRole      = userState?.role ?: "jugador"
            val userTeamId    = userState?.equipoId  // ← ya existía, solo asegúrate de pasarlo

            HistoryScreen(
                navController      = navController,
                viewModel          = gesReservationViewModel,
                facilityViewModel  = facilityViewModel,
                currentUserId      = currentUserId,
                userRole           = userRole,
                userTeamId         = userTeamId  // ← añadir este
            )
        }

        composable(
            route = "confirm_booking/{facilityId}",
            arguments = listOf(navArgument("facilityId") { type = NavType.IntType })
        ) { backStackEntry ->
            val facilityId = backStackEntry.arguments?.getInt("facilityId") ?: 0

            // Datos del usuario logueado extraídos del userState
            val currentUserId = userState?.id ?: 0
            val userRole = userState?.role ?: "jugador"
            val userTeamId = userState?.equipoId

            ConfirmBookingScreen(
                navController = navController,
                facilityId = facilityId,
                facilityViewModel = facilityViewModel,
                gesReservationViewModel = gesReservationViewModel,
                currentUserId = currentUserId,
                userRole = userRole,
                userTeamId = userTeamId
            )
        }
    }
}