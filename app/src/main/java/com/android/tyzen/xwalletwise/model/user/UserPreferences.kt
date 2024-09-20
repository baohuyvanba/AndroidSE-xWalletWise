package com.android.tyzen.xwalletwise.model.user

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton


private val Context.dataStore by preferencesDataStore("user_preferences")

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }
}

@HiltViewModel
class UserPreferences @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : ViewModel() {
    companion object {
        //App Preferences
        val FIRST_TIME_KEY = booleanPreferencesKey("first_time_launch")
        //User Preferences
        val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")
        val LANGUAGE_KEY = stringPreferencesKey("language")
        val BIOMETRICS_KEY = booleanPreferencesKey("biometrics")
        //Related to App's Functionalities
        val CURRENCY_KEY = stringPreferencesKey("currency")
    }

    /**
     * APP PREFERENCES =============================================================================
     */

    //FIRST TIME preference ------------------------------------------------------------------------
    // Check if it's the first time launching the app
    val isFirstTimeLaunch: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[FIRST_TIME_KEY] ?: true
    }

    fun setFirstTimeLaunch(isFirstTime: Boolean) {
        viewModelScope.launch {
            dataStore.edit { preferences ->
                preferences[FIRST_TIME_KEY] = isFirstTime
            }
        }
    }

    /**
     * USER PREFERENCES ============================================================================
     */

    //DARK MODE preference -------------------------------------------------------------------------
    val isDarkModeEnabled: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[DARK_MODE_KEY] ?: false
    }

    fun setDarkModeEnabled(isEnabled: Boolean) {
        viewModelScope.launch {
            dataStore.edit { preferences ->
                preferences[DARK_MODE_KEY] = isEnabled
            }
        }
    }

    //LANGUAGES preference -------------------------------------------------------------------------
    val language: Flow<String> = dataStore.data.map { preferences ->
        preferences[LANGUAGE_KEY] ?: "en"
    }

    fun setLanguage(language: String) {
        viewModelScope.launch {
            dataStore.edit { preferences ->
                preferences[LANGUAGE_KEY] = language
            }
        }
    }

    //BIOMETRICS preference ------------------------------------------------------------------------
    val isBiometricsEnabled: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[BIOMETRICS_KEY] ?: false
    }

    fun setBiometricsEnabled(isEnabled: Boolean) {
        viewModelScope.launch {
            dataStore.edit { preferences ->
                preferences[BIOMETRICS_KEY] = isEnabled
            }
        }
    }

    /**
     * APP FUNCTIONALITIES =========================================================================
     */

    //CURRENCY preference --------------------------------------------------------------------------
    val currency: Flow<String> = dataStore.data.map { preferences ->
        preferences[CURRENCY_KEY] ?: "USD"
    }

    fun setCurrency(currency: String) {
        viewModelScope.launch {
            dataStore.edit { preferences ->
                preferences[CURRENCY_KEY] = currency
            }
        }
    }
}

/**
 * FIRST TIME LAUNCH ===============================================================================
 */
fun isFirstTimeLaunch(context: Context): Boolean {
    val sharedPrefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    return sharedPrefs.getBoolean("is_first_time_launch", true)
}

fun setFirstTimeLaunch(context: Context, isFirstTime: Boolean) {
    val sharedPrefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    with(sharedPrefs.edit()) {
        putBoolean("is_first_time_launch", isFirstTime)
        apply()
    }
}