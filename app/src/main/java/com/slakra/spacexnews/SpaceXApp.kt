package com.slakra.spacexnews

import android.app.Application
import android.content.Context
import com.slakra.di.initKoin
import com.slakra.di.loadDataModules
import com.slakra.spacexnews.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SpaceXApp: Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        startKoin {
            androidContext(this@SpaceXApp)
            modules(mutableListOf(viewModelModule))
        }
    }

    override fun onCreate() {
        super.onCreate()
        initKoin()
        loadDataModules()
    }
}