package com.android.tyzen.xwalletwise.ui.activity.sercurity

import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricManager.BIOMETRIC_SUCCESS
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.tyzen.xwalletwise.R
import com.android.tyzen.xwalletwise.ui.fragment.GlassMorphPinField
import com.android.tyzen.xwalletwise.ui.theme.WalletWiseTheme
import com.android.tyzen.xwalletwise.ui.fragment.NumbersPadSetupPin
import com.android.tyzen.xwalletwise.ui.viewmodel.user.PinViewModel
import com.finance.android.walletwise.ui.fragment.AnimatedGradientText

@Preview(showBackground = true, apiLevel = 34)
@Composable
fun PreviewSetupPinScreen()
{
    WalletWiseTheme {
        SetupPinScreen(
            onNextClickToBiometric = { /*TODO*/ },
            onNextClickToInputPin  = { /*TODO*/ },
        )
    }
}

@Composable
fun SetupPinScreen(
    pinViewModel: PinViewModel = hiltViewModel(),
    onNextClickToBiometric: () -> Unit,
    onNextClickToInputPin : () -> Unit, )
{
    val pinUiState = pinViewModel.pinUiState

    val configuration = LocalConfiguration.current
    val context = LocalContext.current

    val biometricManager = BiometricManager.from(context)
    val isBiometricSupported = biometricManager.canAuthenticate(BIOMETRIC_STRONG) == BIOMETRIC_SUCCESS

    val keyboardController = LocalSoftwareKeyboardController.current
    keyboardController?.hide()

    val screenHeight  = configuration.screenHeightDp
    val screenWidth   = configuration.screenWidthDp

    val isPinsMatched = pinUiState.pin.length == 4 && pinUiState.confirmPin.length == 4 && pinUiState.pin == pinUiState.confirmPin

    Scaffold(
        modifier = Modifier.fillMaxSize(), )
    {innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding), )
        {
            //Background Image ---------------------------------------------------------------------
            Image(
                painter = painterResource(R.drawable.bg_userprofile),
                contentDescription = "PIN setup background",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(), )

            //LOGO ---------------------------------------------------------------------------------
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.TopCenter)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start, )
            {
                Image(
                    painter = painterResource(R.drawable.application_logo),
                    contentDescription = "WalletWise Logo",
                )
            }

            /**
             * PIN FORM ============================================================================
             */
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .padding(bottom = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally, )
            {
                //Setup PIN text -------------------------------------------------------------------
                AnimatedGradientText(
                    text = "Setup PIN",
                    align = TextAlign.Center,
                    modifier = Modifier.padding(top = 18.dp, bottom = 12.dp),
                    gradient = listOf(
                        Color(0xFF62AE29).copy(alpha = 0.8f),
                        Color(0xFF186C12).copy(alpha = 0.8f)
                    ),
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp
                    ),
                )

                Spacer(modifier = Modifier.height((screenHeight * 0.01).dp))

                //Enter PIN field ------------------------------------------------------------------
                Text(
                    text = "Enter PIN",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.onSurface,
                )
                GlassMorphPinField(
                    value = pinUiState.pin,
                    mask = true,
                    onValueChange = {},
                    onPinEntered = {},
                    isError = false,
                )

                Spacer(modifier = Modifier.fillMaxHeight(0.04f))

                //Confirm PIN field ----------------------------------------------------------------
                Text(
                    text = "Confirm PIN",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.onSurface,
                )
                GlassMorphPinField(
                    value = pinUiState.confirmPin,
                    mask = true,
                    onValueChange = {},
                    onPinEntered = {},
                    isError = false,
                )

                //ERROR message --------------------------------------------------------------------
                if (!isPinsMatched &&
                    pinUiState.pin.length == 4 &&
                    pinUiState.confirmPin.length == 4) {
                    Text(
                        text = "PINs do not match",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                    )
                }

                Spacer(modifier = Modifier.fillMaxHeight(0.5f))
            }

            /**
             * NUMBERS PAD =========================================================================
             */
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally, )
            {
                NumbersPadSetupPin(
                    inputVal = pinUiState.pin,
                    onClickNumber = { digit ->
                        if (pinUiState.pin.length < 4) {
                            pinViewModel.onPinChanged(pinUiState.pin + digit)
                        }
                        else if (pinUiState.confirmPin.length < 4) {
                            pinViewModel.onConfirmPinChanged(pinUiState.confirmPin + digit)
                        }
                    },
                    onClickClear = {
                        pinViewModel.onPinChanged("")
                        pinViewModel.onConfirmPinChanged("")
                    },
                    onClickConfirm = {
                        if (isPinsMatched) {
                            pinViewModel.createPin()
                            if (isBiometricSupported) {
                                onNextClickToBiometric()
                            }
                            else {
                                onNextClickToInputPin()
                            }
                        }
                        else {
                            Toast.makeText(context, "Invalid PIN. Please double-check and try again!", Toast.LENGTH_SHORT).show()
                        }
                    },
                    buttonSize = (screenWidth * 0.18).dp,
                    padding = 4.dp,
                )
            }
        }
    }
}

