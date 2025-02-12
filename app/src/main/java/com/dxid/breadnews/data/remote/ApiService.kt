package com.dxid.breadnews.data.remote

import com.dxid.breadnews.data.models.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String? = "us",
        @Query("pageSize") pageSize: Int,
        @Query("page") page: Int
    ): NewsResponse
}