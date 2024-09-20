package com.android.tyzen.xwalletwise.ui.viewmodel.transaction

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.tyzen.xwalletwise.model.transactionDB.TransactionWithCategory
import com.android.tyzen.xwalletwise.repository.transaction.TransactionWithCategoryRepository
import com.android.tyzen.xwalletwise.util.filterListTransaction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

data class TransactionUiState(
    val transactions: List<TransactionWithCategory> = emptyList(),
    val totalIncome : Double = 0.0,
    val totalOutcome: Double = 0.0,
    val totalBalance: Double = 0.0,
    //Filter
    val filterTime: String = ""
)

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val transactionWithCategoryRepository: TransactionWithCategoryRepository
) : ViewModel() {
    var transactionUiState by mutableStateOf(TransactionUiState())
        private set

    init {
        getTransactions()
        getIncome()
        getOutcome()
        getBalance()
    }

    /**
     * Get all transactions with category ==========================================================
     */
    private fun getTransactions()
    {
        viewModelScope.launch {
            transactionWithCategoryRepository.allTransactionsWithCategories.collectLatest {
                transactionUiState = transactionUiState.copy(transactions = it)
            }
        }
    }

    /**
     * Calculate functions =========================================================================
     */
    //Income ---------------------------------------------------------------------------------------
    private fun getIncome()
    {
        viewModelScope.launch {
            transactionWithCategoryRepository.totalIncome.collectLatest {
                transactionUiState = transactionUiState.copy(totalIncome = it)
            }
        }
    }

    //Outcome
    private fun getOutcome()
    {
        viewModelScope.launch {
            transactionWithCategoryRepository.totalExpense.collectLatest {
                transactionUiState = transactionUiState.copy(totalOutcome = it)
            }
        }
    }

    //Balance
    private fun getBalance()
    {
        viewModelScope.launch {
            transactionWithCategoryRepository.totalBalance.collectLatest {
                transactionUiState = transactionUiState.copy(totalBalance = it)
            }
        }
    }

    /**
     * Filter functions ----------------------------------------------------------------------------
     */
    fun onFilterListTransactionTimeChange(filterTime: String)
    {
        transactionUiState = transactionUiState.copy(filterTime = filterTime)
        filterTransactionsByTime(filterTime)
    }

    private fun filterTransactionsByTime(filterTime: String)
    {
        when (filterTime)
        {
            filterListTransaction[0] //Today
            -> {
                viewModelScope.launch {
                    transactionWithCategoryRepository.getTransactionsWithCategoriesByDay(Date().time).collectLatest {
                        transactionUiState = transactionUiState.copy(transactions = it)
                    }
                }
            }
            filterListTransaction[1] //This week
            -> {
                viewModelScope.launch {
                    transactionWithCategoryRepository.getTransactionsWithCategoriesByWeek(Date().time).collectLatest {
                        transactionUiState = transactionUiState.copy(transactions = it)
                    }
                }
            }
            filterListTransaction[2] //This month
            -> {
                viewModelScope.launch {
                    transactionWithCategoryRepository.getTransactionsWithCategoriesByMonth(Date().time).collectLatest {
                        transactionUiState = transactionUiState.copy(transactions = it)
                    }
                }
            }
            filterListTransaction[3] //This year
            -> {
                viewModelScope.launch {
                    transactionWithCategoryRepository.getTransactionsWithCategoriesByYear(Date().time).collectLatest {
                        transactionUiState = transactionUiState.copy(transactions = it)
                    }
                }
            }
            else //All
            -> {
                getTransactions()
            }
        }
    }
}