package com.android.tyzen.xwalletwise.ui.viewmodel.user

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.tyzen.xwalletwise.model.user.UserProfile
import com.android.tyzen.xwalletwise.repository.authentication.UserProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

data class UserProfileUiState(
    val name: String = "",
    val gender: Int = 0,
    val age: Int = 0,
    val phoneNumber: String = "",
    val currency: String = "",
    val currencyPosition: Boolean = false,

    val isViewing: Boolean = false,
    val isUpdating: Boolean = false,
    val isDeleting: Boolean = false,
)

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val userProfileRepository: UserProfileRepository
) : ViewModel() {
    var userProfileUiState by mutableStateOf(UserProfileUiState())
        private set

    //INIT
    init {
        getUserProfile()
    }

    private fun getUserProfile() {
        viewModelScope.launch {
            userProfileRepository.userProfile.collectLatest { userProfile ->
                if (userProfile != null) {
                    userProfileUiState = userProfileUiState.copy(
                        name = userProfile.name,
                        gender = userProfile.gender,
                        age = userProfile.age,
                        phoneNumber = userProfile.phoneNumber,
                        currency = userProfile.currency,
                        currencyPosition = userProfile.currencyPosition,
                        isViewing = true,
                    )
                } else {
                    userProfileUiState = userProfileUiState.copy(
                        isViewing = false,
                    )
                }
            }
        }
    }

    /**
     * On change functions -------------------------------------------------------------------------
     */
    //Username
    fun onFullNameChanged(fullName: String) {
        userProfileUiState = userProfileUiState.copy(name = fullName)
    }
    //Gender
    fun onGenderChanged(gender: Int) {
        userProfileUiState = userProfileUiState.copy(gender = gender)
    }
    //Age
    fun onAgeChanged(age: Int) {
        userProfileUiState = userProfileUiState.copy(age = age)
    }
    //Phone Number
    fun onPhoneNumberChanged(phoneNumber: String) {
        userProfileUiState = userProfileUiState.copy(phoneNumber = phoneNumber)
    }
    //Currency & Currency Position
    fun onCurrencyChanged(currency: String, currencyPosition: Boolean) {
        userProfileUiState = userProfileUiState.copy(currency = currency)
        userProfileUiState = userProfileUiState.copy(currencyPosition = currencyPosition)
    }

    /**
     * Update/Insert User Profile function
     */
    fun upsertUserProfile() {
        viewModelScope.launch {
            userProfileRepository.upsertUserProfile(
                UserProfile(
                    name = userProfileUiState.name,
                    gender = userProfileUiState.gender,
                    age = userProfileUiState.age,
                    phoneNumber = userProfileUiState.phoneNumber,
                    currency = userProfileUiState.currency,
                    currencyPosition = userProfileUiState.currencyPosition,
                )
            )
        }
    }

    /**
     * Delete User Profile function
     */
    fun deleteUserProfile() {
        viewModelScope.launch {
            userProfileRepository.deleteUserProfile()
        }
    }
}