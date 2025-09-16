package com.android.tyzen.xwalletwise.ui.activity

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.tyzen.xwalletwise.model.user.UserPreferences

import com.android.tyzen.xwalletwise.ui.fragment.BalanceSection
import com.android.tyzen.xwalletwise.ui.fragment.DetailedBalanceSection
import com.android.tyzen.xwalletwise.ui.activity.category.CategoryCard
import com.android.tyzen.xwalletwise.ui.activity.transaction.TransactionCard
import com.android.tyzen.xwalletwise.ui.fragment.QuickAccessBar
import com.android.tyzen.xwalletwise.ui.theme.*
import com.android.tyzen.xwalletwise.ui.viewmodel.HomeUiState
import com.android.tyzen.xwalletwise.ui.viewmodel.HomeViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/**
 * Home Screen -------------------------------------------------------------------------------------
 */
@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
fun HomeScreen(
    //transaction
    onNavigateToTransactionList: () -> Unit = {},
    onNavigateToTransactionDetail: (Long) -> Unit = {},
    //category
    onNavigateToCategoryList: () -> Unit = {},
    onNavigateToCategoryDetail: (Int) -> Unit = {},
    quickAccessOnAnalysisClick:() -> Unit = {},
    quickAccessOnAIChatClick: () -> Unit = {},
    quickAccessOnRemindClick: () -> Unit = {},)
{
    val userPreferences: UserPreferences = hiltViewModel()
    val homeViewModel: HomeViewModel = hiltViewModel()
    val homeUiState = homeViewModel.homeUiState

    val scope = rememberCoroutineScope()
    var currency by rememberSaveable { mutableStateOf("") }
    LaunchedEffect(key1 = Unit) {
        scope.launch {
            currency = userPreferences.currency.first()
        }
    }

    Scaffold(
        modifier = Modifier
            .background(Color.Transparent),
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            secondaryContainerLight,
                            Color.White
                        )
                    )
                ),
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .background(Color.Transparent), )
            {
                //BALANCE SECTION ------------------------------------------------------------------
                item {
                    Spacer(
                        modifier = Modifier
                            .height(16.dp)
                            .fillMaxWidth()
                            .background(Color.Transparent)
                    )

                    BalanceSection(
                        title = "Balance",
                        balance = homeUiState.totalBalance,
                        currency = currency,
                        showTitle = false,
                        showCurrencyBackground = true,
                        currencyBackgroundColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                    )
                }
                //DETAILED BALANCE -----------------------------------------------------------------
                item {
                    DetailedBalanceSection(
                        incomeAmount  = homeUiState.totalIncome,
                        outcomeAmount = homeUiState.totalExpense,
                        onIncomeClick = {
                            onNavigateToTransactionList()
                        },
                        onOutcomeClick = {
                            onNavigateToTransactionList()
                        },
                    )
                }
                //SHORTCUTS ------------------------------------------------------------------------
                item {
                    Column (
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .background(
                                color = Color.White.copy(alpha = 0.65f),
                                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                            )
                            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)), )
                    {
                        Spacer(
                            modifier = Modifier
                                .height(16.dp)
                                .fillMaxWidth()
                                .background(Color.Transparent)
                        )

                        //QUICK ACCESS BAR ---------------------------------------------------------
                        QuickAccessBar(
                            onAnalysisClick = { quickAccessOnAnalysisClick() },
                            onAIChatClick   = { quickAccessOnAIChatClick()   },
                            onRemindClick   = { quickAccessOnRemindClick()   },
                        )

                        HorizontalDivider(
                            modifier = Modifier
                                .padding(start = 8.dp, end = 8.dp)
                                .padding(top = 16.dp, bottom = 0.dp),
                            thickness = 1.dp, color = Color.Gray.copy(alpha = 0.5f)
                        )

                        //QUICK VIEW OF TRANSACTIONS LIST ------------------------------------------
                        TransactionShortCutView(
                            title = "Transaction",
                            homeUiState = homeUiState,
                            onTransactionClick = onNavigateToTransactionDetail,
                            onSeeAllClick = onNavigateToTransactionList,
                        )

                        //QUICK VIEW OF CATEGORIES LIST --------------------------------------------
                        CategoryShortCutView(
                            title = "Category",
                            homeViewModel = homeViewModel,
                            homeUiState = homeUiState,
                            onCategoryClick = onNavigateToCategoryDetail,
                            onSeeAllClick = onNavigateToCategoryList,
                        )

                        //Spacer
                        Spacer(
                            modifier = Modifier
                                .height(60.dp)
                                .fillMaxWidth()
                                .background(Color.Transparent)
                        )
                    }
                }
            }
        }
    }
}

/**
 * SHORTCUTS View ==================================================================================
 */
@Composable
fun TransactionShortCutView(
    title: String,
    homeUiState: HomeUiState,
    onTransactionClick: (Long) -> Unit,
    onSeeAllClick: () -> Unit, )
{
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp), )
    {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(8.dp), )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = Color.LightGray.copy(alpha = 0.4f),
                    shape = RoundedCornerShape(8.dp)
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,)
        {
            if (homeUiState.transactions.isEmpty()) {
                Text(
                    text = "No recent transactions",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            else {
                Column {
                    homeUiState.transactions.take(3).forEach { transaction ->
                        TransactionCard(
                            transaction = transaction,
                            onTransactionClick = {
                                onTransactionClick.invoke(transaction.transaction.id)
                            },
                            containerColor = Color.White.copy(alpha = 0.4f),
                            borderColor = Color.White,
                            paddingValues = listOf(8.dp, 0.dp, 8.dp, 8.dp)
                        )
                    }
                }
            }

            TextButton(
                onClick = onSeeAllClick,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 4.dp), )
            {
                Text(
                    text = "See All",
                    color = MaterialTheme.colorScheme.secondary,)
            }
        }
    }
}

@Composable
fun CategoryShortCutView(
    title: String,
    homeViewModel: HomeViewModel,
    homeUiState: HomeUiState,
    onCategoryClick: (Int) -> Unit,
    onSeeAllClick: () -> Unit, )
{
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp), )
    {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(8.dp), )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = Color.LightGray.copy(alpha = 0.4f),
                    shape = RoundedCornerShape(8.dp)
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,)
        {
            if (homeUiState.transactions.isEmpty()) {
                Text(
                    text = "No category",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            else {
                Column {
                    homeUiState.categories.take(5).forEach { category ->
                        CategoryCard(
                            category = category,
                            totalExpenses = homeViewModel.getTotalExpenses(category.id),
                            onCategoryClick = {
                                onCategoryClick.invoke(category.id)
                            },
                            containerColor = Color.White.copy(alpha = 0.4f),
                            borderColor = Color.White,
                            paddingValues = listOf(8.dp, 0.dp, 8.dp, 8.dp)
                        )
                    }
                }
            }

            TextButton(
                onClick = onSeeAllClick,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 4.dp), )
            {
                Text(
                    text = "See All",
                    color = MaterialTheme.colorScheme.secondary,)
            }
        }
    }
}