package com.slakra.data.di

import com.slakra.common.utils.CoroutineContextProvider
import org.koin.dsl.module

internal val coroutineContextProviderModule = module {
    single { CoroutineContextProvider() }
}