package com.slakra.data.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.slakra.common.BuildConfig
import com.slakra.common.utils.AppConstants
import com.slakra.data.remote.NewsArticleService
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val CACHE_SIZE = 10 * 1024 * 1024
private const val BASE_URL = "https://test.spaceflightnewsapi.net/"

val retrofitModule = module {
    single { createOkHttpClient(androidContext()) }
    single { createGsonConverter() }
    single { createRetrofitBuilder(get(), get()) }

    single { createApiService(get(), BASE_URL) }
}

fun createOkHttpClient(context: Context): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
    val clientBuilder = OkHttpClient.Builder()
    clientBuilder.connectTimeout(AppConstants.TIME_OUT, TimeUnit.SECONDS)
    clientBuilder.readTimeout(AppConstants.TIME_OUT, TimeUnit.SECONDS)
    clientBuilder.writeTimeout(AppConstants.TIME_OUT, TimeUnit.SECONDS)

    // Deciding cache size and where to keep it
    val cache = Cache(context.cacheDir, CACHE_SIZE.toLong())
    clientBuilder.cache(cache)
    if (BuildConfig.DEBUG) {
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        clientBuilder.addInterceptor(httpLoggingInterceptor)
    }
    return clientBuilder.build()
}

fun createGsonConverter(): Gson = GsonBuilder().setLenient().create()

fun createRetrofitBuilder(okHttpClient: OkHttpClient, gson: Gson): Retrofit.Builder =
    Retrofit.Builder().client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())

fun createApiService(builder: Retrofit.Builder, baseUrl: String): NewsArticleService =
    builder.baseUrl(baseUrl).build().create(NewsArticleService::class.java)
