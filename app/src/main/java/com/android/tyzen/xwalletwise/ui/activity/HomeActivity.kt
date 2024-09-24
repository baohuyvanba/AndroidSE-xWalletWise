package com.android.tyzen.xwalletwise.ui.activity

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.tyzen.xwalletwise.R
import com.android.tyzen.xwalletwise.model.user.UserPreferences

import com.android.tyzen.xwalletwise.ui.fragment.BalanceSection
import com.android.tyzen.xwalletwise.ui.fragment.DetailedBalanceSection
import com.android.tyzen.xwalletwise.ui.fragment.NormalIconLabelButton
import com.android.tyzen.xwalletwise.ui.activity.category.CategoryCard
import com.android.tyzen.xwalletwise.ui.activity.transaction.TransactionCard
import com.android.tyzen.xwalletwise.ui.fragment.FABTransaction
import com.android.tyzen.xwalletwise.ui.fragment.FABTransactionCircle
import com.android.tyzen.xwalletwise.ui.fragment.FAButtonCircle
import com.android.tyzen.xwalletwise.ui.fragment.NormalIconButton
import com.android.tyzen.xwalletwise.ui.fragment.QuickAccessBar
import com.android.tyzen.xwalletwise.ui.viewmodel.HomeUiState
import com.android.tyzen.xwalletwise.ui.viewmodel.HomeViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.sin

/**
 * Home Screen -------------------------------------------------------------------------------------
 */
@SuppressLint("UseOfNonLambdaOffsetOverload")
@OptIn(ExperimentalMaterial3Api::class)
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

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp

    val scope = rememberCoroutineScope()
    var currency by rememberSaveable { mutableStateOf("") }
    LaunchedEffect(key1 = Unit) {
        scope.launch {
            currency = userPreferences.currency.first()
        }
    }

    //FAB animation
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
        modifier = Modifier
            .background(Color.Transparent)
            .padding(bottom = (screenHeight * 0.06).dp),
        floatingActionButton = {
            FABTransaction(
                modifier = Modifier,
                onClick  = {
                    expandedState.value = !expandedState.value
                },
                icon = if (!expandedState.value) Icons.Default.Add else Icons.Default.Close,
                containerColor = Color(0xFF91E9CF).copy(alpha = 0.3f),
                borderColor = Color(0xFF196B52).copy(0.5f),
                contentDescription = "Add Transaction",
            )

            if (expandedState.value)
            {
                for (i in 0 until numExpandedFab)
                {
                    val fabAngle = rotationAngle + (i + 1) * angleIncrement

                    Box(
                        modifier = Modifier
                            .offset(
                                x = animateFloatAsState(
                                    targetValue = 100 * sin(Math.toRadians(fabAngle.toDouble())).toFloat(),
                                    animationSpec = tween(durationMillis = 300), label = "", ).value.dp,
                                y = animateFloatAsState(
                                    targetValue = 100 * cos(Math.toRadians(fabAngle.toDouble())).toFloat(),
                                    animationSpec = tween(durationMillis = 300), label = "", ).value.dp
                            ),
                    ) {
                        when (i) {
                            0 -> FABTransactionCircle(
                                modifier = Modifier.rotate(fabAngle),
                                onClick = {
                                    onNavigateToTransactionDetail.invoke(-1)
                                },
                                icon = Icons.Default.Edit,
                                containerColor = Color(0xFF91E9CF).copy(alpha = 0.3f),
                                borderColor = Color(0xFF196B52).copy(0.5f),
                                contentDescription = "Manual",
                            )
                            1 -> FABTransactionCircle(
                                modifier = Modifier.rotate(fabAngle),
                                onClick = {
                                    onNavigateToTransactionDetail.invoke(-2)
                                },
                                icon = ImageVector.vectorResource(R.drawable.ic_receipt_scan),
                                containerColor = Color(0xFF91E9CF).copy(alpha = 0.3f),
                                borderColor = Color(0xFF196B52).copy(0.5f),
                                contentDescription = "OCR",
                            )

                            2 -> FABTransactionCircle(
                                modifier = Modifier.rotate(fabAngle),
                                onClick = {
                                    onNavigateToTransactionDetail.invoke(-3)
                                },
                                icon = ImageVector.vectorResource(R.drawable.ic_transaction_text),
                                containerColor = Color(0xFF91E9CF).copy(alpha = 0.3f),
                                borderColor = Color(0xFF196B52).copy(0.5f),
                                contentDescription = "Text",
                            )
                        }
                    }
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End, )
    { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF6be5ba).copy(alpha = 0.8f),
                            Color(0xFFc7e1fc).copy(alpha = 0.8f),
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