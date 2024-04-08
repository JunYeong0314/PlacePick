package com.jyproject.presentation

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jyproject.presentation.anim.noAnimatedComposable
import com.jyproject.presentation.ui.home.HomeScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(){
    val items = listOf(
        Screen.Home
    )
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomBar(navController = navController, items = items) }
    ) { innerPadding->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = Screen.Home.route
        ){
            noAnimatedComposable(Screen.Home.route) { HomeScreen(navController) }
        }

    }

}

@Composable
fun BottomBar(
    navController: NavController,
    items: List<Screen>
){
    BottomNavigation {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        items.forEach { screen ->
            BottomNavigationItem(
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
                onClick = {
                    navController.navigate(screen.route){
                        popUpTo(navController.graph.findStartDestination().id){
                            saveState = true
                        }
                    }
                }
            )
        }
    }

}

sealed class Screen(val route: String, @StringRes val resourceId: Int){
    data object Home: Screen("home", R.string.route_home)
}