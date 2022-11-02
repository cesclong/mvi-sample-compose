package com.cesc.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomAppBar
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cesc.ui.theme.AppTheme
import com.cn.featurewanandroidhome.presentation.composable.HomeScreen
import com.cn.featurewanandroidhome.presentation.graph.createHomeNavGraph

class WanAndroidBottomNavigationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
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
    BottomNavItem.Category,
    BottomNavItem.Collection,
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
                modifier = Modifier.background(MaterialTheme.colors.background),
                navController = navController,
                startDestination = BottomNavItem.Home.route
            ) {

                createHomeNavGraph(navController)

                composable(BottomNavItem.Home.route) {
                    HomeScreen(navController)
                }

                composable(BottomNavItem.Profile.route) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Text(text = "profile")
                    }
                }

                composable(BottomNavItem.Collection.route) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Text(text = "profile")
                    }
                }

                composable(BottomNavItem.Category.route) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Text(text = "profile")
                    }
                }

            }

        }
    }
}