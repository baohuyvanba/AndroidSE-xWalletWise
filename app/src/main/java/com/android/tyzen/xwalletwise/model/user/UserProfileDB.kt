package com.android.tyzen.xwalletwise.model.user

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * USER PROFILE DATABASE IMPLEMENTATION
 */
@Database(
    entities = [UserProfile::class],
    version = 1,
    exportSchema = false,
)
abstract class UserProfileDB: RoomDatabase()
{
    abstract fun userProfileDao(): UserProfileDao

    companion object {
        @Volatile
        var INSTANCE: UserProfileDB? = null
        fun getDatabase(context: Context) : UserProfileDB {
            return INSTANCE ?: synchronized(this)    //Only 1 thread able to access at a time
            {
                val instance = Room.databaseBuilder(
                    context,
                    UserProfileDB::class.java,
                    "user_profile_database",
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
