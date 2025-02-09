package com.jyproject.presentation.ui.feature.common.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.jyproject.presentation.R
import com.jyproject.presentation.navigation.Screen


@Composable
fun BottomBar(
    navController: NavController,
    items: List<Screen>
){
    NavigationBar(
        modifier = Modifier
            .height(60.dp)
            .shadow(elevation = 10.dp, shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)),
        containerColor = colorResource(id = R.color.white)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        items.forEach { screen ->
            NavigationBarItem(
                modifier = Modifier
                    .background(Color.White)
                ,
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                icon = {
                    Icon(
                        imageVector = screen.icon,
                        contentDescription = null,
                    )
                },
                onClick = {
                    if(currentDestination?.route != screen.route) {
                        navController.navigate(screen.route){
                            popUpTo(0) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent,
                    selectedIconColor = colorResource(id = R.color.app_base),
                    unselectedIconColor = colorResource(id = R.color.light_gray_hard1)
                )
            )
        }
    }
}