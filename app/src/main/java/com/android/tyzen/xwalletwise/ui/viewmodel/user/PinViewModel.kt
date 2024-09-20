package com.android.tyzen.xwalletwise.ui.viewmodel.user

import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.tyzen.xwalletwise.model.user.UserPreferences
import com.android.tyzen.xwalletwise.repository.authentication.PinRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PinUiState(
    val pin: String = "",
    val confirmPin: String = "",
    val error: String? = null,
    val isPinVerified: Boolean = false,
    val isBiometricVerified: Boolean = false
)

@HiltViewModel
class PinViewModel @Inject constructor(
    private val pinRepository: PinRepository,
) : ViewModel() {
    var pinUiState by mutableStateOf(PinUiState())
        private set

    /**
     * Upgrade Functions ---------------------------------------------------------------------------
     */
    fun onPinChanged(pin: String) {
        pinUiState = pinUiState.copy(pin = pin)
    }

    fun onConfirmPinChanged(confirmPin: String) {
        pinUiState = pinUiState.copy(confirmPin = confirmPin)
    }

    /**
     * Create Pin Functions ------------------------------------------------------------------------
     */
    fun createPin()
    {
        pinRepository.savePin(pinUiState.pin)
    }

    /**
     * Verify Pin Functions ------------------------------------------------------------------------
     */
    fun verifyPin(inputPin: String)
    {
        val storedPinHash = pinRepository.getPin()
        pinUiState =
            if (storedPinHash == null || pinRepository.hashPin(inputPin) != storedPinHash)
            {
                pinUiState.copy(error = "Incorrect PIN",)
            }
            else {
                pinUiState.copy(isPinVerified = true)
            }
    }

    fun clearErrorMessages()
    {
        pinUiState = pinUiState.copy(error = null)
    }

    /**
     * Verify PIN or Biometric Functions -----------------------------------------------------------
     */
    fun verifyBiometric(
        activity: AppCompatActivity,  // Use AppCompatActivity here
        userPreferences: UserPreferences,
    ) {
        viewModelScope.launch {
            if (userPreferences.isBiometricsEnabled.first()) {
                // 1. Get the executor to run on the main thread
                val executor = ContextCompat.getMainExecutor(activity)

                // 2. Create a BiometricPrompt instance
                val biometricPrompt = BiometricPrompt(
                    activity,
                    executor,
                    object : BiometricPrompt.AuthenticationCallback() {
                        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                            super.onAuthenticationSucceeded(result)
                            pinUiState = pinUiState.copy(isBiometricVerified = true)
                        }

                        override fun onAuthenticationFailed() {
                            super.onAuthenticationFailed()
                            pinUiState = pinUiState.copy(error = "Biometric authentication failed")
                        }

                        override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                            super.onAuthenticationError(errorCode, errString)
                            pinUiState = pinUiState.copy(error = errString.toString())
                        }
                    }
                )

                // 3. Define the prompt info
                val promptInfo = BiometricPrompt.PromptInfo.Builder()
                    .setTitle("Biometric Authentication")
                    .setSubtitle("Authenticate using your biometrics")
                    .setNegativeButtonText("Cancel")
                    .build()

                // 4. Start biometric authentication
                biometricPrompt.authenticate(promptInfo)
            }
            else {
                pinUiState = pinUiState.copy(error = "Biometric authentication is disabled")
            }
        }
    }
    /**
     * Reset Pin Functions -------------------------------------------------------------------------
     */
    fun resetPinState() {
        pinUiState = PinUiState()
    }
}