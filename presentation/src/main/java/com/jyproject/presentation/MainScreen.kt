package com.jyproject.presentation

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jyproject.presentation.anim.noAnimatedComposable
import com.jyproject.presentation.ui.home.HomeScreen
import com.jyproject.presentation.ui.mypage.MyPageScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(){
    val items = listOf(
        Screen.Home, Screen.MyPage
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
            noAnimatedComposable(Screen.MyPage.route) { MyPageScreen(navController) }
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
                modifier = Modifier.background(Color.White),
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                icon = {
                    Icon(
                        imageVector = screen.icon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary
                    )
               },
                onClick = {
                    navController.navigate(screen.route){
                        popUpTo(navController.graph.findStartDestination().id){
                            saveState = true
                        }
                    }
                },
            )
        }
    }


}

sealed class Screen(val route: String, @StringRes val resourceId: Int, val icon: ImageVector){
    data object Home: Screen("home", R.string.route_home, icon = Icons.Filled.Home)
    data object MyPage: Screen("mypage", R.string.route_mypage, icon = Icons.Filled.FavoriteBorder)
}