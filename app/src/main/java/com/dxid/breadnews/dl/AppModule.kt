package com.dxid.breadnews.dl

import android.content.Context
import com.dxid.breadnews.BuildConfig
import com.dxid.breadnews.data.local.ArticleDataStore
import com.dxid.breadnews.data.paging.NewsPaging
import com.dxid.breadnews.data.remote.ApiService
import com.dxid.breadnews.data.repository.NewsRepository
import com.dxid.breadnews.domain.usecase.GetNewsUseCase
import com.dxid.breadnews.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideApiService(

    ): ApiService {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .addInterceptor { chain ->
                        val token = BuildConfig.NEWS_TOKEN
                        val request = chain.request().newBuilder()
                            .header("Authorization", token)
                            .build()
                        chain.proceed(request)
                    }
                    .addInterceptor(loggingInterceptor)
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun providesNewsRepository(
        apiService: ApiService,
    ): NewsRepository {
        return NewsRepository(
            apiService = apiService,
        )
    }

    @Provides
    @Singleton
    fun providesNewsUseCase(repository: NewsRepository): GetNewsUseCase {
        return GetNewsUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesNewsPaging(apiService: ApiService): NewsPaging {
        return NewsPaging(apiService)
    }

    @Provides
    @Singleton
    fun providesArticleDataStore(@ApplicationContext context: Context): ArticleDataStore {
        return ArticleDataStore(context)
    }
}