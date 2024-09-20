package com.android.tyzen.xwalletwise.model.user

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * USER ENTITY
 */
@Entity(tableName = "user")
data class UserProfile(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    //
    val name: String,
    val gender: Int,
    val age: Int = 0,
    val phoneNumber: String = "",
    val currency: String = "",
    val currencyPosition: Boolean = false,
)
