package com.android.tyzen.xwalletwise.ui.activity.sercurity

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.tyzen.xwalletwise.R
import com.android.tyzen.xwalletwise.model.user.UserPreferences
import com.android.tyzen.xwalletwise.ui.fragment.GlassMorphPinField
import com.android.tyzen.xwalletwise.ui.fragment.NumbersPadVerifyUser
import com.android.tyzen.xwalletwise.ui.theme.WalletWiseTheme
import com.finance.android.walletwise.ui.fragment.AnimatedGradientText
import com.android.tyzen.xwalletwise.ui.viewmodel.user.PinViewModel
import com.android.tyzen.xwalletwise.ui.viewmodel.user.UserProfileViewModel
import kotlinx.coroutines.flow.first

/**
 * ENTER PIN SCREEN
 */
@Preview(showBackground = true)
@Composable
fun PreviewVerifyUserScreen()
{
    WalletWiseTheme {
        VerifyUserScreen(
            onNavigateHome = {}
        )
    }
}

@Composable
fun VerifyUserScreen(
    pinViewModel: PinViewModel = hiltViewModel(),
    userProfileViewModel: UserProfileViewModel = hiltViewModel(),
    userPreferences: UserPreferences = hiltViewModel(),
    onNavigateHome: () -> Unit,)
{
    val pinUiState = pinViewModel.pinUiState
    var pin by remember { mutableStateOf("") }
    val userProfileUiState = userProfileViewModel.userProfileUiState
    val name = userProfileUiState.name

    val context       = LocalContext.current
    val configuration = LocalConfiguration.current
    val screenHeight  = configuration.screenHeightDp
    val screenWidth   = configuration.screenWidthDp

    Scaffold(
        modifier = Modifier.fillMaxSize(), )
    { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding), )
        {
            //Background Image ---------------------------------------------------------------------
            Image(
                painter = painterResource(R.drawable.bg_userprofile),
                contentDescription = "Verification background",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(), )

            //LOGO ---------------------------------------------------------------------------------
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.TopCenter)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start )
            {
                Image(
                    painter = painterResource(R.drawable.application_logo),
                    contentDescription = "Application Logo",
                )
            }

            /**
             * GREETING ============================================================================
             */
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.TopCenter)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally )
            {
                Spacer(
                    modifier = Modifier.height((screenHeight * 0.08).dp),
                )

                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally )
                {
                    Text(
                        text = "Welcome back,",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    )

                    AnimatedGradientText(
                        text = name,
                        align = TextAlign.Center,
                        modifier = Modifier.padding(top = 0.dp, bottom = 12.dp),
                        gradient = listOf(
                            Color(0xFF62AE29).copy(alpha = 0.8f),
                            Color(0xFF186C12).copy(alpha = 0.8f)
                        ),
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 32.sp
                        ),
                    )
                }
            }

            /**
             * PIN =================================================================================
             */
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally, )
            {
                //Enter Pin field ------------------------------------------------------------------
                Text(
                    text = "Enter Your PIN",
                    style = MaterialTheme.typography.titleMedium
                        .copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                //PinField -------------------------------------------------------------------------
                GlassMorphPinField(
                    value = pin,
                    mask = true,
                    onValueChange = { pin = it},
                    onPinEntered = { pinViewModel.verifyPin(pin) },
                    isError = false,
                )

                //Error message
                if (pinUiState.error != null) {
                    Text(
                        text = pinUiState.error,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Spacer(modifier = Modifier.height((screenHeight * 0.2).dp))
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
                NumbersPadVerifyUser(
                    inputVal = pinUiState.pin,
                    onClickNumber = { digit ->
                        pin += digit
                        if (pin.length == 4) {
                            pinViewModel.verifyPin(pin)
                        }
                    },
                    onClickClear = {
                        pin = ""
                        pinViewModel.clearErrorMessages()
                    },
                    onClickBiometric = {
                        pinViewModel.verifyBiometric(
                            activity = context as AppCompatActivity,
                            userPreferences = userPreferences
                        )
                    },
                    buttonSize = (screenWidth * 0.18).dp,
                    padding = 4.dp,
                )
            }

            LaunchedEffect(key1 = pinUiState.isPinVerified || pinUiState.isBiometricVerified)
            {
                if (pinUiState.isPinVerified || pinUiState.isBiometricVerified) {
                    onNavigateHome()
                }
            }

        }
    }
}