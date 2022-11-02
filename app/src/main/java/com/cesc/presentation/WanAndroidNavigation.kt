package com.cesc.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector


sealed class BottomNavItem(val route: String, val icon: ImageVector, val name: String) {
    object Home : BottomNavItem("bottom_home", Icons.Default.Home, "Home")

    object Category : BottomNavItem("bottom_category", Icons.Default.Menu, "Category")
    object Profile : BottomNavItem("bottom_profile", Icons.Default.Person, "Profile")

    object Collection : BottomNavItem("bottom_collection", Icons.Default.Favorite, "Collection")
}


sealed class ScreenNav(val route: String) {
}
