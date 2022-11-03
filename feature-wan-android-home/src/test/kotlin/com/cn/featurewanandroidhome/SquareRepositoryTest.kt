package com.cn.featurewanandroidhome

import com.cesc.commonmodel.BaseResponse
import com.cn.featurewanandroidhome.data.SquareRepositoryImpl
import com.cn.featurewanandroidhome.data.model.SquareDataResponse
import com.cn.featurewanandroidhome.data.source.ApiService
import com.google.gson.Gson
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.FileInputStream
import java.util.Scanner
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class SquareRepositoryTest {
    private val apiService: ApiService = mockk(relaxed = true)


    @Before
    fun setup() {
        clearMocks(apiService)

        val dataPath = "src/test/resources/square_response.json"
        val dataResponseSp = Gson().fromJson(
            Scanner(FileInputStream(dataPath)).useDelimiter("\\Z").next(),
            SquareDataResponse::class.java
        )


        coEvery { apiService.getSquareArticles(any()) } returns dataResponseSp
    }

    @After
    fun tearDown() {

    }

    @Test
    fun `get square data`() = runTest{
        val result = apiService.getSquareArticles(0)

        assertEquals(0, result.code)
        assertEquals("广场", result.data.datas.get(0).chapterName)
    }


}