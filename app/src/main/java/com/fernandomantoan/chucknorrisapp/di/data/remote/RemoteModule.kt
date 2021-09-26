package com.fernandomantoan.chucknorrisapp.di.data.remote

import com.fernandomantoan.chucknorrisapp.BuildConfig
import com.fernandomantoan.chucknorrisapp.data.remote.ChuckNorrisService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RemoteModule {
    @Provides
    @Singleton
    fun provideRetrofit(moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.REMOTE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideChuckNorrisService(retrofit: Retrofit): ChuckNorrisService {
        return retrofit.create(ChuckNorrisService::class.java)
    }
}