package com.android.tyzen.xwalletwise.ui.fragment

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
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
import com.android.tyzen.xwalletwise.model.transaction.TransactionType

/**
 * Transaction Type ================================================================================
 */
@Composable
fun TransactionTypeChipsGroup(
    readOnly: Boolean,
    initialType: TransactionType,
    onTransactionTypeSet: (TransactionType) -> Unit)
{
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        contentAlignment = Alignment.Center, )
    {
        Row (
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,)
        {
            TransactionTypeChip(
                text = "Income",
                icon = ImageVector.vectorResource(R.drawable.ic_transaction_income),
                iconColor = MaterialTheme.colorScheme.primary.copy(0.8f),
                isSelected = initialType == TransactionType.INCOME,
                readOnly = readOnly,
                onTransactionTypeSelect = {
                    onTransactionTypeSet(TransactionType.INCOME)
                },
                backgroundColors = listOf(
                    MaterialTheme.colorScheme.secondary,
                    Color.Transparent,
                ),
                textColors = listOf(
                    MaterialTheme.colorScheme.onPrimary,
                    MaterialTheme.colorScheme.surfaceVariant,
                ),
            )

            Spacer(modifier = Modifier.width(8.dp))

            TransactionTypeChip(
                text = "Expense",
                icon = ImageVector.vectorResource(R.drawable.ic_transaction_expense),
                iconColor = MaterialTheme.colorScheme.error.copy(alpha = 0.8f),
                isSelected = initialType == TransactionType.EXPENSE,
                readOnly = readOnly,
                onTransactionTypeSelect = {
                    onTransactionTypeSet(TransactionType.EXPENSE)
                },
                backgroundColors = listOf(
                    MaterialTheme.colorScheme.error.copy(0.8f),
                    Color.Transparent,
                ),
                textColors = listOf(
                    MaterialTheme.colorScheme.onPrimary,
                    MaterialTheme.colorScheme.surfaceVariant,
                ),
            )
        }
    }
}

/**
 * Transaction Type Chip ===========================================================================
 */
@Composable
fun TransactionTypeChip(
    text: String,
    icon: ImageVector,
    iconColor: Color,
    isSelected: Boolean,
    readOnly: Boolean,
    onTransactionTypeSelect: () -> Unit,
    backgroundColors: List<Color>,
    textColors: List<Color>, )
{
    FilterChip(
        modifier = Modifier
            .wrapContentSize()
            .animateContentSize()
            .height(48.dp),
        selected = isSelected,
        onClick = {
            onTransactionTypeSelect()
        },
        label = {
            AnimatedVisibility(
                visible = !(readOnly && !isSelected),
                enter = expandIn(),
                exit  = shrinkHorizontally(),
            ) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize * 0.8,
                    ),
                )
            }
        },
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = text,
                tint = if (!isSelected) iconColor else Color.White,
            )
        },
        colors = FilterChipDefaults.filterChipColors(
            containerColor = backgroundColors[1],
            iconColor = Color.White,
            labelColor = textColors[1],
            //Selected
            selectedContainerColor = backgroundColors[0],
            selectedLabelColor = textColors[0],
            selectedLeadingIconColor = Color.White,
        ),
        border = BorderStroke(
            width = 1.dp,
            color = Color.Gray.copy(0.5f),
        ),
    )
}