package com.slakra.data.remote

import com.slakra.data.model.NewsArticleModel
import retrofit2.Response
import retrofit2.http.GET

interface NewsArticleService {

    @GET("api/v2/articles")
    suspend fun getNewsArticles(): Response<List<NewsArticleModel>>
}