package com.slakra.data.repository

import com.slakra.common.utils.CoroutineContextProvider
import com.slakra.data.local.NewsArticleDao
import com.slakra.data.mapper.NewsArticleMapper
import com.slakra.data.model.NewsArticleModel
import com.slakra.data.remote.NewsArticleService
import com.slakra.domain.arch.ResultState
import com.slakra.domain.entity.ArticleEntity
import com.slakra.domain.repository.ArticleRepo
import kotlinx.coroutines.withContext

class ArticleRepositoryImpl(
        private val remote: NewsArticleService,
        private val newsArticleDao: NewsArticleDao,
        private val articleMapper: NewsArticleMapper,
        private val contextProvider: CoroutineContextProvider) : ArticleRepo {

    override suspend fun getNewsArticles(): ResultState<List<ArticleEntity>> =
            withContext(contextProvider.IO) {
                try {
                    val response = remote.getNewsArticles()

                    if (response.isSuccessful) {
                        val body = response.body()
                        if (body != null) {
                            saveNewsArticle(body)
                            ResultState.Success(body.map {
                                articleMapper.map(it)
                            })
                        } else {
                            ResultState.InvalidData
                        }
                    } else {
                        when (response.code()) {
                            403 -> ResultState.HttpErrors.ResourceForbidden(response.message())
                            404 -> ResultState.HttpErrors.ResourceNotFound(response.message())
                            500 -> ResultState.HttpErrors.InternalServerError(response.message())
                            502 -> ResultState.HttpErrors.BadGateWay(response.message())
                            301 -> ResultState.HttpErrors.ResourceRemoved(response.message())
                            302 -> ResultState.HttpErrors.RemovedResourceFound(response.message())
                            else -> ResultState.Error(response.message())
                        }
                    }
                } catch (ex: Exception) {
                    ResultState.NetworkException(ex.localizedMessage!!)
                }
            }

    private fun saveNewsArticle(articleList: List<NewsArticleModel>) {
        newsArticleDao.insertAllWithReplaceStrategy(articleList)
    }
}