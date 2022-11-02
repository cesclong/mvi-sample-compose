package com.cesc.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.cesc.ui.theme.AppTheme


@Composable
fun WanAndroidBottomNavigation(
    navItems: List<BottomNavItem> = emptyList(),
    navController: NavController
) {
    BottomNavigation(backgroundColor = MaterialTheme.colors.background) {

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        navItems.forEach { item ->
            BottomNavigationItem(
                modifier = Modifier.background(AppTheme.colors.themeUi),
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                },
                
                label = { Text(text = item.name)},

                icon = {
                    Image(imageVector = item.icon, contentDescription = null)
                },
                selectedContentColor = Color.White,
                unselectedContentColor = Color.Black
            )

        }

    }
}