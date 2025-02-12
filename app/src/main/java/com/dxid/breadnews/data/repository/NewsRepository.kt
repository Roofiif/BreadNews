package com.dxid.breadnews.data.repository


import androidx.paging.PagingSource
import com.dxid.breadnews.data.models.Article
import com.dxid.breadnews.data.paging.NewsPaging
import com.dxid.breadnews.data.remote.ApiService

class NewsRepository (private val apiService: ApiService) {
    fun getNewsPagingSource(): PagingSource<Int, Article> {
        return NewsPaging(apiService)
    }
}