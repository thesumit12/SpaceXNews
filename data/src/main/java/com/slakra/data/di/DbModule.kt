package com.slakra.data.di

import com.slakra.data.local.AppDatabase
import com.slakra.domain.repository.NewsArticleStore
import com.slakra.data.repository.NewsArticleStoreImpl
import org.koin.dsl.module

internal val dbModule = module {
    single { AppDatabase(get()) }
    factory { getNewsArticleDao(get()) }
    factory<NewsArticleStore> { NewsArticleStoreImpl(get(), get()) }
}

fun getNewsArticleDao(appDatabase: AppDatabase) = appDatabase.newsArticleDao()