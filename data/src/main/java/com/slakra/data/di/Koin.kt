package com.slakra.data.di

import org.koin.core.context.loadKoinModules

fun loadDataModules() {
    loadKoinModules(listOf(retrofitModule, mapperModule, coroutineContextProviderModule, repositoryModule, dbModule))
}