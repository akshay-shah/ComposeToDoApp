package com.example.composetodoapp.di

import android.content.Context
import androidx.room.Room
import com.example.composetodoapp.data.database.ToDoDatabase
import com.example.composetodoapp.utils.Constants.DATABASE_NAME
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            ToDoDatabase::class.java,
            DATABASE_NAME
        )

    @Singleton
    @Provides
    fun provideDao(database: ToDoDatabase) = database.getToDoDao()

}