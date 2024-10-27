package com.arvind.timeslotmusic.di

import com.arvind.timeslotmusic.domain.usecase.TimeSlotUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTimeSlotUseCase(): TimeSlotUseCase {
        return TimeSlotUseCase()
    }
}
