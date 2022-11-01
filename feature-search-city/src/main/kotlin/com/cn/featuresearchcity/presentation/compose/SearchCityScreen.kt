package com.cn.featuresearchcity.presentation.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.cn.featuresearchcity.presentation.SearchCityViewModel
import org.koin.androidx.compose.koinViewModel

/**
 * <类说明 必填>
 *
 * @author  shilong
 * @version  [版本号]
 * @see  [参考资料]
 * @since  [历史 创建日期:2022/11/2]
 */

@Composable
fun SearchCityScreen(viewModel: SearchCityViewModel = koinViewModel()) {
    Column(modifier = Modifier.fillMaxSize()) {
        Button(onClick = {
            viewModel.fetchData()
        }) {
            Text(text = "点击获取")
        }

        Divider(thickness = 1.dp, color = Color.Black)

        Text(text = viewModel.state.value)
    }

}