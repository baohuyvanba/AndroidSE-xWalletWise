package com.android.tyzen.xwalletwise.ui.activity.transaction

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.tyzen.xwalletwise.R
import com.android.tyzen.xwalletwise.model.transaction.TransactionType

import com.android.tyzen.xwalletwise.model.transactionDB.TransactionWithCategory
import com.android.tyzen.xwalletwise.model.user.UserPreferences
import com.android.tyzen.xwalletwise.ui.fragment.BalanceSection
import com.android.tyzen.xwalletwise.ui.fragment.DetailedBalanceSection
import com.android.tyzen.xwalletwise.ui.fragment.FAButton
import com.android.tyzen.xwalletwise.ui.fragment.FAButtonCircle
import com.android.tyzen.xwalletwise.ui.fragment.FilterRow
import com.android.tyzen.xwalletwise.ui.viewmodel.transaction.TransactionViewModel
import com.android.tyzen.xwalletwise.util.categoryIconsList
import com.android.tyzen.xwalletwise.util.filterListTransaction
import com.android.tyzen.xwalletwise.util.formatBalance
import com.android.tyzen.xwalletwise.util.formatDate
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.sin

@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
fun TransactionsListScreen(
    transactionViewModel: TransactionViewModel = hiltViewModel(),
    userPreferences: UserPreferences = hiltViewModel(),
    onTransactionClick: (Long) -> Unit,)
{
    val transactionUiState = transactionViewModel.transactionUiState

    val scope = rememberCoroutineScope()
    var currency by rememberSaveable { mutableStateOf("") }
    LaunchedEffect(key1 = Unit) {
        scope.launch {
            currency = userPreferences.currency.first()
        }
    }

    //FABs Animation
    val expandedState = remember { mutableStateOf(false) }
    var rotationAngle by remember { mutableFloatStateOf(0f) }
    val numExpandedFab = 3
    val angleIncrement = 45f
    LaunchedEffect(expandedState.value) {
        rotationAngle =
            if (expandedState.value) {
                135f
            } else {
                0f
            }
    }

    Scaffold(
        floatingActionButton = {
            FAButton(
                modifier = Modifier
                    .offset(y = (-48).dp),
                onClick = {
                    expandedState.value = !expandedState.value
                },
                buttonColor =
                    if (!expandedState.value)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.secondary,
                icon =
                    if (!expandedState.value)
                        Icons.Default.Add else Icons.Default.Close,
                contentDescription = "Add Transaction", )

            if (expandedState.value) {
                for (i in 0 until numExpandedFab)
                {
                    val fabAngle = rotationAngle + (i + 1) * angleIncrement
                    val fabIndex = i

                    Box(
                        modifier = Modifier
                            .offset(
                                x = animateFloatAsState(
                                    targetValue = 100 * sin(Math.toRadians(fabAngle.toDouble())).toFloat(),
                                    animationSpec = tween(durationMillis = 300), label = "", ).value.dp,
                                y = animateFloatAsState(
                                    targetValue = 100 * cos(Math.toRadians(fabAngle.toDouble())).toFloat() - 48,
                                    animationSpec = tween(durationMillis = 300), label = "", ).value.dp
                            ),
                    ) {
                        when (fabIndex)
                        {
                            0 -> FAButtonCircle( // Manual
                                onClick = { onTransactionClick.invoke(-1) },
                                icon = Icons.Default.Edit,
                                buttonColor = MaterialTheme.colorScheme.primary,
                                contentDescription = "Manual",
                                modifier = Modifier.rotate(fabAngle), )

                            1 -> FAButtonCircle( // OCR
                                onClick = { onTransactionClick.invoke(-2) },
                                iconResId = R.drawable.ic_receipt_scan,
                                buttonColor = MaterialTheme.colorScheme.primary,
                                contentDescription = "Manual",
                                modifier = Modifier.rotate(fabAngle), )

                            2 -> FAButtonCircle( // Text
                                onClick = { onTransactionClick.invoke(-3) },
                                iconResId = R.drawable.ic_transaction_text,
                                buttonColor = MaterialTheme.colorScheme.primary,
                                contentDescription = "Manual",
                                modifier = Modifier.rotate(fabAngle), )
                        }
                    }
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End, )
    { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF6be5ba).copy(alpha = 0.8f),
                            Color(0xFFc7e1fc).copy(alpha = 0.8f),
                            Color.White, Color.White, Color.White,
                        )
                    )
                ),
        ) {
            /**
             * BALANCE SECTION =====================================================================
             */
            BalanceSection(
                title = "Balance",
                balance = transactionUiState.totalBalance,
                currency = currency,
                showTitle = true,
                showCurrencyBackground = true,
                currencyBackgroundColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
            )

            DetailedBalanceSection(
                incomeAmount = transactionUiState.totalIncome,
                outcomeAmount = transactionUiState.totalOutcome,
                onIncomeClick = {},
                onOutcomeClick = {},
            )

            /**
             * TRANSACTIONS LIST ===================================================================
             */
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(
                        color = Color.White.copy(alpha = 0.65f),
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    )
                    //.background(brush = Brush.verticalGradient(colors = listOf(Color(0xFF62cff4).copy(alpha = 0.3f), Color(0xFF2c67f2).copy(alpha = 0.3f),),), shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp), )
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)), )
            {
                Spacer(
                    modifier = Modifier
                        .height(16.dp)
                        .fillMaxWidth()
                        .background(Color.Transparent)
                )

                val state = rememberLazyListState()
                LazyColumn(state = state,)
                {
                    item {
                        FilterRow(
                            filterOptions = filterListTransaction,
                            selectedFilter = transactionUiState.filterTime,
                            onFilterSelected = {
                                transactionViewModel.onFilterListTransactionTimeChange(it)
                            }
                        )
                    }

                    item {
                        HorizontalDivider(
                            modifier = Modifier
                                .padding(start = 8.dp, end = 8.dp, top = 12.dp, bottom = 8.dp),
                            thickness = 1.dp, color = Color.Gray.copy(alpha = 0.5f),
                        )
                    }

                    items(transactionUiState.transactions) { transaction ->
                        TransactionCard(
                            transaction = transaction,
                            onTransactionClick = {
                                onTransactionClick.invoke(transaction.transaction.id)
                            },
                            containerColor = Color.White.copy(alpha = 0.2f),
                            borderColor    = Color.Gray.copy(alpha = 0.2f),
                            paddingValues  = listOf(8.dp, 0.dp, 8.dp, 8.dp),
                        )
                    }
                }
            }
        }
    }
}

