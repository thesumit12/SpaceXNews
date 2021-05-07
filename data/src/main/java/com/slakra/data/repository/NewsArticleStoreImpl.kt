package com.slakra.data.repository

import com.slakra.data.local.NewsArticleDao
import com.slakra.data.mapper.NewsArticleMapper
import com.slakra.domain.entity.ArticleEntity
import com.slakra.domain.repository.NewsArticleStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NewsArticleStoreImpl(
        private val newsArticleDao: NewsArticleDao,
        private val mapper: NewsArticleMapper): NewsArticleStore {

    override fun getLatestSearchResults(input: String): Flow<List<ArticleEntity>> {
        return newsArticleDao.getSearchResults(input).map { list->
            list.map { newsArticleModel ->
                mapper.map(newsArticleModel)
            }
        }
    }
}