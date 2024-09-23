package com.android.tyzen.xwalletwise.ui.activity.user

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

import com.android.tyzen.xwalletwise.R
import com.android.tyzen.xwalletwise.ui.theme.WalletWiseTheme
import com.finance.android.walletwise.ui.fragment.AnimatedGradientText
import com.android.tyzen.xwalletwise.ui.fragment.DropDownMenu
import com.android.tyzen.xwalletwise.ui.fragment.FormTextField
import com.android.tyzen.xwalletwise.ui.fragment.SpecialGlassButton
import com.android.tyzen.xwalletwise.ui.viewmodel.user.UserProfileViewModel

@Preview(showBackground = true)
@Composable
fun PreviewProfileSetupScreen() {
    WalletWiseTheme {
        ProfileSetupScreen()
    }
}

@Composable
fun ProfileSetupScreen(
    userProfileViewModel: UserProfileViewModel = hiltViewModel(),
    navigateToPINSetup: () -> Unit = {}, )
{
    val userProfileUiState = userProfileViewModel.userProfileUiState

    val configuration = LocalConfiguration.current
    val screenHeight   = configuration.screenHeightDp
    val screenWidth    = configuration.screenWidthDp

    //Next Logic
    val isFormNotBlank = userProfileUiState.name.isNotBlank() &&
            userProfileUiState.gender in 0..2 &&
            userProfileUiState.age != 0 &&
            userProfileUiState.phoneNumber.isNotBlank() &&
            userProfileUiState.currency.isNotBlank()

    //Lists
    val genders = listOf("Male", "Female", "Other")
    val currencies = listOf("VND", "USD", "EUR")

    Box(
        modifier = Modifier.fillMaxSize(), )
    {
        //Background Image
        Image(
            painter = painterResource(R.drawable.bg_userprofile),
            contentDescription = "User Profile setup background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(), )

        //Form Section
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopCenter)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start)
        {
            /**
             * Application Logo --------------------------------------------------------------------
             */
            Image(
                painter = painterResource(R.drawable.application_logo),
                contentDescription = "Application Logo",
                modifier = Modifier
                    .padding(bottom = 8.dp)
            )

            Spacer(
                modifier = Modifier
                    .height((screenHeight*0.15).dp))

            /**
             * FORM: User Profile ------------------------------------------------------------------
             */
            Box(
                modifier = Modifier
                    .border(
                        width = 2.dp,
                        color = Color.White.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .wrapContentSize(), )
            {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .blur(8.dp)
                        .background(
                            Color.Gray.copy(alpha = 0.2f),
                            RoundedCornerShape(10.dp)
                        )
                )

                Column(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, )
                        .clip(RoundedCornerShape(10.dp)),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally, )
                {
                    //"Set up your profile" text ---------------------------------------------------
                    AnimatedGradientText(
                        text = "Set Up Your Profile",
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

                    //Your name --------------------------------------------------------------------
                    FormTextField(
                        value = userProfileUiState.name,
                        onValueChange = { userProfileViewModel.onFullNameChanged(it) },
                        label = "Your name",
                        keyboardOptions = KeyboardOptions.Default,
                        singleLine = true,
                        isError = false,
                        enabled = true,
                        textFieldColors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor   = Color.White.copy(alpha = 0.5f),
                            unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
                            disabledBorderColor  = Color.White.copy(alpha = 0.5f),
                            focusedLabelColor    = Color.White,
                            unfocusedLabelColor  = Color.White,
                            disabledLabelColor   = Color.White,
                            cursorColor          = Color.White.copy(alpha = 0.5f),
                        ),
                        formTextStyle = TextStyle(
                            color = Color.White.copy(alpha = 0.8f),
                        ),
                    )

                    //Gender and Age section
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp), )
                    {
                        //Gender -------------------------------------------------------------------
                        Box(
                            modifier = Modifier.weight(1f), )
                        {
                            DropDownMenu(
                                label = "Gender",
                                options = genders,
                                value = numToGender(userProfileUiState.gender),
                                onOptionSelected = {
                                    userProfileViewModel.onGenderChanged(
                                        genderToNum(it)
                                    )
                                },
                                focusedBorderColor   = Color.White.copy(alpha = 0.5f),
                                unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
                                focusedLabelColor    = Color.White,
                                unfocusedLabelColor  = Color.White,
                                textStyle  = MaterialTheme.typography.bodyMedium.copy(
                                    color = Color.White
                                ),
                                labelStyle = MaterialTheme.typography.bodyMedium,
                                trailingIconColor = Color.White,
                                dropdownTextItemColor = Color.Black.copy(alpha = 0.5f),
                                backgroundColor = Color.Transparent,
                            )

                        }

                        //Age ----------------------------------------------------------------------
                        Box(
                            modifier = Modifier.weight(1f), )
                        {
                            FormTextField(
                                value = if (userProfileUiState.age != 0) (userProfileUiState.age).toString() else "",
                                onValueChange = {
                                    userProfileViewModel.onAgeChanged(
                                        it.toIntOrNull() ?: 0
                                    )
                                },
                                label = "Age",
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                                singleLine = true,
                                isError = false,
                                enabled = true,
                                textFieldColors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor   = Color.White.copy(alpha = 0.5f),
                                    unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
                                    disabledBorderColor  = Color.White.copy(alpha = 0.5f),
                                    focusedLabelColor    = Color.White,
                                    unfocusedLabelColor  = Color.White,
                                    disabledLabelColor   = Color.White,
                                    cursorColor          = Color.White.copy(alpha = 0.5f),
                                ),
                                formTextStyle = TextStyle(
                                    color = Color.White.copy(alpha = 0.8f),
                                ),
                            )

                        }
                    }

                    //Phone number -----------------------------------------------------------------
                    FormTextField(
                        value = userProfileUiState.phoneNumber,
                        onValueChange = { userProfileViewModel.onPhoneNumberChanged(it) },
                        label = "Phone number",
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        singleLine = true,
                        isError = false,
                        enabled = true,
                        textFieldColors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor   = Color.White.copy(alpha = 0.5f),
                            unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
                            disabledBorderColor  = Color.White.copy(alpha = 0.5f),
                            focusedLabelColor    = Color.White,
                            unfocusedLabelColor  = Color.White,
                            disabledLabelColor   = Color.White,
                            cursorColor          = Color.White.copy(alpha = 0.5f),
                        ),
                        formTextStyle = TextStyle(
                            color = Color.White.copy(alpha = 0.8f),
                        ),
                    )

                    //Currency ---------------------------------------------------------------------
                    DropDownMenu(
                        label = "Currency",
                        options = currencies,
                        value = userProfileUiState.currency,
                        onOptionSelected = {
                            userProfileViewModel.onCurrencyChanged(
                                it,
                                currencyPosition(it)
                            )
                        },
                        focusedBorderColor   = Color.White.copy(alpha = 0.5f),
                        unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
                        focusedLabelColor    = Color.White,
                        unfocusedLabelColor  = Color.White,
                        textStyle  = MaterialTheme.typography.bodyMedium.copy(
                            color = Color.White
                        ),
                        labelStyle = MaterialTheme.typography.bodyMedium,
                        trailingIconColor = Color.White,
                        dropdownTextItemColor = Color.Black.copy(alpha = 0.5f),
                        backgroundColor = Color.Transparent,
                    )

                    Text(
                        text = "Your data is yours alone!",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimary
                        ),
                        modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
                    )
                }
            }

            Spacer(
                modifier = Modifier
                    .height((screenHeight*0.1).dp))

            /**
             * BUTTON: Next ------------------------------------------------------------------------
             */
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.BottomCenter, )
            {
                SpecialGlassButton(
                    text = "Next",
                    onClick = {
                        if (isFormNotBlank) {
                            userProfileViewModel.upsertUserProfile();
                            navigateToPINSetup()
                        }
                    },
                    buttonWidth = screenWidth.dp,
                    buttonHeight = 50.dp
                )
            }
        }
    }
}

//--------------------------------------------------------------------------------------------------
fun genderToNum(gender: String): Int {
    return when (gender) {
        "Male"   -> 0
        "Female" -> 1
        else     -> 2
    }
}

fun numToGender(num: Int): String {
    return when (num) {
        0    -> "Male"
        1    -> "Female"
        else -> "Other"
    }
}

fun currencyPosition(currency: String): Boolean {
    return when (currency) {
        "VND" -> true
        else  -> false
    }
}
