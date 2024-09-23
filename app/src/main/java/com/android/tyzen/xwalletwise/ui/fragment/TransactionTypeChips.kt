package com.android.tyzen.xwalletwise.ui.fragment

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import com.android.tyzen.xwalletwise.R
import com.android.tyzen.xwalletwise.model.transaction.TransactionType
import com.android.tyzen.xwalletwise.ui.theme.WalletWiseTheme

@Composable
@Preview(showBackground = true)
fun PreviewTransactionTypeChipsGroup() {
    WalletWiseTheme {
        Column(
            modifier = Modifier.background(Color.Gray)
        ) {
            TransactionTypeChipsGroup(
                readOnly = false,
                initialType = TransactionType.INCOME,
                onTransactionTypeSet = { }
            )
        }
    }
}

//Transaction Type ---------------------------------------------------------------------------------
@Composable
fun TransactionTypeChipsGroup(
    readOnly: Boolean,
    initialType: TransactionType,
    onTransactionTypeSet: (TransactionType) -> Unit)
{
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        contentAlignment = Alignment.Center, )
    {
        Row (
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,)
        {
            TransactionTypeChip(
                text = "Income",
                painterId = R.drawable.ic_transaction_income,
                iconColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.8f),
                isSelected = initialType == TransactionType.INCOME,
                onTransactionTypeSelect = {
                    onTransactionTypeSet(TransactionType.INCOME)
                },
                backgroundColors = listOf(
                    Color(0xFF196B52).copy(alpha = 0.5f),
                    MaterialTheme.colorScheme.surfaceVariant,
                ),
                textColors = listOf(
                    MaterialTheme.colorScheme.onPrimary,
                    MaterialTheme.colorScheme.surfaceVariant,
                ),
            )

            Spacer(modifier = Modifier.width(8.dp))

            TransactionTypeChip(
                text = "Expense",
                painterId = R.drawable.ic_transaction_expense,
                iconColor = MaterialTheme.colorScheme.error.copy(alpha = 0.8f),
                isSelected = initialType == TransactionType.EXPENSE,
                onTransactionTypeSelect = {
                    onTransactionTypeSet(TransactionType.EXPENSE)
                },
                backgroundColors = listOf(
                    Color(0xFFBA1A1A).copy(alpha = 0.5f),
                    MaterialTheme.colorScheme.surfaceVariant,
                ),
                textColors = listOf(
                    MaterialTheme.colorScheme.onPrimary,
                    MaterialTheme.colorScheme.surfaceVariant,
                ),
            )
        }
    }
}

@Composable
fun TransactionTypeChip(
    text: String,
    painterId: Int,
    iconColor: Color,
    isSelected: Boolean,
    onTransactionTypeSelect: () -> Unit,
    backgroundColors: List<Color>,
    textColors: List<Color>, )
{
    val backgroundColor = backgroundColors[if (isSelected) 0 else 1]
    val textColor = textColors[if (isSelected) 0 else 1]

    Box(
        modifier = Modifier
            .wrapContentSize()
            .background(
                color = if (isSelected) backgroundColor else Color.Transparent,
                shape = FilterChipDefaults.shape,
            )
            .border(
                width = 1.dp,
                color = Color.White.copy(0.5f),
                shape = FilterChipDefaults.shape,
            )
            .clip(FilterChipDefaults.shape),
    ) {
        FilterChip(
            selected = isSelected,
            onClick = {
                onTransactionTypeSelect()
            },
            label = {
                Text(
                    text = text,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize * 0.8,
                    ),
                    color = textColor,
                )
            },
            leadingIcon = {
                Image(
                    painter = painterResource(id = painterId),
                    contentDescription = text,
                    colorFilter =
                        if (!isSelected)
                            ColorFilter.tint(iconColor)
                        else
                            ColorFilter.tint(Color.White),
                )
            },
            colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = Color.White.copy(0.2f)
            ),
            modifier = Modifier
                .height(48.dp)
        )
    }
}