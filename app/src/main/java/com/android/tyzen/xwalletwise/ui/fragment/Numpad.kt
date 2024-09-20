package com.android.tyzen.xwalletwise.ui.fragment

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

import com.android.tyzen.xwalletwise.R
import com.android.tyzen.xwalletwise.ui.theme.WalletWiseTheme

@Preview(showBackground = true)
@Composable
fun PreviewButtons() {
    WalletWiseTheme {
        Column {
            NumberButton(
                number = 0,
                onClick = { /*TODO*/ }
            )

            FunctionButton(
                icon = Icons.Default.Home,
                contentDescription = "Category",
                onClick = { /*TODO*/ }
            )
        }
    }
}

@Composable
fun NumberButton(
    modifier: Modifier = Modifier,
    number: Int = 0,
    onClick: (digit: Char) -> Unit = {},
    buttonSize: Dp = 50.dp,
    blurRadius: Dp = 8.dp, )
{
    val density = LocalDensity.current
    val fontSize = with(density) { (buttonSize * 0.3f).toSp() }

    //Outer Box: Background Gradient + The Button
    Box(
        modifier = modifier
            .wrapContentSize(align = Alignment.Center)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Black.copy(alpha = 0.10f),
                        Color.Black.copy(alpha = 0.05f)
                    )
                ),
                shape = RoundedCornerShape(10.dp)
            )
            .size(buttonSize)
            .clip(RoundedCornerShape(10.dp))
    ) {
        //Inner Box for the blur BACKGROUND
        Box(
            modifier = Modifier
                .size(buttonSize)
                .blur(blurRadius)
                .background(
                    Color.Gray.copy(alpha = 0.2f),
                    RoundedCornerShape(10.dp)
                )
        )

        //Transparent BUTTON with a border
        OutlinedButton(
            onClick = { onClick(number.digitToChar()) },
            modifier = Modifier.size(buttonSize),
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.5f)
            ),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.Transparent
            ),
        ) {
            Box(
                modifier = Modifier.size(buttonSize),
                contentAlignment = Alignment.Center, )
            {
                Text(
                    text = number.toString(),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = fontSize,
                    ),
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun FunctionButton(
    modifier: Modifier = Modifier,
    icon: Any,
    contentDescription: String = "",
    onClick: () -> Unit = {},
    buttonSize: Dp = 50.dp,
    blurRadius: Dp = 8.dp,
    iconColor: Color = Color.White, )
{
    //Outer Box: Background Gradient + The Button
    Box(
        modifier = modifier
            .wrapContentSize(align = Alignment.Center)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Black.copy(alpha = 0.10f),
                        Color.Black.copy(alpha = 0.05f)
                    )
                ),
                shape = RoundedCornerShape(10.dp)
            )
            .size(buttonSize)
            .clip(RoundedCornerShape(10.dp))
    ) {
        //Inner Box for the blur BACKGROUND
        Box(
            modifier = Modifier
                .size(buttonSize)
                .blur(blurRadius)
                .background(
                    Color.Gray.copy(alpha = 0.2f),
                    RoundedCornerShape(10.dp)
                )
        )

        //Transparent BUTTON with a border
        Box(
            modifier = Modifier
                .size(buttonSize)
                .background(
                    Color.Transparent,
                    RoundedCornerShape(10.dp)
                )
        ) {
            // Centered icon
            Box(
                modifier = Modifier
                    .size(buttonSize)
                    .clickable { onClick() },
                contentAlignment = Alignment.Center
            ) {
                when (icon) {
                    is ImageVector -> {
                        Icon(
                            modifier = Modifier
                                .size(buttonSize.times(0.4f), buttonSize.times(0.4f)),
                            imageVector = icon,
                            tint = iconColor,
                            contentDescription = contentDescription,
                        )
                    }
                    is Painter -> {
                        Icon(
                            modifier = Modifier
                                .size(buttonSize.times(0.4f), buttonSize.times(0.4f)),
                            painter = icon,
                            tint = iconColor,
                            contentDescription = contentDescription,
                        )
                    }
                    else -> throw IllegalArgumentException("Unsupported icon type")
                }
            }
        }
    }
}

/**
 * Numbers Pad PIN Input ===========================================================================
 */
