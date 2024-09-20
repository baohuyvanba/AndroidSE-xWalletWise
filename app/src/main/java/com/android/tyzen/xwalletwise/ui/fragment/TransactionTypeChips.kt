package com.android.tyzen.xwalletwise.ui.fragment

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

import com.android.tyzen.xwalletwise.R
import com.android.tyzen.xwalletwise.model.transaction.TransactionType

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
            if ((readOnly && initialType == TransactionType.INCOME) || !readOnly)
            {
                TransactionTypeChip(
                    text = "Income",
                    painterId = R.drawable.ic_transaction_income,
                    iconColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.8f),
                    type = TransactionType.INCOME,
                    isSelected = initialType == TransactionType.INCOME,
                    onTransactionTypeSelect = {
                        onTransactionTypeSet(TransactionType.INCOME)
                    }
                )
            }

            if (!readOnly)
            {
                Spacer(modifier = Modifier.width(8.dp))
            }

            if ((readOnly && initialType == TransactionType.EXPENSE) || !readOnly)
            {
                TransactionTypeChip(
                    text = "Expense",
                    painterId = R.drawable.ic_transaction_expense,
                    iconColor = MaterialTheme.colorScheme.error.copy(alpha = 0.8f),
                    type = TransactionType.EXPENSE,
                    isSelected = initialType == TransactionType.EXPENSE,
                    onTransactionTypeSelect = {
                        onTransactionTypeSet(TransactionType.EXPENSE)
                    }
                )
            }
        }
    }
}

@Composable
fun TransactionTypeChip(
    text: String,
    painterId: Int,
    iconColor: Color,
    type: TransactionType,
    isSelected: Boolean,
    onTransactionTypeSelect: () -> Unit, )
{
    val chipBackgroundColor =
        if (isSelected)
        {
            if (type == TransactionType.INCOME)
                MaterialTheme.colorScheme.tertiary.copy(alpha = 0.8f)
            else
                MaterialTheme.colorScheme.error.copy(alpha = 0.8f)
        }
        else
        {
            MaterialTheme.colorScheme.surfaceVariant
        }

    val textColor =
        if (isSelected)
        {
            MaterialTheme.colorScheme.onPrimary
        }
        else
        {
            MaterialTheme.colorScheme.surfaceVariant
        }

    FilterChip(
        selected = isSelected,
        onClick = { onTransactionTypeSelect() },
        label = {
            Text(
                text = text,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize   = MaterialTheme.typography.titleLarge.fontSize * 0.8,),
                color = textColor, )
        },
        leadingIcon = {
            Image(
                painter = painterResource(id = painterId),
                contentDescription = text,
                colorFilter = if (!isSelected) ColorFilter.tint(iconColor) else ColorFilter.tint(
                    Color.White),
            )
        },
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = chipBackgroundColor ),
        border = FilterChipDefaults.filterChipBorder(
            borderColor = chipBackgroundColor,
            selected    = isSelected,
            enabled     = true, ),
        modifier = Modifier
            .height(48.dp)
    )
}