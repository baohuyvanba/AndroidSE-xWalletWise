package com.android.tyzen.xwalletwise.di

import android.content.Context
import com.android.tyzen.xwalletwise.repository.authentication.PinRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ViewModelModule {
    @Provides
    fun providePinRepository(@ApplicationContext context: Context): PinRepository {
        return PinRepository(context)
    }
}