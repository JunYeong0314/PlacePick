package com.jyproject.presentation.anim

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

private const val animDurationMillis = 400
fun NavGraphBuilder.horizontallyAnimatedComposable(
    route: String,
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit
){
    composable(
        route = route,
        content = content,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(animDurationMillis)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(animDurationMillis)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(animDurationMillis)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(animDurationMillis)
            )
        }
    )
}

fun NavGraphBuilder.verticallyAnimatedComposable(
    route: String,
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit,
) {
    composable(
        route = route,
        content = content,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Up,
                animationSpec = tween(animDurationMillis)
            )
        },
        exitTransition = {
            fadeOut(animationSpec = tween(animDurationMillis))
        },
        popEnterTransition = {
            fadeIn(animationSpec = tween(animDurationMillis))
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Down,
                animationSpec = tween(animDurationMillis)
            )
        },
    )
}

fun NavGraphBuilder.noAnimatedComposable(
    route: String,
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit,
){
    composable(
        route = route,
        content = content,
        enterTransition = {
            fadeIn(animationSpec = tween(durationMillis = 0))
        },
        exitTransition = {
            fadeOut(animationSpec = tween(durationMillis = 0))
        },
        popExitTransition = {
            fadeOut(animationSpec = tween(durationMillis = 0))
        },
        popEnterTransition = {
            fadeIn(animationSpec = tween(durationMillis = 0))
        },
    )

}

fun NavGraphBuilder.horizontallyAnimatedComposableArguments(
    route: String,
    arguments: List<NamedNavArgument>? = null,
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit,
){
    composable(
        route = route,
        content = content,
        arguments = arguments ?: emptyList(),
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(animDurationMillis)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(animDurationMillis)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(animDurationMillis)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(animDurationMillis)
            )
        }
    )
}

fun NavGraphBuilder.verticallyAnimatedComposableArguments(
    route: String,
    arguments: List<NamedNavArgument>? = null,
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit,
){
    composable(
        route = route,
        content = content,
        arguments = arguments ?: emptyList(),
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Up,
                animationSpec = tween(animDurationMillis)
            )
        },
        exitTransition = {
            fadeOut(animationSpec = tween(animDurationMillis))
        },
        popEnterTransition = {
            fadeIn(animationSpec = tween(animDurationMillis))
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Down,
                animationSpec = tween(animDurationMillis)
            )
        },
    )
}