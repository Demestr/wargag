package com.lipssoftware.wargag.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.lipssoftware.wargag.data.local.WargagDao
import com.lipssoftware.wargag.data.local.WargagDatabase
import com.lipssoftware.wargag.data.network.WargagApi
import com.lipssoftware.wargag.data.repository.WargagRepository
import dagger.BindsInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson) : Retrofit = Retrofit.Builder()
        .baseUrl("https://api.worldoftanks.ru/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideWargagApi(retrofit: Retrofit): WargagApi = retrofit.create(WargagApi::class.java)

    @Singleton
    @Provides
    fun provideRepository(@ApplicationContext context: Context, remoteDataSource: WargagApi, wargagDao: WargagDao) = WargagRepository(context, remoteDataSource, wargagDao)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): WargagDatabase{
        return WargagDatabase.getInstance(context)
    }

    @Singleton
    @Provides
    fun provideWargagDao(database: WargagDatabase): WargagDao{
        return database.getDao()
    }
}