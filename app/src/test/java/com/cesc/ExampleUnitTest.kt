package com.cesc

import com.cesc.data.RepositoryImpl
import com.cesc.data.model.CityInfo
import com.cesc.data.model.WeatherResponse
import com.cesc.data.network.ApiService
import com.cesc.domain.Repository
import com.cesc.domain.UseCase
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class ExampleUnitTest {
    private val api: ApiService = mockk(relaxed = true)
    private val repository: Repository = RepositoryImpl(api)
    private val useCase: UseCase = UseCase(repository)

    @Before
    fun setup() {
    }

    @Test
    fun `api service test get weather`() = runTest {
        val response = getResponse()
        coEvery { api.getWeather("") } returns response

        val result = api.getWeather("")

        assertEquals("大连市", result.cityInfo.city)
        assertEquals(200, result.status)
    }

    @Test
    fun `repository test get weather`() = runTest {
        val response = getResponse()
        coEvery { api.getWeather("") } returns response

        val result = repository.getWeather("")
        assertEquals(200, result.status.code)
        assertEquals("大连市", result.cityInfo.city)
    }

    @Test
    fun `usecase test get weather`() = runTest {
        val response = getResponse()
        coEvery { api.getWeather("") } returns response

        val result = useCase.getWeather("")
        assertEquals(200, result.status.code)
        assertEquals("大连市", result.cityInfo.city)
    }

    private fun getResponse() = WeatherResponse(
        status = 200,
        message = "success感谢又拍云(upyun.com)提供CDN赞助",
        date = "20221026",
        time = "2022-10-26 23:08:55",
        cityInfo = CityInfo(
            city = "大连市",
            citykey = "101070201",
            parent = "辽宁",
            updateTime = "19:31"
        )
    )
}
