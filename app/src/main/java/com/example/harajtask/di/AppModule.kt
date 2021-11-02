package com.example.harajtask.di

import android.content.Context
import com.example.harajtask.data.HarajReop
import com.example.harajtask.data.HarajReopImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRep(@ApplicationContext context: Context)=HarajReopImpl(context)
}