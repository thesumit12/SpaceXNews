package com.slakra.di

import com.slakra.data.di.loadDataModules
import org.koin.core.context.loadKoinModules

fun initKoin() {
    loadKoinModules(listOf(useCaseModule))
}

fun loadDataModules() {
    loadDataModules()
}