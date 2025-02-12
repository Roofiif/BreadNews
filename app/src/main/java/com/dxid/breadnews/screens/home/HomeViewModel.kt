package com.dxid.breadnews.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.dxid.breadnews.data.local.ArticleDataStore
import com.dxid.breadnews.data.models.ArticleStore
import com.dxid.breadnews.domain.usecase.GetNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase,
    private val articleDataStore: ArticleDataStore
): ViewModel() {
    private val _transactionError = MutableStateFlow<String?>(null)
    val transactionError: StateFlow<String?> = _transactionError

    val newsPagingFlow = getNewsUseCase()
        .cachedIn(viewModelScope)

    val getArticles: StateFlow<List<ArticleStore>> = articleDataStore
        .getArticles()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    fun saveArticle(article: ArticleStore) {
        viewModelScope.launch {
            articleDataStore.saveArticle(article)
        }
    }
}
