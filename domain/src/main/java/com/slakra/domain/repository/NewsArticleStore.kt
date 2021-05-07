package com.slakra.domain.repository

import com.slakra.domain.entity.ArticleEntity
import kotlinx.coroutines.flow.Flow

interface NewsArticleStore {

    fun getLatestSearchResults(input: String): Flow<List<ArticleEntity>>

}