/**
 * Transaction card --------------------------------------------------------------------------------
 */
@Composable
fun TransactionCard(
    transaction: TransactionWithCategory,
    onTransactionClick: () -> Unit,
    containerColor: Color = Color.White.copy(alpha = 0.2f),
    borderColor: Color = Color.White.copy(alpha = 0.5f),
    paddingValues: List<Dp> = listOf(8.dp, 8.dp, 8.dp, 8.dp), )
{
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onTransactionClick.invoke() }
            .padding(top = paddingValues[0], bottom = paddingValues[1],
                     start = paddingValues[2], end = paddingValues[3], ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
        ),
        border = BorderStroke(
            width = 1.dp,
            color = borderColor,
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,)
        {
            /**
             * CATEGORY ICON -----------------------------------------------------------------------
             */
            Box(
                modifier = Modifier
                    .weight(0.17f)
                    .aspectRatio(1f)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center,)
            {
                Image(
                    painter = painterResource(id = categoryIconsList[transaction.category.icon] ?: R.drawable.ic_category_other),
                    contentDescription = "Category Icon: ${transaction.category.icon}"
                )
            }

            Spacer(modifier = Modifier.weight(0.05f))

            /**
             * TITLE + PROGRESS BAR ----------------------------------------------------------------
             */
            Box(
                modifier = Modifier
                    .weight(1f)
                    .background(Color.Transparent), )
            {
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center, )
                {
                    //TITLE
                    Text(
                        text = transaction.transaction.transactionTitle,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontSize = MaterialTheme.typography.titleLarge.fontSize * 0.8, ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Box(
                        modifier = Modifier
                            .wrapContentWidth()
                            .background(
                                color = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.3f),
                                shape = RoundedCornerShape(4.dp),)
                    ) {
                        Text(
                            modifier = Modifier.padding(horizontal = 4.dp),
                            text = transaction.category.title,
                            color = MaterialTheme.colorScheme.onTertiary,
                            style = MaterialTheme.typography.bodySmall,
                        )
                    }
                }

                /**
                 * TOTAL EXPENSES & BUDGET -------------------------------------------------------------
                 */
                Column(
                    modifier = Modifier
                        .align(Alignment.CenterEnd),
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.Center, )
                {
                    Text(
                        text = formatBalance(transaction.transaction.amount),
                        maxLines = 1,
                        style = MaterialTheme.typography.titleMedium.copy(
                            color =
                            if (transaction.transaction.type == TransactionType.EXPENSE)
                                MaterialTheme.colorScheme.error
                            else
                                MaterialTheme.colorScheme.primary,
                        ),
                    )

                    Text(
                        text = formatDate(transaction.transaction.datetime),
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.tertiary,
                        ),
                    )
                }
            }

        }
    }
}