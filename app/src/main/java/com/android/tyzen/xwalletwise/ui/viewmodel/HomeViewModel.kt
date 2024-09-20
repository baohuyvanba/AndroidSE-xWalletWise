package com.android.tyzen.xwalletwise.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.android.tyzen.xwalletwise.model.category.Category
import com.android.tyzen.xwalletwise.model.transactionDB.TransactionWithCategory
import com.android.tyzen.xwalletwise.repository.transaction.TransactionWithCategoryRepository

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    //Transaction
    val transactions: List<TransactionWithCategory> = emptyList(),
    val totalExpense: Double = 0.0,
    val totalIncome: Double = 0.0,
    val totalBalance: Double = 0.0,
    //Category
    val categories: List<Category> = emptyList(),
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val transactionWithCategoryRepository: TransactionWithCategoryRepository
) : ViewModel() {
    var homeUiState by mutableStateOf(HomeUiState())
        private set

    init {
        getTransactions()
        getIncome()
        getExpense()
        getBalance()
        getCategories()
    }

    /**
     * TRANSACTION RELATED FUNCTIONS ===============================================================
     */

    /**
     * Get all transactions with category
     */
    private fun getTransactions()
    {
        viewModelScope.launch {
            transactionWithCategoryRepository.allTransactionsWithCategories.collectLatest {
                homeUiState = homeUiState.copy(transactions = it)
            }
        }
    }

    /**
     * Calculate functions
     */
    //Income
    private fun getIncome()
    {
        viewModelScope.launch {
            transactionWithCategoryRepository.totalIncome.collectLatest {
                homeUiState = homeUiState.copy(totalIncome = it)
            }
        }
    }

    //Expense
    private fun getExpense()
    {
        viewModelScope.launch {
            transactionWithCategoryRepository.totalExpense.collectLatest {
                homeUiState = homeUiState.copy(totalExpense = it)
            }
        }
    }

    //Balance
    private fun getBalance()
    {
        viewModelScope.launch {
            transactionWithCategoryRepository.totalBalance.collectLatest {
                homeUiState = homeUiState.copy(totalBalance = it)
            }
        }
    }

    /**
     * CATEGORY RELATED FUNCTIONS ==================================================================
     */
    private fun getCategories()
    {
        viewModelScope.launch {
            transactionWithCategoryRepository.allCategories.collectLatest {
                homeUiState = homeUiState.copy(categories = it)
            }
        }
    }

    /**
     * Calculate functions
     */
    fun getTotalExpenses(categoryId: Int): Flow<Double> {
        return transactionWithCategoryRepository.updateTotalExpenses(categoryId)
    }
}