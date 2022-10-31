package com.cesc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.cesc.presentation.compose.WeatherScreen
import com.cesc.ui.theme.MvisamplecomposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MvisamplecomposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    // WeatherScreen()
//                    CaculatorScreen()
                    TestUI()
                }
            }
        }
    }
}

@Composable
fun TestUI() {
    Column(modifier = Modifier.fillMaxSize().background(color = Color.Black)) {

        ProgressBar(modifier = Modifier.fillMaxWidth().height(20.dp),
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
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
                    .background(color = Color.Red))
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
            .clip(RoundedCornerShape(cornerRadius)) // 裁剪矩形区域为圆角矩形，将超出圆角矩形的部分绘制去掉
            .drawWithContent {
                drawContent() // 先绘制内容后绘制自定义图形，这样我们绘制的图形将显示在内容区域上方
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
