package com.android.tyzen.xwalletwise.ui.activity.user

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.android.tyzen.xwalletwise.R
import com.android.tyzen.xwalletwise.ui.theme.WalletWiseTheme

@Preview(showBackground = true)
@Composable
fun PreviewWelcomeScreen() {
    WalletWiseTheme {
        WelcomeScreen(
            onStartPress  = { /* TODO */ },
        )
    }
}

/**
 * Welcome Screen
 */
@Composable
fun WelcomeScreen(
    onStartPress: () -> Unit )
{
    val configuration = LocalConfiguration.current
    val screenWidth   = configuration.screenWidthDp

    WalletWiseTheme {
        Box(
            modifier = Modifier.fillMaxSize(), )
        {
            Image(
                painter = painterResource(R.drawable.bg_welcome),
                contentDescription = "Welcome background",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
            )

            /**
             * LOGO AND WELCOME TEXT ---------------------------------------------------------------
             */

            /**
             * LOGO AND WELCOME TEXT ---------------------------------------------------------------
             */
            Column(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally, )
            {
                //Application Logo
                Image(
                    painter = painterResource(R.drawable.application_logo),
                    contentDescription = "App Logo",
                    modifier = Modifier
                        .size((screenWidth * 0.4).dp)
                        .padding(bottom = 8.dp))

                //"Welcome on board" text
                Image(
                    painter = painterResource(R.drawable.lb_welcome_text),
                    contentDescription = "Welcome Text",
                    modifier = Modifier
                        .size((screenWidth*0.6).dp))
            }

            /**
             * BUTTON SECTION ----------------------------------------------------------------------
             */

            /**
             * BUTTON SECTION ----------------------------------------------------------------------
             */
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally)
            {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically, )
                {
                    //Button
                    Box(
                        modifier = Modifier
                            .height(60.dp)
                            .background(
                                brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Black.copy(alpha = 0.10f),
                                    Color.Black.copy(alpha = 0.05f), )
                                ),
                                shape = RoundedCornerShape(10.dp)
                            ), )
                    {
                        //Blur background
                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .blur(8.dp)
                                .background(
                                    Color.Gray.copy(alpha = 0.2f),
                                    RoundedCornerShape(10.dp)
                                )
                        )

                        //Button
                        OutlinedButton(
                            onClick = { onStartPress() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp),
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.5f)),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = Color.Transparent
                            ), )
                        {
                            Text(
                                text = "Start your journey",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                ),
                                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f), )
                        }
                    }
                }

                //User Agreement text
                Text(
                    text = stringResource(id = R.string.user_agreement),
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    lineHeight = 16.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp, bottom = 0.dp),
                )
            }
        }
    }
}