package com.weather.freeweatherapp.presentation.di

import android.content.Context
import androidx.room.Room
import com.weather.freeweatherapp.data.db.AppDatabase
import com.weather.freeweatherapp.data.db.DefaultLocationDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DBModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "db"
        ).build()
    }

    @Provides
    @Singleton
    fun providesDBDAO(database: AppDatabase): DefaultLocationDAO{
        return database.defaultLocationDAO()
    }


}