package com.android.tyzen.xwalletwise.ui.fragment

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

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
@Preview(showBackground = false)
@Composable
fun PreviewFormTextField() {
    WalletWiseTheme {
        Column {
            FormTextField(
                value = "",
                onValueChange = {},
                label = "Title",
                modifier = Modifier,
                keyboardOptions = KeyboardOptions.Default,
                singleLine = true,
                isError = false,
                enabled = true,
                readOnly = false, )

            FormTextField(
                value = "Value - ReadOnly",
                onValueChange = {},
                label = "Title",
                modifier = Modifier,
                keyboardOptions = KeyboardOptions.Default,
                singleLine = true,
                isError = false,
                enabled = false,
                readOnly = true, )
        }
    }
}

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
    focusedBorderColor: Color = MaterialTheme.colorScheme.primary,
    unfocusedBorderColor: Color = MaterialTheme.colorScheme.outline,
    disabledBorderColor: Color = MaterialTheme.colorScheme.secondaryContainer,
    //
    focusedLabelColor: Color = MaterialTheme.colorScheme.primary,
    unfocusedLabelColor: Color = MaterialTheme.colorScheme.primary,
    disabledLabelColor: Color = MaterialTheme.colorScheme.onSurface,
    //
    cursorColor: Color = MaterialTheme.colorScheme.primary,
    //
    formTextStyle: TextStyle = TextStyle(
        color = MaterialTheme.colorScheme.onSurface,
    ),
    readOnlyTextStyle: TextStyle = TextStyle(
        color = MaterialTheme.colorScheme.onSurface,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        fontSize = MaterialTheme.typography.titleLarge.fontSize,
    ),
)
{
    val interactionSource = remember { MutableInteractionSource() }
    val colors: TextFieldColors = OutlinedTextFieldDefaults.colors(
        //Border color
        focusedBorderColor = focusedBorderColor,
        unfocusedBorderColor = unfocusedBorderColor,
        disabledBorderColor = disabledBorderColor,
        //Label color
        focusedLabelColor = focusedLabelColor,
        unfocusedLabelColor = unfocusedLabelColor,
        disabledLabelColor = disabledLabelColor,
        //Cursor color
        cursorColor = cursorColor,
    )

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