package com.android.tyzen.xwalletwise.ui.activity.category

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.tyzen.xwalletwise.R
import com.android.tyzen.xwalletwise.model.category.Category
import com.android.tyzen.xwalletwise.model.user.UserPreferences
import com.android.tyzen.xwalletwise.ui.fragment.BalanceSection
import com.android.tyzen.xwalletwise.ui.fragment.FAButton
import com.android.tyzen.xwalletwise.ui.fragment.FilterRow
import com.android.tyzen.xwalletwise.ui.viewmodel.category.CategoryViewModel
import com.android.tyzen.xwalletwise.util.categoryIconsList
import com.android.tyzen.xwalletwise.util.filterListCategoryType
import com.android.tyzen.xwalletwise.util.formatBalance
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Composable
fun CategoriesListScreen(
    categoryViewModel: CategoryViewModel = hiltViewModel(),
    userPreferences: UserPreferences = hiltViewModel(),
    onCategoryClick: (Int) -> Unit, )
{
    val categoryUiState = categoryViewModel.categoryUiState

    val scope = rememberCoroutineScope()
    var currency by rememberSaveable { mutableStateOf("") }
    LaunchedEffect(key1 = Unit) {
        scope.launch {
            currency = userPreferences.currency.first()
        }
    }

    Scaffold(
        floatingActionButton = {
            FAButton(
                modifier = Modifier.offset(y = (-48).dp),
                onClick = { onCategoryClick.invoke(-1) },
                icon = Icons.Default.Add,
                text = "Add Category",
                contentDescription = "Add category"
            )
        },
        floatingActionButtonPosition = FabPosition.End,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF6be5ba).copy(alpha = 0.8f),
                            Color(0xFFc7e1fc).copy(alpha = 0.8f),
                            //Color.White, Color.White, Color.White, Color.White, Color.White,
                        )
                    )
                ),
        ) {
            /**
             * BUDGET ==============================================================================
             */
            BalanceSection(
                title = "Budget",
                balance = categoryUiState.totalBudgets,
                currency = currency,
                showTitle = true,
                showCurrencyBackground = true,
                currencyBackgroundColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
            )

            /**
             * CATEGORIES LIST =====================================================================
             */
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
                        .background(Color.Transparent),
                )

                val state = rememberLazyListState()
                LazyColumn(state = state,)
                {
                    //FILTER ROW -------------------------------------------------------------------
                    item {
                        FilterRow(
                            filterOptions = filterListCategoryType,
                            selectedFilter = categoryUiState.filterType,
                            onFilterSelected = { categoryViewModel.onFilterListCategoryTypeChange(it) })
                    }

                    //DIVIDER ----------------------------------------------------------------------
                    item {
                        HorizontalDivider(
                            modifier = Modifier
                                .padding(start = 8.dp, end = 8.dp, top = 12.dp, bottom = 8.dp),
                            thickness = 1.dp, color = Color.Gray.copy(alpha = 0.5f),
                        )
                    }

                    //CATEGORIES -------------------------------------------------------------------
                    items(categoryUiState.categories) { category ->
                        CategoryCard(
                            category = category,
                            totalExpenses = categoryViewModel.getTotalExpenses(category.id),
                            onCategoryClick = {
                                onCategoryClick.invoke(category.id)
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
 * CATEGORY CARD ===================================================================================
 */
@Composable
fun CategoryCard(
    category: Category,
    totalExpenses: Flow<Double>,
    onCategoryClick: () -> Unit,
    containerColor: Color = Color.White.copy(alpha = 0.2f),
    borderColor: Color = Color.White.copy(alpha = 0.5f),
    paddingValues: List<Dp> = listOf(8.dp, 8.dp, 8.dp, 8.dp), )
{
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCategoryClick.invoke() }
            .padding(top = paddingValues[0], bottom = paddingValues[1],
                     start = paddingValues[2], end = paddingValues[3], ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        ),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
        ),
        border = BorderStroke(
            width = 1.dp,
            color = borderColor,
        ),
    ) {
        val totalCategoryExpenses by totalExpenses.collectAsState(initial = 0.0)

        Row(
            modifier = Modifier
                .fillMaxSize()
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
                    painter = painterResource(
                        id = categoryIconsList[category.icon] ?: R.drawable.ic_category_other
                    ),
                    contentDescription = "Category Icon: $category.icon"
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
                    //TITLE ------------------------------------------------------------------------
                    Text(
                        text = category.title,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontSize = 18.sp
                        ),
                    )

                    //PROGRESS BAR -----------------------------------------------------------------
                    if (category.budget > 0.0)
                    {
                        Spacer(modifier = Modifier.height(8.dp))

                        val progress = (totalCategoryExpenses / category.budget).toFloat()

                        val progressColor = when {
                            progress < 0.5f -> Color(0x804CAF50)
                            progress < 0.8f -> Color(0xCCFFC107)
                            else -> Color(0xFFF44336)
                        }

                        LinearProgressIndicator(
                            progress = { progress },
                            modifier = Modifier.fillMaxWidth(0.6f),
                            color = progressColor.copy(alpha = 0.5f),
                            trackColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f),
                        )
                        Spacer(modifier = Modifier.height(8.dp))
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
                        text = formatBalance(totalCategoryExpenses),
                        maxLines = 1,
                        style = MaterialTheme.typography.titleMedium,
                    )

                    if (category.budget > 0.0)
                    {
                        Text(
                            text = formatBalance(category.budget),
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                            ),
                        )
                    }
                }
            }

        }
    }
}