@Composable
fun NumbersPad(
    modifier: Modifier = Modifier,
    inputVal: String,
    onClickNumber: (digit: Char) -> Unit,
    onClickClear: () -> Unit,
    onClickBiometric: () -> Unit,
    buttonSize: Dp = 50.dp,
    padding: Dp = 5.dp, )
{
    var input by remember { mutableStateOf(inputVal) }

    val numbers = listOf(
        listOf(1, 2, 3),
        listOf(4, 5, 6),
        listOf(7, 8, 9)
    )

    Column(
        modifier = modifier
            .wrapContentSize(Alignment.Center)
            .padding(padding), )
    {
        //Loop through rows of numbers
        numbers.forEach { row ->
            Row {
                row.forEach { number ->
                    NumberButton(
                        number = number,
                        onClick = onClickNumber,
                        buttonSize = buttonSize,
                        modifier = Modifier.padding(padding.times(0.5f))
                    )
                }
            }
        }

        //Last row with Function buttons and 0
        Row {
            FunctionButton(
                icon = Icons.Filled.Clear,
                contentDescription = "Clear",
                onClick = {
                    onClickClear()
                },
                buttonSize = buttonSize,
                modifier = Modifier.padding(padding.times(0.5f))
            )
            //
            NumberButton(
                number = 0,
                onClick = {
                    input += it
                    onClickNumber(it)
                },
                buttonSize = buttonSize,
                modifier = Modifier.padding(padding.times(0.5f))
            )
            //
            FunctionButton(
                icon = painterResource(R.drawable.ic_security_biometrics),
                contentDescription = "Fingerprint",
                onClick = onClickBiometric,
                buttonSize = buttonSize,
                modifier = Modifier.padding(padding.times(0.5f))
            )
        }
    }
}

//VERIFY USER SCREEN NUMPAD ------------------------------------------------------------------------
@Composable
fun NumbersPadVerifyUser(
    modifier: Modifier = Modifier,
    inputVal: String,
    onClickNumber: (digit: Char) -> Unit,
    onClickClear: () -> Unit,
    onClickBiometric: () -> Unit,
    buttonSize: Dp = 50.dp,
    padding: Dp = 5.dp, )
{
    var input by remember { mutableStateOf(inputVal) }

    val numbers = listOf(
        listOf(1, 2, 3),
        listOf(4, 5, 6),
        listOf(7, 8, 9)
    )

    Column(
        modifier = modifier
            .wrapContentSize(Alignment.Center)
            .padding(padding), )
    {
        //Loop through rows of numbers
        numbers.forEach { row ->
            Row {
                row.forEach { number ->
                    NumberButton(
                        number = number,
                        onClick = onClickNumber,
                        buttonSize = buttonSize,
                        modifier = Modifier.padding(padding.times(0.5f))
                    )
                }
            }
        }

        //Last row with Function buttons and 0
        Row {
            FunctionButton(
                icon = Icons.Filled.Clear,
                contentDescription = "Clear",
                onClick = {
                    onClickClear()
                },
                buttonSize = buttonSize,
                modifier = Modifier.padding(padding.times(0.5f))
            )
            //
            NumberButton(
                number = 0,
                onClick = {
                    input += it
                    onClickNumber(it)
                },
                buttonSize = buttonSize,
                modifier = Modifier.padding(padding.times(0.5f))
            )
            //
            FunctionButton(
                icon = painterResource(R.drawable.ic_security_biometrics),
                contentDescription = "Fingerprint",
                onClick = onClickBiometric,
                buttonSize = buttonSize,
                modifier = Modifier.padding(padding.times(0.5f)),
                iconColor = Color(0xFF62AE29)
            )
        }
    }
}

//SETUP PIN SCREEN NUMPAD --------------------------------------------------------------------------
@Composable
fun NumbersPadSetupPin(
    modifier: Modifier = Modifier,
    inputVal: String,
    onClickNumber: (digit: Char) -> Unit,
    onClickClear: () -> Unit,
    onClickConfirm: () -> Unit,
    buttonSize: Dp = 50.dp,
    padding: Dp = 5.dp, )
{
    var input by remember { mutableStateOf(inputVal) }

    val numbers = listOf(
        listOf(1, 2, 3),
        listOf(4, 5, 6),
        listOf(7, 8, 9)
    )

    Column(
        modifier = modifier
            .wrapContentSize(Alignment.Center)
            .padding(padding), )
    {
        //Loop through rows of numbers
        numbers.forEach { row ->
            Row {
                row.forEach { number ->
                    NumberButton(
                        number = number,
                        onClick = onClickNumber,
                        buttonSize = buttonSize,
                        modifier = Modifier.padding(padding.times(0.5f))
                    )
                }
            }
        }

        //Last row with Function buttons and 0
        Row {
            FunctionButton(
                icon = Icons.Filled.Clear,
                contentDescription = "Clear",
                onClick = {
                    onClickClear()
                },
                buttonSize = buttonSize,
                modifier = Modifier.padding(padding.times(0.5f))
            )
            //
            NumberButton(
                number = 0,
                onClick = {
                    input += it
                    onClickNumber(it)
                },
                buttonSize = buttonSize,
                modifier = Modifier.padding(padding.times(0.5f))
            )
            //
            FunctionButton(
                icon = Icons.Filled.ArrowForward,
                contentDescription = "Confirm PIN Setup",
                onClick = onClickConfirm,
                buttonSize = buttonSize,
                modifier = Modifier.padding(padding.times(0.5f))
            )
        }
    }
}