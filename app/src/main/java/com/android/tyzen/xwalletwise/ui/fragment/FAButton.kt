package com.android.tyzen.xwalletwise.ui.fragment

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
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
            modifier = modifier.padding(16.dp), )
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

/**
 * FAB Add Transaction =============================================================================
 */
@Composable
fun FABTransaction(
    modifier: Modifier,
    onClick: () -> Unit,
    icon: ImageVector = Icons.Default.Add,
    shape: Shape = FloatingActionButtonDefaults.shape,
    containerColor: Color = Color.White,
    borderColor: Color = Color.White,
    contentDescription: String, )
{
    FloatingActionButton(
        onClick = onClick,
        containerColor = containerColor,
        shape = shape,
        elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 0.dp),
        modifier = modifier
            .border(
                width = 1.dp,
                color = borderColor,
                shape = shape,
            ),
    ) {
        Icon(
            imageVector = icon,
            tint = borderColor,
            contentDescription = contentDescription,)
    }
}

@Composable
fun FABTransactionCircle(
    modifier: Modifier,
    onClick: () -> Unit,
    icon: ImageVector = Icons.Default.Add,
    containerColor: Color = Color.White,
    borderColor: Color = Color.White,
    contentDescription: String)
{
    FloatingActionButton(
        onClick = onClick,
        containerColor = containerColor,
        shape = CircleShape,
        elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 0.dp),
        modifier = modifier
            .border(
                width = 1.dp,
                color = borderColor,
                shape = CircleShape,
            ),
    ) {
        Icon(
            imageVector = icon,
            tint = borderColor,
            contentDescription = contentDescription, )
    }
}


/**
 * FAB View Detail =================================================================================
 */
@Composable
fun FABViewDetailIcon(
    modifier: Modifier,
    onClick: () -> Unit,
    icon: ImageVector = Icons.Default.Add,
    containerColor: Color = Color.White,
    shape: Shape = FloatingActionButtonDefaults.shape,
    iconColor: Color = Color.White,
    contentDescription: String, )
{
    FloatingActionButton(
        onClick = onClick,
        containerColor = containerColor,
        shape = shape,
        elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 0.dp),
        modifier = modifier
            .border(
                width = 1.dp,
                color = containerColor.copy(0.5f),
                shape = shape,
            ),
    ) {
        Icon(
            imageVector = icon,
            tint = iconColor,
            contentDescription = contentDescription,)
    }
}

@Composable
fun FABViewDetailExtended(
    modifier: Modifier,
    onClick: () -> Unit,
    text: String,
    icon: ImageVector = Icons.Default.Add,
    containerColor: Color = Color.White,
    shape: Shape = FloatingActionButtonDefaults.extendedFabShape,
    contentColor: Color = Color.White,
    contentDescription: String, )
{
    ExtendedFloatingActionButton(
        modifier = modifier,
        onClick = onClick,
        containerColor = containerColor,
        shape = shape,
        elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 0.dp),
        expanded = true,
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = contentColor, )
        },
        text = {
            Text(
                text = text,
                color = contentColor, )
        },
    )
}