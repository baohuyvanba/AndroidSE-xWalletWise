package com.android.tyzen.xwalletwise.repository.authentication

import com.android.tyzen.xwalletwise.model.user.UserProfile
import com.android.tyzen.xwalletwise.model.user.UserProfileDao
import kotlinx.coroutines.flow.Flow

/**
 * Repository for user profile
 */
class UserProfileRepository(
    private val userProfileDao: UserProfileDao, )
{
    //Get
    val userProfile: Flow<UserProfile?> = userProfileDao.getUserProfile()

    //Update & Insert
    suspend fun upsertUserProfile(userProfile: UserProfile) =
        userProfileDao.upsertUserProfile(userProfile)

    //Delete
    suspend fun deleteUserProfile() =
        userProfileDao.deleteUserProfile()
}


///**
// * Resource Class to manage the state of a resource
// */
//sealed class Resource<T>(
//    val data:T? = null,
//    val throwable: Throwable? = null, )
//{
//    class Loading<T>: Resource<T>()
//    class Success<T>(data: T?): Resource<T>(data = data)
//    class Error<T>(throwable: Throwable?): Resource<T>(throwable = throwable)
//}