package com.android.tyzen.xwalletwise.ui.activity.sercurity

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
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
import com.android.tyzen.xwalletwise.model.user.setFirstTimeLaunch
import com.android.tyzen.xwalletwise.ui.fragment.SpecialGlassButton
import com.android.tyzen.xwalletwise.ui.theme.WalletWiseTheme
import com.android.tyzen.xwalletwise.ui.viewmodel.user.PinViewModel
import com.finance.android.walletwise.ui.fragment.AnimatedGradientText

@Preview(showBackground = true, apiLevel = 34)
@Composable
fun PreviewSetupBiometricsScreen()
{
    WalletWiseTheme {
        SetupBiometricsScreen(
            onNextClick = {},
        )
    }
}

@Composable
fun SetupBiometricsScreen(
    userPreferences: UserPreferences = hiltViewModel(),
    onNextClick: () -> Unit, )
{
    val context    = LocalContext.current
    val configuration = LocalConfiguration.current

    val screenHeight  = configuration.screenHeightDp
    val screenWidth   = configuration.screenWidthDp

    Scaffold(
        modifier = Modifier.fillMaxSize(), )
    {innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .imePadding(), )
        {
            //Background Image ---------------------------------------------------------------------
            Image(
                painter = painterResource(R.drawable.bg_userprofile),
                contentDescription = "Biometrics setup background",
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
             * BIOMETRICS ==========================================================================
             */
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .padding(bottom = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally, )
            {
                //Setup Biometrics text ------------------------------------------------------------
                AnimatedGradientText(
                    text = "Setup Biometrics",
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

                Image(
                    painter = painterResource(R.drawable.ic_security_biometrics),
                    contentDescription = "Biometrics setup",
                )
            }

            /**
             * BUTTONS =============================================================================
             */
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
                    .padding(16.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Center, )
            {
                SpecialGlassButton(
                    text = "Skip",
                    onClick = {
                        setFirstTimeLaunch(context = context, isFirstTime = false)
                        userPreferences.setFirstTimeLaunch(false)
                        onNextClick()
                    },
                    buttonWidth = screenWidth.dp,
                    buttonHeight = 50.dp,
                    modifier = Modifier.weight(0.5f)
                )

                SpecialGlassButton(
                    text = "Enable",
                    onClick = {
                        userPreferences.setBiometricsEnabled(true)
                        userPreferences.setFirstTimeLaunch(false)
                        setFirstTimeLaunch(context = context, isFirstTime = false)
                        onNextClick()
                    },
                    buttonWidth = screenWidth.dp,
                    buttonHeight = 50.dp,
                    modifier = Modifier.weight(0.5f)
                )
            }
        }
    }
}

