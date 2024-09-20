package com.android.tyzen.xwalletwise.ui.activity.category

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.FabPosition
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
//
import com.android.tyzen.xwalletwise.R
import com.android.tyzen.xwalletwise.ui.fragment.FAButton
import com.android.tyzen.xwalletwise.ui.fragment.FormTextField
import com.android.tyzen.xwalletwise.ui.fragment.IconsBottomSheet
import com.android.tyzen.xwalletwise.ui.fragment.TransactionTypeChipsGroup
import com.android.tyzen.xwalletwise.ui.fragment.WalletWiseTopAppBar
import com.android.tyzen.xwalletwise.ui.viewmodel.category.CategoryDetailViewModel
import com.android.tyzen.xwalletwise.util.categoryIconsList
import com.android.tyzen.xwalletwise.util.formatBalance
import com.android.tyzen.xwalletwise.util.formatDouble

@Composable
fun CategoryDetailScreen(
    onBackClick: () -> Unit, )
{
    val categoryDetailViewModel: CategoryDetailViewModel = hiltViewModel()
    val categoryId = categoryDetailViewModel.categoryId
    val categoryDetailUiState = categoryDetailViewModel.categoryDetailUiState

    var showBottomSheet by remember { mutableStateOf(false) }
    var readOnly by remember { mutableStateOf(categoryId != -1) }

    Scaffold(
        topBar = {
            WalletWiseTopAppBar(
                title = if (categoryId != -1 && readOnly) "Category detail" else if (categoryId != -1) "Edit category" else "Add new Category", //categoryDetailUiState.title?: "Category",
                useIconForTitle = false,
                showNavigationButton = true,
                navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
                onNavigationClick = onBackClick,
                showActionButton = false,
            )
        },
        floatingActionButton = {
            if (categoryId != -1 && readOnly)
            {
                FAButton(
                    modifier = Modifier,
                    onClick = { readOnly = !readOnly },
                    icon = Icons.Default.Create,
                    text = "Edit",
                    contentDescription = "Edit Category"
                )
            }
            else if (!readOnly)
            {
                FAButton(
                    modifier = Modifier,
                    onClick = {
                        if (categoryId == -1)
                        {
                            categoryDetailViewModel.addCategory()
                        }
                        else
                        {
                            categoryDetailViewModel.updateCategory(categoryId)
                        }
                        onBackClick()
                    },
                    icon = Icons.Default.Check,
                    text = "Save Change",
                    contentDescription = "Save Category")
            }
        },
        floatingActionButtonPosition = if (readOnly) FabPosition.End else FabPosition.Center,
    )
    {innerpadding ->
        Box(modifier = Modifier.padding(innerpadding),)
        {
            Column(
                modifier = Modifier
                    .padding(24.dp), )
            {
                /**
                 * CATEGORY TYPE
                 */
                TransactionTypeChipsGroup(
                    readOnly = readOnly,
                    initialType = categoryDetailUiState.categoryType,
                    onTransactionTypeSet = {
                        categoryDetailViewModel.onCategoryTypeChange(it)
                    },
                )

                /**
                 * CATEGORY ICON & TITLE
                 */
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween, )
                {
                    //Category ICON
                    IconButton(
                        onClick = { if (!readOnly) showBottomSheet = true },
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .background(
                                color = MaterialTheme.colorScheme.surfaceVariant,
                                shape = CircleShape
                            ),
                    ) {
                        Image(
                            painter = painterResource(
                                id = categoryIconsList[categoryDetailUiState.icon]
                                    ?: R.drawable.ic_category_other //check
                            ),
                            contentDescription = "Category Icon: $categoryDetailUiState.icon",
                        )
                    }

                    Spacer(modifier = Modifier.weight(0.03f))

                    //Category TITLE
                    Box(
                        modifier = Modifier.weight(0.87f), )
                    {
                        FormTextField(
                            label = "Title",
                            value = categoryDetailUiState.title ?: "",
                            onValueChange = { categoryDetailViewModel.onTitleChange(it) },
                            readOnly = readOnly,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                //Category BUDGET
                FormTextField(
                    label = "Budget",
                    value = if (categoryDetailUiState.budget > 0 && readOnly) formatBalance(categoryDetailUiState.budget) else if (categoryDetailUiState.budget > 0) formatBalance(categoryDetailUiState.budget*1.0) else "",
                    onValueChange = { categoryDetailViewModel.onBudgetChange(formatDouble(it))},
                    readOnly = readOnly,
                )
                Spacer(modifier = Modifier.height(16.dp))

                //Category DESCRIPTION
                FormTextField(
                    label = "Description",
                    value = categoryDetailUiState.description ?: "",
                    onValueChange = { categoryDetailViewModel.onDescriptionChange(it) },
                    readOnly = readOnly,
                )
            }

            if (showBottomSheet)
            {
                IconsBottomSheet(
                    onIconSelected = {
                        categoryDetailViewModel.onIconChange(it)
                        showBottomSheet = false
                    },
                    onDismissRequest = { showBottomSheet = false },
                )

            }
        }
    }
}