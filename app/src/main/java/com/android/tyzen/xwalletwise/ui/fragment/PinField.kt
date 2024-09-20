package com.android.tyzen.xwalletwise.ui.fragment

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

import com.android.tyzen.xwalletwise.R
import com.android.tyzen.xwalletwise.ui.theme.WalletWiseTheme

@Preview(showBackground = true)
@Composable
fun PreviewPinField() {
    WalletWiseTheme {
        Column {
            PinField(
                value = "1",
                mask = true,
                onValueChange = {},
                onPinEntered = {},
                isError = false,
            )

            GlassMorphPinField(
                value = "1",
                mask = true,
                onValueChange = {},
                onPinEntered = {},
                isError = false,
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PinField(
    modifier: Modifier = Modifier,
    value: String,
    maxNumber: Int = 4,
    mask: Boolean = true,
    isError: Boolean = false,
    onValueChange: (String) -> Unit,
    onPinEntered: ((String) -> Unit)? = null,
    rowPadding: Dp = 8.dp,
    cellPadding: Dp = 16.dp,
    cellSize: Dp = 50.dp,
    cellBorderWidth: Dp = 1.dp, )
{
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusedState = remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxWidth())
    {
        BasicTextField(
            value = value,
            onValueChange = { text ->
                if (text.length <= maxNumber)
                {
                    onValueChange(text)
                    if (text.length == maxNumber)
                    {
                        onPinEntered?.invoke(text)
                    }
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {keyboardController?.hide() } ),
            modifier = Modifier
                .alpha(0.01f)
                .onFocusChanged { focusedState.value = it.isFocused }
                .focusRequester(focusRequester),

            textStyle = TextStyle.Default.copy(color = Color.Transparent),)

        //UI - Pin Field
        Row(
            horizontalArrangement = Arrangement.spacedBy(cellPadding),
            modifier = Modifier
                .padding(rowPadding)
        )
        {
            repeat(maxNumber)
            { index ->
                val isActiveBox = focusedState.value && index == value.length
                
                Box(
                    modifier = Modifier
                        .size(cellSize)
                        .background(
                            color = MaterialTheme.colorScheme.background,
                            shape = RoundedCornerShape(4.dp) )
                        .border(
                            width = cellBorderWidth,
                            color = when {
                                isError -> MaterialTheme.colorScheme.error
                                isActiveBox -> MaterialTheme.colorScheme.primary
                                else -> MaterialTheme.colorScheme.onSurface
                            },
                            shape = RoundedCornerShape(4.dp) )
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) { focusRequester.requestFocus() } )
                {
                    if (index < value.length)
                    {
                        if (mask)
                        {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_circle_filled),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize(0.3f)
                                    .align(Alignment.Center),
                                tint = MaterialTheme.colorScheme.secondary)
                        }
                        else
                        {
                            Text(
                                text = value[index].toString(),
                                modifier = Modifier.align(Alignment.Center),
                                style = MaterialTheme.typography.headlineMedium,
                                color = MaterialTheme.colorScheme.onSurface)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun GlassMorphPinField(
    modifier: Modifier = Modifier,
    value: String,
    mask: Boolean = true,
    isError: Boolean = false,
    onValueChange: (String) -> Unit,
    onPinEntered: ((String) -> Unit)? = null,
    rowPadding: Dp = 8.dp,
    cellPadding: Dp = 12.dp,
    cellSize: Dp = 50.dp,
    cellBorderWidth: Dp = 1.dp,
    blurRadius: Dp = 12.dp, )
{
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusedState = remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    val maxNumber = 4

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxWidth(), )
    {
        //CONTAIN PIN VALUE ========================================================================
        BasicTextField(
            value = value,
            onValueChange = { text ->
                if (text.length <= maxNumber) {
                    onValueChange(text)
                    if (text.length == maxNumber) {
                        onPinEntered?.invoke(text)
                        keyboardController?.hide()
                    }
                }
            },
            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() }
            ),
            modifier = Modifier
                .alpha(0.01f)
                .onFocusChanged { focusedState.value = it.isFocused }
                .focusRequester(focusRequester),
            textStyle = TextStyle.Default.copy(color = Color.Transparent),
        )

        //VISUAL ===================================================================================
        Row(
            horizontalArrangement = Arrangement.spacedBy(cellPadding),
            modifier = Modifier
                .padding(rowPadding), )
        {
            repeat(maxNumber) { index ->
                val isActiveBox = focusedState.value && index == value.length

                Box(
                    modifier = Modifier
                        .size(cellSize)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Black.copy(alpha = 0.10f),
                                    Color.Black.copy(alpha = 0.05f)
                                )
                            ),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .clip(RoundedCornerShape(10.dp))
                )
                {
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .blur (blurRadius)
                            .background(
                                Color.Gray.copy(alpha = 0.2f),
                                RoundedCornerShape(10.dp)
                            )
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) { focusRequester.requestFocus() }
                    )

                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .border(
                                width = cellBorderWidth,
                                color = when {
                                    isError -> MaterialTheme.colorScheme.error
                                    isActiveBox -> MaterialTheme.colorScheme.primary
                                    else -> Color.White
                                },
                                shape = RoundedCornerShape(10.dp)
                            )
                    ) {
                        if (index < value.length) {
                            if (mask) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_circle_filled),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxSize(0.3f)
                                        .align(Alignment.Center),
                                    tint = MaterialTheme.colorScheme.secondary
                                )
                            } else {
                                Text(
                                    text = value[index].toString(),
                                    modifier = Modifier.align(Alignment.Center),
                                    style = MaterialTheme.typography.headlineMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }

            }
        }
    }
}
