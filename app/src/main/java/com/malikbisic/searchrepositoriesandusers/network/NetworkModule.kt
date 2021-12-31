package com.malikbisic.searchrepositoriesandusers.network

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideHttpLoggerInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level= HttpLoggingInterceptor.Level.BODY
        }
    }

    @Singleton
    @Provides
    fun provideCallFactory(httpLoggingInterceptor: HttpLoggingInterceptor): Call.Factory {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder().build()
    }

    @Singleton
    @Provides
    fun provideMoshiConverter(): MoshiConverterFactory {
        return MoshiConverterFactory.create()
    }

    @Singleton
    @Provides
    fun provideRxJava3AdapterFactory(): RxJava3CallAdapterFactory {
        return RxJava3CallAdapterFactory.create()
    }

    @Singleton
    @Provides
    fun provideBaseUrl(): String {
        return "https://api.github.com/"
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        callFactory: Call.Factory,
        moshiConverterFactory: MoshiConverterFactory,
        rxJava3CallAdapterFactory: RxJava3CallAdapterFactory,
        baseUrl: String
    ): Retrofit {
        return Retrofit.Builder()
            .callFactory(callFactory)
            .addCallAdapterFactory(rxJava3CallAdapterFactory)
            .baseUrl(baseUrl)
            .addConverterFactory(moshiConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideGitHubService(retrofit: Retrofit): GitHubService {
        return retrofit.create(GitHubService::class.java)
    }
}