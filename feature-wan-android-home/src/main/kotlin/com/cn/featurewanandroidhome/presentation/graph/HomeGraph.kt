package com.cn.featurewanandroidhome.presentation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.cn.featurewanandroidhome.presentation.composable.HomeScreen
import com.cn.featurewanandroidhome.presentation.composable.SquareScreen

sealed class WanAndroidHomeRoute(val route : String){
    object Square :  WanAndroidHomeRoute("wan_android_home_square")
}

internal const val HomeRoute = "HomeRouteFromWanAndroid"

fun NavGraphBuilder.createHomeNavGraph(navController: NavController){
    navigation(route = HomeRoute, startDestination = WanAndroidHomeRoute.Square.route){
        composable(route = WanAndroidHomeRoute.Square.route){
            SquareScreen(navController = navController)
        }

    }
}