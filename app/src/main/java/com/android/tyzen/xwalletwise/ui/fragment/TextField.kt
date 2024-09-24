package com.android.tyzen.xwalletwise.ui.fragment

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.android.tyzen.xwalletwise.R
import com.android.tyzen.xwalletwise.ui.theme.WalletWiseTheme

/**
 * TEXT FIELD ======================================================================================
 */
@Composable
fun NormalTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    singleLine: Boolean = true,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false, )
{
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        keyboardOptions = keyboardOptions,
        singleLine = singleLine,
        leadingIcon = leadingIcon,
        trailingIcon = {
            if (value.isNotEmpty())
            {
                IconButton(onClick = { onValueChange("") })
                {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Clear Text")
                }
            }
        },
        isError = isError,
    )
}

/**
 * PASSWORD FIELD ==================================================================================
 */
@Composable
fun PasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    singleLine: Boolean = true,
    leadingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    isError: Boolean = false, )
{
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        keyboardOptions = keyboardOptions,
        singleLine = singleLine,
        leadingIcon = leadingIcon,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(
                    painter = if (passwordVisible) { painterResource(R.drawable.ic_password_show) } else painterResource(R.drawable.ic_password_hide),
                    contentDescription = if (passwordVisible) "Hide password" else "Show password"
                )
            }
        },
        isError = isError,
    )
}

/**
 * FORM TEXT FIELD =================================================================================
 */
//@Preview(showBackground = false)
//@Composable
//fun PreviewFormTextField() {
//    WalletWiseTheme {
//        Column {
//            FormTextField(
//                value = "",
//                onValueChange = {},
//                label = "Title",
//                modifier = Modifier,
//                keyboardOptions = KeyboardOptions.Default,
//                singleLine = true,
//                isError = false,
//                enabled = true,
//                readOnly = false, )
//
//            FormTextField(
//                value = "Value - ReadOnly",
//                onValueChange = {},
//                label = "Title",
//                modifier = Modifier,
//                keyboardOptions = KeyboardOptions.Default,
//                singleLine = true,
//                isError = false,
//                enabled = false,
//                readOnly = true, )
//        }
//    }
//}

@Composable
fun FormTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    singleLine: Boolean = true,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    //
    textFieldColors: TextFieldColors = OutlinedTextFieldDefaults.colors(
        //Border color
        focusedBorderColor   = MaterialTheme.colorScheme.primary,              //focused
        unfocusedBorderColor = MaterialTheme.colorScheme.outline,              //unfocused
        disabledBorderColor  = MaterialTheme.colorScheme.secondaryContainer,   //disabled
        //Label color
        focusedLabelColor    = MaterialTheme.colorScheme.primary,              //focused
        unfocusedLabelColor  = MaterialTheme.colorScheme.primary,              //unfocused
        disabledLabelColor   = MaterialTheme.colorScheme.onSurface,            //disabled
        //Cursor color
        cursorColor = MaterialTheme.colorScheme.primary,
    ),
    formTextStyle: TextStyle = TextStyle(
        color = MaterialTheme.colorScheme.onSurface,
    ),
    readOnlyTextStyle: TextStyle = TextStyle(
        color = MaterialTheme.colorScheme.onSurface,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        fontSize = MaterialTheme.typography.titleLarge.fontSize,
    ),
) {
    val interactionSource = remember { MutableInteractionSource() }
    val colors: TextFieldColors = textFieldColors

    OutlinedTextField(
        textStyle =
            if (readOnly)
            {
                readOnlyTextStyle
            }
            else
            {
                formTextStyle
            },
        value = value,
        onValueChange = {
            if (enabled && !readOnly) {
                onValueChange(it)
            }
        },
        //Label
        label = {
            val style = if (readOnly)
            {
                MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    lineHeight = MaterialTheme.typography.titleLarge.lineHeight * 1.2,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize * 0.8)
            }
            else
            {
                MaterialTheme.typography.bodyMedium
            }

            if (readOnly)
            {
                Box(
                    modifier = Modifier.padding(8.dp),
                    contentAlignment = Alignment.Center,)
                {
                    Text(
                        text = label,
                        style = style, )
                }
            }
            else
            {
                Text(
                    text = label,
                    style = style, )
            }
        },
        modifier = modifier
            .padding(top = 8.dp, bottom = 8.dp)
            .fillMaxWidth(),
        keyboardOptions = keyboardOptions,
        singleLine = singleLine,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        isError = isError,
        enabled = !readOnly,
        readOnly = readOnly,
        colors = colors,
        interactionSource = interactionSource
    )
}

