package com.cn.featurewanandroidhome.presentation.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.cesc.commonmodel.Article
import com.cesc.commonui.ShowErrorView
import com.cesc.commonui.ShowLoadingView
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun QuestionScreen(
    navController: NavController,
    viewModel: QuestionViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = "Init") {
        viewModel.sendIntent(QuestionIntent.Fetch(0))
    }
    Box(modifier = Modifier.fillMaxSize()) {

        if (uiState.list.isNotEmpty()) {

            LazyColumn {

                items(uiState.list) { item ->
                    QuestionItemView(item)
                }
            }
        }

        ShowLoadingView(isShow = uiState.isLoading)
        ShowErrorView(isShow = uiState.isError, msg = uiState.errorMessage)

    }
}

@Composable
internal fun QuestionItemView(article: Article) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .padding(10.dp), shape = RoundedCornerShape(10.dp),
        elevation = 5.dp,
        backgroundColor = MaterialTheme.colors.primary
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    text = article.shareUser,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .wrapContentHeight()
                )

                Text(
                    text = article.niceDate,
                    fontSize = 14.sp
                )
            }

            Text(text = article.title, fontSize = 18.sp)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)

            ) {
                LabelTextButton(text = article.chapterName)
                LabelTextButton(text = article.superChapterName)

            }

        }

    }
}