package com.cesc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cesc.ui.theme.MvisamplecomposeTheme
import com.cesc.weatherfeature.presentation.compose.WeatherScreen
import com.cn.featuresearchcity.presentation.compose.SearchCityScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            MvisamplecomposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    AppNavHost(navController = navController)
                }
            }
        }
    }
}

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(modifier = Modifier.fillMaxSize(), navController = navController, startDestination = NavRoute.Splash.route) {

        composable(NavRoute.Splash.route) {
            SplashScreen(navController)
        }

        composable(NavRoute.WeatherInfo.route) {
            WeatherScreen()
        }

        composable(NavRoute.SearchCity.route) {
            SearchCityScreen()
        }
    }
}

@Composable
fun SplashScreen(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize()) {
        Button(onClick = {
            navController.navigate(NavRoute.WeatherInfo.route)
        }) {
            Text(text = "Weather Info")
        }

        Button(onClick = {
            navController.navigate(NavRoute.SearchCity.route)
        }) {
            Text(text = "Search City")
        }
    }

}

sealed class NavRoute(val route: String) {
    object Splash : NavRoute("splash")
    object WeatherInfo : NavRoute("weatherInfo")
    object SearchCity : NavRoute("searchCity")
}

@Composable
fun TestUI() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black)
    ) {

        ProgressBar(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp),
            progress = 50f,
            color = Color.Red,
            backgroundColor = Color.Black,
            cornerRadius = 5.dp
        )


        Card(
            modifier = Modifier.padding(top = 20.dp),
            elevation = 4.dp,
            shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)
        ) {
            Column() {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                        .background(color = Color.Red)
                )
                Text("AB CDE", fontWeight = FontWeight.W700)
                Text("+0 12345678")
                Text("XYZ city.", color = Color.Gray)
            }
        }


    }
}

@Composable
fun ProgressBar(
    modifier: Modifier,
    progress: Float,
    color: Color,
    cornerRadius: Dp,
    backgroundColor: Color,
) {
    Surface(
        shape = RoundedCornerShape(cornerRadius),
        color = backgroundColor,
        modifier = modifier
            .clip(RoundedCornerShape(cornerRadius)) // ??????????????????????????????????????????????????????????????????????????????
            .drawWithContent {
                drawContent() // ???????????????????????????????????????????????????????????????????????????????????????????????????
                val progressWidth = drawContext.size.width * progress
                drawRect(
                    color = color,
                    size = drawContext.size.copy(width = progressWidth),
                )
            },
        content = {}
    )
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MvisamplecomposeTheme {
        Greeting("Android")
    }
}
