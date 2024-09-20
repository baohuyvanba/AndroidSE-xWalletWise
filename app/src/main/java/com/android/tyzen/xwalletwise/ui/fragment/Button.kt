package com.android.tyzen.xwalletwise.ui.fragment

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.android.tyzen.xwalletwise.R
import com.android.tyzen.xwalletwise.ui.theme.WalletWiseTheme

/**
 * Normal button
 */
@Preview(showBackground = true)
@Composable
fun PreviewNormalButton()
{
    WalletWiseTheme {
        NormalButton(
            text = "Button",
            onClick = { /*TODO*/ }
        )
    }
}

@Composable
fun NormalButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    enabled: Boolean   = true,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color    = MaterialTheme.colorScheme.onPrimary )
{
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(backgroundColor, contentColor = contentColor),
        shape = RoundedCornerShape(50.dp) )
    {
        Text(text = text)
    }
}

/**
 * Icon button with label
 */
@Preview(showBackground = true)
@Composable
fun PreviewNormalIconLabelButton()
{
    WalletWiseTheme {
        NormalIconLabelButton(
            icon = ImageVector.vectorResource(id = R.drawable.ic_category),
            text = "Category",
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White),
            onClick = { /*TODO*/ }
        )
    }
}

@Composable
fun NormalIconLabelButton(
    icon: Any,
    text: String,
    colors: ButtonColors,
    onClick: () -> Unit,)
{
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(50),
        colors = colors )
    {
        when (icon)
        {
            is ImageVector -> {
                Icon(
                    imageVector = icon,
                    contentDescription = text, )
            }
            is Painter -> {
                Icon(
                    painter = icon,
                    contentDescription = text,)
            }
            else -> throw IllegalArgumentException("Unsupported icon type")
        }
        Spacer(Modifier.width(8.dp))
        Text(text = text)
    }
}

/**
 * Icon button
 */
@Preview(showBackground = true)
@Composable
fun PreviewNormalIconButton() {
    WalletWiseTheme {
        NormalIconButton(
            icon = ImageVector.vectorResource(id = R.drawable.ic_category),
            contentDescription = "Category",
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            ),
            onClick = { /*TODO*/ })
    }
}

@Composable
fun NormalIconButton(
    icon: Any,
    colors: ButtonColors,
    contentDescription: String = "",
    onClick: () -> Unit,)
{
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(50),
        colors = colors, )
    {
        when (icon)
        {
            is ImageVector -> {
                Icon(
                    imageVector = icon,
                    contentDescription = contentDescription, )
            }
            is Painter -> {
                Icon(
                    painter = icon,
                    contentDescription = contentDescription,)
            }
            else -> throw IllegalArgumentException("Unsupported icon type")
        }
    }
}

//--------------------------------------------------------------------------------------------------
@Preview(showBackground = true)
@Composable
fun PreviewSpecialButton() {
    WalletWiseTheme {
        SpecialGlassButton(
            text = "Glass Button",
            onClick = { /*TODO*/ }
        )
    }
}

@Composable
fun SpecialGlassButton(
    modifier: Modifier = Modifier,
    text: String = "Start your journey",
    onClick: () -> Unit,
    blurRadius: Dp = 8.dp,
    buttonWidth: Dp = 200.dp,
    buttonHeight: Dp = 60.dp, )
{
    // Outer Box containing the background gradient and the button
    Box(
        modifier = modifier
            .width(buttonWidth) // Adjust width based on parameter
            .height(buttonHeight) // Adjust height based on parameter
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Black.copy(alpha = 0.10f),
                        Color.Black.copy(alpha = 0.05f)
                    )
                ),
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        // Inner Box for the blur background
        Box(
            modifier = Modifier
                .matchParentSize()  // Match size with the parent Box
                .blur(blurRadius)   // Apply blur effect
                .background(
                    Color.Gray.copy(alpha = 0.2f),
                    RoundedCornerShape(10.dp)
                )
        )

        // Transparent button with a border
        OutlinedButton(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(buttonHeight),  // Set height based on parameter
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.5f)), // Semi-transparent border
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.Transparent // Transparent button background
            )
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                ),
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f) // Text color
            )
        }
    }
}