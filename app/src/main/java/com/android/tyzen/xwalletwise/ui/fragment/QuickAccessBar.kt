package com.android.tyzen.xwalletwise.ui.fragment

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.android.tyzen.xwalletwise.R

/**
 * QUICK ACCESS Bar ================================================================================
 */
@Composable
fun QuickAccessBar(
    onAnalysisClick: () -> Unit = {},
    onAIChatClick: () -> Unit = {},
    onRemindClick: () -> Unit = {})
{
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically, )
    {
        //Analysis Button
        IconLabelQuickAccessButton(
            onClick = onAnalysisClick,
            icon = ImageVector.vectorResource(R.drawable.ic_analytics_filled),
            label = "Analysis",
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary.copy(0.9f),
                contentColor   = Color.White,
            ),
            border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
        )

        //AI Chat Button (Icon Only)
        IconQuickAccessButton(
            onClick = onAIChatClick,
            icon = ImageVector.vectorResource(R.drawable.ic_chat_finance),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor   = MaterialTheme.colorScheme.primary,
            ),
            border = BorderStroke(
                width = 2.dp,
                color = MaterialTheme.colorScheme.primary,
            ),
            contentDescription = "AI Chat",
        )

        //Remind Button
        IconLabelQuickAccessButton(
            onClick = onRemindClick,
            icon = ImageVector.vectorResource(R.drawable.ic_remind_event),
            label = "Remind",
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary.copy(0.9f),
                contentColor   = Color.White,
            ),
            border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
        )
    }
}

/**
 * Icon + Label Button Quick-Access =================================================================
 */
@Composable
fun IconLabelQuickAccessButton(
    icon: ImageVector,
    label: String,
    colors: ButtonColors,
    border: BorderStroke,
    onClick: () -> Unit,)
{
    Button(
        onClick = onClick,
        shape   = CircleShape,
        border  = border,
        colors  = colors,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),)
    {
        Icon(
            imageVector = icon,
            contentDescription = label,
        )

        Spacer(Modifier.width(4.dp))

        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Bold,
            ),
        )
    }
}

/**
 * Icon Button Quick-Access ========================================================================
 */
@Composable
fun IconQuickAccessButton(
    onClick: () -> Unit,
    icon: ImageVector,
    colors: ButtonColors,
    border: BorderStroke,
    contentDescription: String, )
{
    Button(
        onClick = onClick,
        shape   = CircleShape,
        border  = border,
        colors  = colors,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),)
    {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
        )
    }
}