package com.android.tyzen.xwalletwise.model.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserProfileDao {
    //Get User Profle
    @Query("""
        SELECT *
        FROM user
        LIMIT 1
    """)
    fun getUserProfile(): Flow<UserProfile>

    //Update and Insert User Profile
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertUserProfile(userProfile: UserProfile)

    //Delete User Profile
    @Query("""
        DELETE FROM user
    """)
    suspend fun deleteUserProfile()
}