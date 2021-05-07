package com.slakra.data.di

import com.slakra.data.repository.ArticleRepositoryImpl
import com.slakra.domain.repository.ArticleRepo
import org.koin.dsl.module

internal val repositoryModule = module {

    factory<ArticleRepo> { ArticleRepositoryImpl(remote = get(), newsArticleDao = get(),
            articleMapper = get(), contextProvider = get()) }
}