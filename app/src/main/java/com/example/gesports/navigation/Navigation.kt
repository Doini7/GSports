package com.example.gesports.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.gesports.ui.login.LoginScreen
import com.example.gesports.ui.login.backend.ges_user.GesUserViewModel
import com.example.gesports.ui.login.backend.ges_user.GesUserViewModelFactory
import com.example.gesports.ui.login.home.HomeScreen
import com.example.gesports.ui.login.screens.AdminScreen
import com.example.gesports.ui.login.screens.FormUserScreen
import com.example.gesports.ui.login.screens.GesUserScreen
import com.example.gesports.ui.login.screens.RegisterScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    val context = LocalContext.current
    val viewModel: GesUserViewModel = viewModel(
        factory = GesUserViewModelFactory(context.applicationContext)
    )

    NavHost(navController = navController, startDestination = "login") {

        composable("login") { LoginScreen(navController, viewModel) }
        composable("register") { RegisterScreen(navController , viewModel) }
        composable("dashboard") { AdminScreen(navController) }
        composable("ges_user") { GesUserScreen(navController = navController, viewModel = viewModel) }
        composable(
            route = "home/{nombre}",
            arguments = listOf(navArgument("nombre") { type = NavType.StringType })
        )
        {
                backStackEntry ->
            val nombre = backStackEntry.arguments?.getString("nombre")
            HomeScreen(navController, nombre)
        }

        composable(
            route = "user_form/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->

            val id = backStackEntry.arguments?.getInt("id") ?: 0

            FormUserScreen(navController, id)
        }
    }
}
