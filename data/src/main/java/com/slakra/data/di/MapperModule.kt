package com.slakra.data.di

import com.slakra.data.mapper.NewsArticleMapper
import org.koin.dsl.module

internal val mapperModule = module {

    factory { NewsArticleMapper() }
}