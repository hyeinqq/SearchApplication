package com.example.hyein.searchapplication

import com.example.hyein.searchapplication.database.SearchLocalCache
import com.example.hyein.searchapplication.repository.ItemRepository
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.BufferedSource
import okio.Okio
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WebserviceTest {
    lateinit var mockWebServer: MockWebServer
    lateinit var webService: WebService

    @Before
    fun setUp(){
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val baseUrl = mockWebServer.url("/")
        val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        webService = retrofit.create(WebService::class.java)
    }

    @Test
    fun getItems(){
        mockWebServer.enqueue(MockResponse().setBody(getResponse("item.json")))

        val call = webService.getItems()
        val response = call.execute()

        assert(response.code() == 200)


    }

    private fun getResponse(fileName: String): String{
        val inputStream = javaClass.classLoader.getResourceAsStream("api-response/$fileName")
        val bufferedSource = Okio.buffer(Okio.source(inputStream))
        return  bufferedSource.readUtf8()
    }
}