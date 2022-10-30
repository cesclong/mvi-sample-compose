package com.cesc.presentation.compose

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
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
import com.cesc.domain.model.CityInfo
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
            .background(color = MaterialTheme.colors.background)
    ) {

        item { CityInfoView(model.cityInfo) }

        item {
            Divider(
                color = Color.Black,
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth(),
                thickness = 1.dp
            )
        }

        item {
            TodayWeatherView(model)
        }

        item {
            Divider(
                color = Color.Black,
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth(),
                thickness = 1.dp
            )
        }
        
        item { 
            WeathersView(weathers = model.weatherInfos)
        }
    }

}

@Composable
internal fun CityInfoView(cityInfo: CityInfo) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        TitleView("城市信息")
        RowItemView("省份:", cityInfo.parent)
        RowItemView("城市:", cityInfo.city)
        RowItemView("更新时间:", cityInfo.updateTime)
    }
}

@Composable
internal fun TodayWeatherView(model: WeatherDomainModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        TitleView("今日天气")
        RowItemView(title = "湿度:", content = model.shidu)
        RowItemView(title = "PM10:", content = model.pm10.toString())
        RowItemView(title = "PM25:", content = model.pm25.toString())
        RowItemView(title = "空气质量:", content = model.quality)
        RowItemView(title = "温度:", content = model.wendu.toString())
        RowItemView(title = "感冒提醒:", content = model.ganmao)
    }
}

@Composable
fun WeathersView(weathers: List<Weather>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        TitleView("15天预报")
        
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(5.dp))
        
        LazyRow(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()) {
            items(weathers) { item ->
                SingleDayWeatherView(weather = item)
            }
        }
    }
}

@Composable
fun SingleDayWeatherView(weather: Weather){

    Card(modifier = Modifier.size(200.dp), elevation = 5.dp, backgroundColor = MaterialTheme.colors.background) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(text = "${weather.high} - ${weather.low}")
        }

    }
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


@Composable
internal fun TitleView(text: String) {
    Text(
        modifier = Modifier.padding(top = 10.dp),
        text = text,
        style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold)
    )
}

@Composable
internal fun RowItemView(title: String, content: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 5.dp)
    ) {
        Text(
            text = title,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.body1
        )
        Text(
            text = content,
            style = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.primary)
        )
    }
}