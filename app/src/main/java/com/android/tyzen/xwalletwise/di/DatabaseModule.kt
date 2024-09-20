package com.android.tyzen.xwalletwise.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.android.tyzen.xwalletwise.model.transactionDB.TransactionDB
import com.android.tyzen.xwalletwise.model.user.UserProfileDB
import com.android.tyzen.xwalletwise.repository.authentication.UserProfileRepository
import com.android.tyzen.xwalletwise.repository.transaction.TransactionWithCategoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideTransactionDB(@ApplicationContext context: Context): TransactionDB {
        return TransactionDB.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideUserProfileDB(@ApplicationContext context: Context): UserProfileDB {
        return UserProfileDB.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideTransactionWithCategoryRepository(
        transactionDB: TransactionDB
    ): TransactionWithCategoryRepository {
        return TransactionWithCategoryRepository(
            transactionDao = transactionDB.transactionDao(),
            categoryDao = transactionDB.categoryDao(),
            transactionWithCategoryDao = transactionDB.transactionWithCategoryDao()
        )
    }

    @Provides
    @Singleton
    fun provideUserProfileRepository(
        userProfileDB: UserProfileDB
    ): UserProfileRepository {
        return UserProfileRepository(
            userProfileDao = userProfileDB.userProfileDao()
        )
    }
}
