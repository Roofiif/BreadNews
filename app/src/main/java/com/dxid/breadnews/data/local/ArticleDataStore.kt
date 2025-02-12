package com.dxid.breadnews.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.dxid.breadnews.data.models.ArticleStore
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


private val Context.dataStore by preferencesDataStore(name = "saved_articles")

class ArticleDataStore @Inject constructor (@ApplicationContext private val context: Context) {
    companion object{
        private val SAVED_ARTICLES_KEY = stringSetPreferencesKey("saved_articles")
    }

    private val gson = Gson()

    suspend fun saveArticle(article: ArticleStore) {
        context.dataStore.edit { preferences ->
            val currentJsonSet = preferences[SAVED_ARTICLES_KEY] ?: emptySet()
            val currentArticles = currentJsonSet.map { json ->
                gson.fromJson(json, ArticleStore::class.java)
            }

            if (currentArticles.none { it.url == article.url }){
                val updatedJsonSet = currentJsonSet + gson.toJson(article)
                preferences[SAVED_ARTICLES_KEY] = updatedJsonSet
            }
        }
    }

    fun getArticles(): Flow<List<ArticleStore>> {
        return context.dataStore.data.map { preferences ->
            val jsonSet = preferences[SAVED_ARTICLES_KEY] ?: emptySet()
            jsonSet.map { json ->
                gson.fromJson(json, ArticleStore::class.java)
            }
        }
    }
}