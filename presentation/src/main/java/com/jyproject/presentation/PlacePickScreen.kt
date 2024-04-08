package com.jyproject.presentation

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.jyproject.presentation.ui.login.LoginScreen
import com.jyproject.presentation.anim.noAnimatedComposable
import com.jyproject.presentation.anim.verticallyAnimatedComposable

enum class Destination(@StringRes val title: Int){
    Login(title = R.string.route_login),
    Main(title = R.string.route_main)
}

@Composable
fun PlacePickApp(
    context: Context
){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Destination.Login.name
    ){
        noAnimatedComposable(route = Destination.Login.name){
            LoginScreen(
                context = context,
                isLogin = {
                    navController.navigate(Destination.Main.name){
                        popUpTo(Destination.Login.name){ inclusive = true }
                    }
                }
            )
        }

        verticallyAnimatedComposable(route = Destination.Main.name){
            MainScreen()
        }

    }
}