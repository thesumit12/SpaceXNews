package com.slakra.spacexnews.di

import com.slakra.spacexnews.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val viewModelModule = module {

    viewModel { MainViewModel(get(), get(), get()) }
}