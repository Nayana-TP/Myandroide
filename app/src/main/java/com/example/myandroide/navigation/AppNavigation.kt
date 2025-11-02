package com.example.myandroide.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myandroide.HomeScreen
import com.example.myandroide.auth.AuthViewModel
import com.example.myandroide.auth.LoginScreen
import com.example.myandroide.auth.SignUpScreen
import com.example.myandroide.billing.BillingViewModel
import com.example.myandroide.billing.PremiumScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel()
    val billingViewModel: BillingViewModel = viewModel()
    val user by authViewModel.user.collectAsState()

    NavHost(navController = navController, startDestination = if (user == null) "login" else "home") {
        composable("login") {
            LoginScreen(
                onLoginClick = { email, password ->
                    authViewModel.login(email, password)
                },
                onSignUpClick = { navController.navigate("signup") }
            )
        }
        composable("signup") {
            SignUpScreen(
                onSignUpClick = { email, password ->
                    authViewModel.signUp(email, password)
                },
                onLoginClick = { navController.navigate("login") }
            )
        }
        composable("home") {
            HomeScreen(
                onLogoutClick = { authViewModel.logout() },
                onPremiumClick = { navController.navigate("premium") }
            )
        }
        composable("premium") {
            PremiumScreen(billingViewModel = billingViewModel)
        }
    }
}