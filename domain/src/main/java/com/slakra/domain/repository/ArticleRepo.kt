package com.slakra.domain.repository

import com.slakra.domain.arch.ResultState
import com.slakra.domain.entity.ArticleEntity

interface ArticleRepo {

    suspend fun getNewsArticles(): ResultState<List<ArticleEntity>>
}