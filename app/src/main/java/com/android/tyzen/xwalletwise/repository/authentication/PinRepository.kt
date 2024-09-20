package com.android.tyzen.xwalletwise.repository.authentication

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import java.security.MessageDigest

class PinRepository(context: Context)
{
    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
    private val sharedPrefs = EncryptedSharedPreferences.create(
        "prefsPin",
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    /**
     * Save/Edit pin to shared preferences
     */
    fun savePin(pin: String) {
        sharedPrefs.edit().putString("pin", hashPin(pin)).apply()
    }

    /**
     * Get pin from shared preferences
     */
    fun getPin(): String? {
        return sharedPrefs.getString("pin", null)
    }

    /**
     * Delete pin from shared preferences
     */
    fun deletePin() {
        sharedPrefs.edit().remove("pin").apply()
    }

    //Pin Hash Function
    fun hashPin(pin: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val salt = "morebe_5"
        digest.update(salt.toByteArray())
        val hash = digest.digest(pin.toByteArray())
        return hash.joinToString("") { String.format("%02x", it) }
    }
}