package com.dxid.breadnews.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dxid.breadnews.data.models.Article
import com.dxid.breadnews.data.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetNewsUseCase (private val repository: NewsRepository) {
    operator fun invoke(): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(
                pageSize = 50,
                prefetchDistance = 10,
                enablePlaceholders = false,
                initialLoadSize = 50
            ),
            pagingSourceFactory = { repository.getNewsPagingSource() }
        ).flow
    }
}