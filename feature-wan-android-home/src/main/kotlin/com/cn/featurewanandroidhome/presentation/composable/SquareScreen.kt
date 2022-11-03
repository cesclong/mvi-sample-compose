package com.cn.featurewanandroidhome.presentation.composable

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.cesc.commonmodel.Article
import org.jetbrains.annotations.NotNull
import org.koin.androidx.compose.koinViewModel


@Composable
internal fun SquareScreen(
    navController: NavController,
    viewModel: SquareViewModel = koinViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    DisposableEffect(key1 = Unit) {
        viewModel.sendIntent(SquareIntent.Fetch(0))
        onDispose { }
    }

    when {
        uiState.isLoading -> {
            LoadView()
        }

        uiState.isError -> ErrorView()

        uiState.datas.isNotEmpty() -> {
            SquareView(uiState.datas)
        }
    }

}

@Composable
internal fun LoadView() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center)
        )
    }

}

@Composable
internal fun ErrorView(msg: String = "error") {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = msg,
            modifier = Modifier.align(Alignment.Center)
        )
    }

}

@Composable
internal fun SquareView(articles: List<Article>) {
    LazyColumn {

        items(articles) {
            SquareItemView(article = it)
        }
    }

}

@Composable
internal fun SquareItemView(article: Article) {

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


@Composable
fun LabelTextButton(
    text: String,
    modifier: Modifier = Modifier,
    cornerValue: Dp = 25.dp / 2,
    onClick: (() -> Unit)? = null
) {
    Text(
        text = text,
        modifier = modifier
            .height(25.dp)
            .clip(shape = RoundedCornerShape(cornerValue))
            .background(
                color = Color(0XFF3489FF)
            )
            .padding(
                horizontal = 10.dp,
                vertical = 3.dp
            )
            .clickable {
                onClick?.invoke()
            },
        fontSize = 13.sp,
        textAlign = TextAlign.Center,
        color = Color.White,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1
    )
}
