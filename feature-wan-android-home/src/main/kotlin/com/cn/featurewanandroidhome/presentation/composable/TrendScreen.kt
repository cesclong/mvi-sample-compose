package com.cn.featurewanandroidhome.presentation.composable

import android.content.Context
import android.util.Log
import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import com.cesc.commonmodel.Article
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel


@Composable
internal fun TrendScreen(
    navController: NavController,
    viewModel: TrendViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        viewModel.event.collectLatest {
            when (it) {
                is TrendEvent.ShowToast -> trendShowToast(context, it.msg)
            }
        }
    }

    //生命周期state
    val lifecycleEventState by LocalLifecycleOwner.current.lifecycle.observeAsState()

    if (lifecycleEventState == Lifecycle.Event.ON_RESUME){
        Log.e("Trend", "Resume")
    }

    DisposableEffect(key1 = "Init") {
        viewModel.sendIntent(TrendIntent.Init)
        onDispose { }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (uiState.data.isNotEmpty()) {
            LazyColumn {
                items(uiState.data) {
                    TrendItemView(it)
                }
            }
        }

        if (uiState.isLoading) {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                    Text(text = "Loading....")
                }
            }
        }

        if (uiState.isError) {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(text = uiState.errorMessage)
            }
        }


    }
}


@Composable
internal fun TrendItemView(article: Article) {

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

private fun trendShowToast(context: Context, msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}

@Composable
internal fun Lifecycle.observeAsState(): State<Lifecycle.Event> {
    val state = remember { mutableStateOf(Lifecycle.Event.ON_ANY) }
    DisposableEffect(this) {
        val observer = LifecycleEventObserver { _, event ->
            state.value = event
        }
        this@observeAsState.addObserver(observer)
        onDispose {
            this@observeAsState.removeObserver(observer)
        }
    }
    return state
}