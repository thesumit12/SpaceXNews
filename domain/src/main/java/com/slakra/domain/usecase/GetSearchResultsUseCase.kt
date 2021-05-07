package com.slakra.domain.usecase

import com.slakra.domain.entity.ArticleEntity
import com.slakra.domain.repository.NewsArticleStore
import kotlinx.coroutines.flow.Flow

class GetSearchResultsUseCase(private val newsArticleStore: NewsArticleStore) {

    fun execute(input: String): Flow<List<ArticleEntity>> {
        return newsArticleStore.getLatestSearchResults(input)
    }
}