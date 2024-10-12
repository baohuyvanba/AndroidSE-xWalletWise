package com.android.tyzen.xwalletwise.ui.fragment

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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

/**
 * FAB Add Transaction =============================================================================
 */
@Composable
fun FABTransaction(
    modifier: Modifier,
    onClick: () -> Unit,
    icon: ImageVector = Icons.Default.Add,
    shape: Shape = FloatingActionButtonDefaults.shape,
    colors: List<Color>, //icon, container, border
    contentDescription: String, )
{
    FloatingActionButton(
        onClick = onClick,
        containerColor = colors[1],
        shape = shape,
        elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 0.dp),
        modifier = modifier
            .border(
                width = 1.dp,
                color = colors[2],
                shape = shape,
            ),
    ) {
        Icon(
            imageVector = icon,
            tint = colors[0],
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

/**
 * FAB Category ====================================================================================
 */
@Composable
fun FABCategory(
    modifier: Modifier,
    onClick: () -> Unit,
    icon: ImageVector = Icons.Default.Add,
    text: String,
    shape: Shape = FloatingActionButtonDefaults.shape,
    colors: List<Color>,
    contentDescription: String, )
{
    ExtendedFloatingActionButton(
        onClick = onClick,
        containerColor = colors[1],
        shape = shape,
        elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 0.dp),
        modifier = modifier
            .border(
                width = 1.dp,
                color = colors[2],
                shape = shape,
            ),
        icon = {
            Icon(
                imageVector = icon,
                tint = colors[0],
                contentDescription = contentDescription,
            )
        },
        text = {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = colors[0],
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.sp,
                )
            )
        }
    )
}