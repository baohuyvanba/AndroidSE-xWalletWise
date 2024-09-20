package com.android.tyzen.xwalletwise.ui.viewmodel.transaction

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.android.tyzen.xwalletwise.model.category.Category
import com.android.tyzen.xwalletwise.model.transaction.Transaction
import com.android.tyzen.xwalletwise.model.transaction.TransactionType
import com.android.tyzen.xwalletwise.repository.transaction.TransactionWithCategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

data class TransactionDetailUiState (
    //Transaction values
    val transactionTitle: String = "",
    val transactionType: TransactionType = TransactionType.EXPENSE,
    val transactionAmount: Double = 0.0,
    val transactionDate: Date = Date(),
    val transactionDescription: String? = "",
    //Foreign key value
    val categoryIdFK: Int = if (transactionType == TransactionType.EXPENSE) 2 else 1,
    val category: Category = Category(0, "", "", transactionType),
    //Categories list
    val categories: List<Category> = emptyList(),
    //State values
    val isViewing: Boolean = false,
    val isDeleting: Boolean = false,
)

@HiltViewModel
class TransactionDetailViewModel @Inject constructor (
    savedStateHandle: SavedStateHandle,
    private val transactionWithCategoryRepository: TransactionWithCategoryRepository
): ViewModel() {
    val transactionId: Long = savedStateHandle["transactionId"] ?: 0

    var transactionDetailUiState by mutableStateOf(TransactionDetailUiState())
        private set

    //Get categories list
    init {
        getCategories()
    }

    private fun getCategories()
    {
        viewModelScope.launch {
            transactionWithCategoryRepository.allCategories.collectLatest { categories ->
                transactionDetailUiState = transactionDetailUiState.copy(categories = categories)
            }
        }
    }

    private fun getCategoriesByType(categoryType: TransactionType): Job
    {
        return viewModelScope.launch {
            transactionWithCategoryRepository
                .getCategoryByType(categoryType)
                .collectLatest { categories ->
                    transactionDetailUiState = transactionDetailUiState.copy(categories = categories)
                }
        }
    }

    //Get transaction for View mode
    init {
        if (transactionId > 0.toLong())
        {
            viewModelScope.launch {
                transactionWithCategoryRepository
                    .getTransactionWithCategoryById(transactionId)
                    .collectLatest { transactionWithCategory ->
                        transactionDetailUiState = transactionDetailUiState.copy(
                            transactionTitle  = transactionWithCategory.transaction.transactionTitle,
                            transactionType   = transactionWithCategory.transaction.type,
                            transactionAmount = transactionWithCategory.transaction.amount,
                            transactionDate   = transactionWithCategory.transaction.datetime,
                            transactionDescription = transactionWithCategory.transaction.transactionDescription,
                            categoryIdFK      = transactionWithCategory.category.id,
                            category          = transactionWithCategory.category,
                        )
                    }
            }
        }
    }

    /**
     * On change functions -------------------------------------------------------------------------
     */
    //NAME
    fun onTransactionTitleChanged(transactionTitle: String) {
        transactionDetailUiState = transactionDetailUiState.copy(
            transactionTitle = transactionTitle)
    }
    //TYPE
    fun onTransactionTypeChanged(transactionType: TransactionType) {
        viewModelScope.launch {
            transactionDetailUiState = transactionDetailUiState.copy(
                transactionType = transactionType,
            )
            transactionDetailUiState = transactionDetailUiState.copy(
                categoryIdFK = if (transactionType == TransactionType.EXPENSE) 2 else 1,
            )
            getCategoriesByType(transactionType).join()
            transactionDetailUiState = transactionDetailUiState.copy(
                category = transactionDetailUiState.categories.find { it.id == transactionDetailUiState.categoryIdFK }!!
            )
        }
    }
    //AMOUNT
    fun onTransactionAmountChanged(transactionAmount: Double) {
        transactionDetailUiState = transactionDetailUiState.copy(
            transactionAmount = transactionAmount)
    }
    //DATETIME
    fun onTransactionDateChanged(transactionDate: Date) {
        transactionDetailUiState = transactionDetailUiState.copy(
            transactionDate = transactionDate)
    }
    //DESCRIPTION
    fun onTransactionDescriptionChanged(transactionDescription: String) {
        transactionDetailUiState = transactionDetailUiState.copy(
            transactionDescription = transactionDescription)
    }
    //CATEGORY-ID
    fun onCategoryIdChanged(categoryId: Int) {
        transactionDetailUiState = transactionDetailUiState.copy(
            categoryIdFK = categoryId,
            category = transactionDetailUiState.categories.find { it.id == categoryId }!!)
    }

    /**
     * Manipulate functions ------------------------------------------------------------------------
     */
    //ADD TRANSACTION
    fun addTransaction() {
        viewModelScope.launch {
            transactionWithCategoryRepository.insertTransaction(
                Transaction(
                    transactionTitle = transactionDetailUiState.transactionTitle,
                    type = transactionDetailUiState.transactionType,
                    amount = transactionDetailUiState.transactionAmount,
                    datetime = transactionDetailUiState.transactionDate,
                    transactionDescription = transactionDetailUiState.transactionDescription,
                    categoryIdFK = transactionDetailUiState.categoryIdFK,
                )
            )
        }
    }

    //UPDATE TRANSACTION
    fun updateTransaction(transactionId: Long) {
        viewModelScope.launch {
            transactionWithCategoryRepository.updateTransaction(
                Transaction(
                    id = transactionId,
                    transactionTitle = transactionDetailUiState.transactionTitle,
                    type = transactionDetailUiState.transactionType,
                    amount = transactionDetailUiState.transactionAmount,
                    datetime = transactionDetailUiState.transactionDate,
                    transactionDescription = transactionDetailUiState.transactionDescription,
                    categoryIdFK = transactionDetailUiState.categoryIdFK,
                )
            )
        }
    }
}

//@Suppress("UNCHECKED_CAST")
//class TransactionDetailViewModelFactor(private  val transactionId: Long): ViewModelProvider.Factory{
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return TransactionDetailViewModel(transactionId = transactionId) as T
//    }
//}