/**
 * BALANCE AMOUNT FIELD ============================================================================
 */
@Composable
fun BalanceAmountField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    readOnly: Boolean = false,
    balanceColor: Color,
    currency: String, )
{
    BoxWithConstraints(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center, )
    {
        val fontSize = remember(value, maxWidth) {
            val textLength = value.length
            when {
                textLength < 6  -> 60.sp
                textLength < 13 -> 60.sp * 0.85f
                textLength < 17 -> 60.sp * 0.7f
                else            -> 60.sp * 0.5f
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .background(Color.Transparent),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center, )
        {
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(
                    color = balanceColor,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    fontSize = fontSize,
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                singleLine = true,
                readOnly = readOnly,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    disabledBorderColor = Color.Transparent,
                    cursorColor = Color.Transparent,
                )
            )

            Box(
                modifier = Modifier
                    .background(
                        color = Color.Black.copy(0.5f),
                        shape = RoundedCornerShape(16.dp),
                    )
                    .padding(
                        start  = 7.dp,
                        end    = 6.dp,
                        top    = 2.dp,
                        bottom = 2.dp,
                    ),
            ) {
                Text(
                    text  = currency,
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.White,
                )
            }
        }
    }
}

/**
 * TRANSACTION FIELD ===============================================================================
 */
@Preview(showBackground = false)
@Composable
fun PreviewTransactionDetailField() {
    WalletWiseTheme {
        Column(
            modifier = Modifier.background(Color(0xFF59C173)).padding(8.dp),
        ) {
            TransactionDetailField(
                value = "This is the Field",
                onValueChange = {},
                label = "Title",
                modifier = Modifier,
                keyboardOptions = KeyboardOptions.Default,
                readOnly = false, )
        }
    }
}

@Composable
fun TransactionDetailField(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    readOnly: Boolean = false,
) {
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = Modifier.fillMaxWidth(), )
    {
        Text(
            text = label,
            modifier = Modifier.padding(start = 4.dp, bottom = 0.dp),
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color      = Color.Black,
                textAlign  = TextAlign.Start,
            ),
        )

        OutlinedTextField(
            modifier = modifier
                .padding(vertical = 4.dp)
                .fillMaxWidth()
                .heightIn(max = 50.dp),
            value = value,
            onValueChange = {
                if (!readOnly) {
                    onValueChange(it)
                }
            },
            keyboardOptions = keyboardOptions,
            shape = RoundedCornerShape(10.dp),
            textStyle =
            TextStyle(
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
            ),
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            readOnly = readOnly,
            colors = OutlinedTextFieldDefaults.colors(
                //Border color
                focusedBorderColor = MaterialTheme.colorScheme.primary,              //focused
                unfocusedBorderColor = Color.White,                                    //unfocused
                disabledBorderColor = MaterialTheme.colorScheme.secondaryContainer,   //disabled
                //Label color
                focusedLabelColor = MaterialTheme.colorScheme.primary,              //focused
                unfocusedLabelColor = MaterialTheme.colorScheme.primary,              //unfocused
                disabledLabelColor = MaterialTheme.colorScheme.onSurface,            //disabled
                //Cursor color
                cursorColor = MaterialTheme.colorScheme.primary,
            ),
            interactionSource = interactionSource,
        )
    }
}