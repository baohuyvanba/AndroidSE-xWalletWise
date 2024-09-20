package com.android.tyzen.xwalletwise.ui.fragment

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import com.android.tyzen.xwalletwise.ui.theme.WalletWiseTheme

@Preview(showBackground = true)
@Composable
fun PreviewFAButton() {
    WalletWiseTheme {
        FAButton(
            modifier = Modifier,
            onClick = { /*TODO*/ },
            icon = Icons.Default.Add,
            contentDescription = "Add Transaction",
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FAButton(
    modifier: Modifier,
    onClick: () -> Unit,
    icon: ImageVector? = null,
    iconResId: Int? = null,
    text: String? = null,
    buttonColor: Color = MaterialTheme.colorScheme.primary,
    contentDescription: String)
{
    if (text.isNullOrBlank()) {
        FloatingActionButton(
            onClick = onClick,
            containerColor = buttonColor,
            modifier = modifier.padding(16.dp),
        )
        {
            if (icon != null)
            {
                Icon(
                    imageVector = icon,
                    contentDescription = contentDescription, )
            }
            else if (iconResId != null)
            {
                Icon(
                    painter = painterResource(id = iconResId),
                    contentDescription = contentDescription, )
            }
        }
    }
    else
    {
        ExtendedFloatingActionButton(
            onClick = onClick,
            containerColor = buttonColor,
            modifier = Modifier.padding(16.dp),
            expanded = true,
            icon = {
                if (icon != null)
                {
                    Icon(
                        imageVector = icon,
                        contentDescription = contentDescription, )
                }
                else if (iconResId != null)
                {
                    Icon(
                        painter = painterResource(id = iconResId),
                        contentDescription = contentDescription, )
                }
            },
            text = {
                   Text(text = text)
            },
        )
    }
}

@Composable
fun FAButtonCircle(
    modifier: Modifier,
    onClick: () -> Unit,
    icon: ImageVector? = null,
    iconResId: Int? = null,
    text: String? = null,
    buttonColor: Color = MaterialTheme.colorScheme.primary,
    contentDescription: String)
{
    if (text.isNullOrBlank()) {
        FloatingActionButton(
            onClick = onClick,
            containerColor = buttonColor,
            shape = CircleShape,
            modifier = modifier.padding(16.dp),
        )
        {
            if (icon != null)
            {
                Icon(
                    imageVector = icon,
                    contentDescription = contentDescription, )
            }
            else if (iconResId != null)
            {
                Icon(
                    painter = painterResource(id = iconResId),
                    contentDescription = contentDescription, )
            }
        }
    }
    else
    {
        ExtendedFloatingActionButton(
            onClick = onClick,
            containerColor = buttonColor,
            modifier = Modifier.padding(16.dp),
            expanded = true,
            icon = {
                if (icon != null)
                {
                    Icon(
                        imageVector = icon,
                        contentDescription = contentDescription, )
                }
                else if (iconResId != null)
                {
                    Icon(
                        painter = painterResource(id = iconResId),
                        contentDescription = contentDescription, )
                }
            },
            text = {
                Text(text = text)
            },
        )
    }
}