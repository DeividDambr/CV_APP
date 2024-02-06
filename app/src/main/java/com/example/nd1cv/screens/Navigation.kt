package com.example.nd1cv.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.nd1cv.preferences.Preferences

sealed class Screen(val route: String){
    object MainScreen: Screen("main")
    object CVScreen: Screen("cv")
    object PortfolioScreen: Screen("portfolio")
    object ContactUsScreen: Screen("contact us")
    object SettingsScreen: Screen("settings")
    object AddEntryScreen: Screen("add entry")
}

@Composable
fun NavigationAppHost(navController: NavHostController){
    val context = LocalContext.current
    val urlManager = Preferences(context)

    NavHost(navController = navController, startDestination = Screen.MainScreen.route){
        composable(route = Screen.MainScreen.route){
            HomeScreen(navController = navController)
        }
        composable(route = Screen.CVScreen.route){
            CVScreen(navController = navController)
        }
        composable(route = Screen.PortfolioScreen.route){
            PortfolioScreen(navController = navController)
        }
        composable(route = Screen.ContactUsScreen.route){
            ContactScreen(navController = navController)
        }
        composable(route = Screen.SettingsScreen.route){
            SettingsScreen(navController = navController)
        }
        composable(route = Screen.AddEntryScreen.route){
            AddEntryScreen(navController = navController, urlManager.getDeletedId())
        }
    }
}

data class NavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String
)