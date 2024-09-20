package com.android.tyzen.xwalletwise.ui.viewmodel.category

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.android.tyzen.xwalletwise.model.category.Category
import com.android.tyzen.xwalletwise.model.transaction.TransactionType
import com.android.tyzen.xwalletwise.repository.transaction.TransactionWithCategoryRepository
import com.android.tyzen.xwalletwise.util.filterListCategoryType
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CategoryUiState(
    val categories: List<Category> = emptyList(),
    val totalBudgets: Double = 0.0,
    //Filter
    val filterType: String = "",
)

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val transactionWithCategoryRepository: TransactionWithCategoryRepository
) : ViewModel() {
    var categoryUiState by mutableStateOf(CategoryUiState())
        private set

    init {
        getCategories()
        getTotalBudget()
    }

    /**
     * CATEGORY RELATED FUNCTIONS ==================================================================
     */
    //GET ALL CATEGORIES
    private fun getCategories()
    {
        viewModelScope.launch {
            transactionWithCategoryRepository.allCategories.collectLatest {
                categoryUiState = categoryUiState.copy(categories = it)
            }
        }
    }

    //GET TOTAL BUDGETS
    private fun getTotalBudget(){
        viewModelScope.launch {
            transactionWithCategoryRepository.totalBudgets.collectLatest {
                categoryUiState = categoryUiState.copy(totalBudgets = it)
            }
        }
    }

    //GET TOTAL OUTCOME
    fun getTotalExpenses(categoryId: Int): Flow<Double> {
        return transactionWithCategoryRepository.updateTotalExpenses(categoryId)
    }

    /**
     * Filter functions ----------------------------------------------------------------------------
     */
    fun onFilterListCategoryTypeChange(filterType: String) {
        categoryUiState = categoryUiState.copy(filterType = filterType)
        filterCategoriesByType(filterType)
    }

    private fun filterCategoriesByType(filterType: String)
    {
        when (filterType) {
            filterListCategoryType[0] //Income
            -> {
                viewModelScope.launch {
                    transactionWithCategoryRepository
                        .getCategoryByType(TransactionType.INCOME)
                        .collectLatest {
                            categoryUiState = categoryUiState.copy(categories = it)
                        }
                }
            }

            filterListCategoryType[1] //Expense
            -> {
                viewModelScope.launch {
                    transactionWithCategoryRepository
                        .getCategoryByType(TransactionType.EXPENSE)
                        .collectLatest {
                            categoryUiState = categoryUiState.copy(categories = it)
                        }
                }
            }

            else -> {
                getCategories()
            }
        }
    }
}