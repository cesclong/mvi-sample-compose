package com.cn.featurewanandroidhome.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch


@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreen(navController: NavController, homeViewModel: HomeScreenViewModel = viewModel()) {
    val scopeState = rememberCoroutineScope()
    val titles = homeViewModel.uiStates.titles
    Column {
        val pagerState = rememberPagerState(initialPage = 0)

        HomeTabBarView(pagerState.currentPage, titles) { index ->
            scopeState.launch {
                pagerState.animateScrollToPage(index)
            }
        }

        HorizontalPager(
            count = titles.size,
            state = pagerState,
            modifier = Modifier.padding(bottom = 50.dp)
        ) { page ->
            when (page) {
                0 -> SquareScreen(navController = navController)
                1 -> RecommendScreen(navController = navController)
                2 -> QuestionScreen(navController = navController)
            }

        }

    }
}


@Composable
internal fun HomeTabBarView(
    index: Int,
    tabList: List<TabTitle>,
    contentAlign: Alignment = Alignment.Center,
    contentColor: Color = Color.White,
    onTabSelect: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp)
            .background(color = Color(0XFF3489FF))
            .horizontalScroll(state = rememberScrollState())
    ) {
        Row(modifier = Modifier.align(contentAlign)) {
            tabList.forEachIndexed { i, tabTitle ->
                Text(
                    text = tabTitle.text,
                    fontSize = if (index == i) 20.sp else 15.sp,
                    fontWeight = if (index == i) FontWeight.Bold else FontWeight.Normal,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(horizontal = 10.dp)
                        .clickable { onTabSelect.invoke(i) },
                    color = contentColor
                )
            }
        }

    }

}
