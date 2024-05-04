package com.jyproject.presentation

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jyproject.presentation.ui.theme.CustomTheme
import com.jyproject.presentation.anim.noAnimatedComposable
import com.jyproject.presentation.anim.verticallyAnimatedComposable
import com.jyproject.presentation.ui.home.AddPlaceScreen
import com.jyproject.presentation.ui.home.HomeScreen
import com.jyproject.presentation.ui.mypage.MyPageScreen
import com.jyproject.presentation.ui.util.Destination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(){
    val items = listOf(
        Screen.Home, Screen.MyPage
    )
    val navController = rememberNavController()
    var isBottomBar by remember { mutableStateOf(true) }

    navController.addOnDestinationChangedListener { _, destination, _ ->
        isBottomBar = destination.route != Destination.ADD_PLACE_ROUTE
    }

    Scaffold(
        // A page that hides the BottomBar
        bottomBar = { if(isBottomBar) BottomBar(navController = navController, items = items) },
        topBar = { TopBar() }
    ) { innerPadding->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = Screen.Home.route
        ){
            noAnimatedComposable(Screen.Home.route) { HomeScreen(navController = navController) }
            verticallyAnimatedComposable(Destination.ADD_PLACE_ROUTE) { AddPlaceScreen() }
            noAnimatedComposable(Screen.MyPage.route) { MyPageScreen(navController = navController) }
        }

    }

}

@Composable
private fun BottomBar(
    navController: NavController,
    items: List<Screen>
){
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        items.forEach { screen ->
            NavigationBarItem(
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

@Composable
private fun TopBar(){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp)
            .background(Color.White)
        ,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(painter = painterResource(id = R.drawable.ic_app), contentDescription = "ic_app")
        Text(
            text = "PlacePick",
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.app_base)
        )
    }
}

sealed class Screen(val route: String, @StringRes val resourceId: Int, val icon: ImageVector){
    data object Home: Screen("home", R.string.route_home, icon = Icons.Filled.Home)
    data object MyPage: Screen("mypage", R.string.route_mypage, icon = Icons.Filled.FavoriteBorder)
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight"
)
@Composable
fun PreviewMain() {
    CustomTheme {
        MainScreen()
    }
}