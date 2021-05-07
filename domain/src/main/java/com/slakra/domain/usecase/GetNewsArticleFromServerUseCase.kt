package com.slakra.domain.usecase

import com.slakra.domain.arch.BaseUseCase
import com.slakra.domain.arch.ResultState
import com.slakra.domain.entity.ArticleEntity
import com.slakra.domain.repository.ArticleRepo

class GetNewsArticleFromServerUseCase(private val articleRepo: ArticleRepo):
    BaseUseCase<List<ArticleEntity>> {

    override suspend fun execute(): ResultState<List<ArticleEntity>> {
        return articleRepo.getNewsArticles()
    }


}