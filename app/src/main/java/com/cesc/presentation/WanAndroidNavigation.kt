package com.cesc.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector


sealed class BottomNavItem(val route: String, val icon : ImageVector, val name : String){
    object Home : BottomNavItem("bottom_home", Icons.Default.Home, "Home")
    object Profile : BottomNavItem("bottom_profile", Icons.Default.Person, "profile")
}


sealed class ScreenNav(val route : String){
}
