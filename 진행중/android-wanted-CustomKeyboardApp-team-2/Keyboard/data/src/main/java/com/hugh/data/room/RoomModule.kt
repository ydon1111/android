package com.hugh.data.room

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): KeyboardDatabase {
        return Room.databaseBuilder(
            context,
            KeyboardDatabase::class.java,
            "KeyboardRoomDatabase"
        )
            .build()
    }

}