package com.cesc.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.cesc.ui.theme.MvisamplecomposeTheme

class WanAndroidBottomNavigationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MvisamplecomposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    BottomNavigationActivityContent()
                }

            }
        }
    }
}

val navItems = listOf(
    BottomNavItem.Home,
    BottomNavItem.Profile
)

@Composable
internal fun BottomNavigationActivityContent() {
    val navController = rememberNavController()
//    val showBottomBar = navController.currentBackStackEntryAsState().value?.destination
    val currentRoute = navController.currentBackStackEntry?.destination?.route


    val bottomBarHeight = 85.dp
    val bottomBarHeightPx = with(LocalDensity.current) {
        bottomBarHeight.roundToPx().toFloat()
    }

    val bottomBarOffsetHeightPx = remember { mutableStateOf(0f) }
//    val nestedScrollConnectionPx = remember {
//        object : NestedScrollConnection {
//            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
//                return super.onPreScroll(available, source)
//            }
//        }
//    }

    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current

    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = {
            BottomAppBar(
                content = {
                    WanAndroidBottomNavigation(
                        navItems = navItems,
                        navController = navController
                    )
                }
            )
        }
    ) {
        Box(modifier = Modifier.padding(bottom = it.calculateBottomPadding())) {

            NavHost(
                navController = navController,
                startDestination = BottomNavItem.Home.route
            ) {

                composable(BottomNavItem.Home.route) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Text(text = "home")
                    }
                }
                composable(BottomNavItem.Profile.route) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Text(text = "profile")
                    }
                }

            }

        }
    }
}