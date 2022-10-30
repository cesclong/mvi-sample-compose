package com.cesc.presentation.compose

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cesc.domain.model.Weather
import com.cesc.domain.model.WeatherDomainModel
import com.cesc.presentation.*
import org.koin.androidx.compose.koinViewModel

/**
 * <类说明 必填>
 *
 * @author shilong
 * @version [版本号]
 * @see [参考资料]
 * @since [历史 创建日期:2022/10/29]
 */

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun WeatherScreen(viewModel: WeatherViewModel = koinViewModel()) {
    LaunchedEffect(key1 = Unit) {
        viewModel.sendAction(WeatherAction.FetchWeather)
    }
    val uiState by viewModel.uiState.collectAsState()

    when {
        uiState.isLoading -> LoadingView()
        uiState.isError -> ErrorView(msg = uiState.errorMessage)
        uiState.weatherDomainModel != null -> ShowWeather(model = uiState.weatherDomainModel!!)
    }

}

@Composable
fun ShowWeather(model: WeatherDomainModel) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp)
    ) {

        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Text(
                    modifier = Modifier.padding(top = 10.dp),
                    text = "城市信息",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(top = 5.dp)
                ) {
                    Text(text = "省份:", modifier = Modifier.weight(1f), fontSize = 16.sp)
                    Text(text = model.cityInfo.parent, fontSize = 16.sp)
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(top = 5.dp)
                ) {
                    Text(text = "城市:", modifier = Modifier.weight(1f), fontSize = 16.sp, color = Color.Black)
                    Text(text = model.cityInfo.city, fontSize = 16.sp, color = Color.Black)
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(top = 5.dp)
                ) {
                    Text(text = "更新时间:", modifier = Modifier.weight(1f), fontSize = 16.sp, color = Color.Black)
                    Text(text = model.cityInfo.updateTime, fontSize = 16.sp, color = Color.Black)
                }
            }
        }

        item {

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(color = Color.Black)
            )
        }

    }

}

@Composable
fun WeatherView(weather: Weather) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {
        Text(text = "${weather.low} ~ ${weather.high}")
    }

    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(color = Color.Black)
    )
}

@Composable
fun LoadingView() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "Loading...", fontSize = 28.sp, textAlign = TextAlign.Center)
    }
}

@Composable
fun ErrorView(msg: String) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "Error:${msg}", fontSize = 28.sp, textAlign = TextAlign.Center)
    }
}
