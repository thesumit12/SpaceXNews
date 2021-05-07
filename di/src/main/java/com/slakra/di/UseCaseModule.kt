package com.slakra.di

import com.slakra.domain.usecase.GetNewsArticleFromServerUseCase
import com.slakra.domain.usecase.GetSearchResultsUseCase
import org.koin.dsl.module

internal val useCaseModule = module {
    factory { GetNewsArticleFromServerUseCase(articleRepo = get()) }
    factory { GetSearchResultsUseCase(newsArticleStore = get()) }
}