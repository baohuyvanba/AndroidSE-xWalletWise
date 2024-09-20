package com.android.tyzen.xwalletwise.ui.viewmodel.category

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.android.tyzen.xwalletwise.model.category.Category
import com.android.tyzen.xwalletwise.model.transaction.TransactionType
import com.android.tyzen.xwalletwise.repository.transaction.TransactionWithCategoryRepository
import com.android.tyzen.xwalletwise.util.categoriesList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CategoryDetailUiState (
    //Category value
    val title: String = "",
    val icon: String = "",
    val budget: Double = 0.0,
    val categoryType: TransactionType = TransactionType.INCOME,
    val description: String? = "",
    //
    val iconList: List<String> = categoriesList,
    //State value
    val isViewing : Boolean = false,
    val isDeleting: Boolean = false,
)

@HiltViewModel
class CategoryDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val transactionWithCategoryRepository: TransactionWithCategoryRepository
): ViewModel() {
    val categoryId: Int = savedStateHandle["categoryId"] ?: 0

    var categoryDetailUiState by mutableStateOf(CategoryDetailUiState())
        private set

    //Get Category in View Mode
    init {
        if (categoryId != -1) //View detail mode
        {
            viewModelScope.launch {
                transactionWithCategoryRepository
                    .getCategoryById(categoryId)
                    .collectLatest { category ->
                        categoryDetailUiState = categoryDetailUiState.copy(
                            title = category.title,
                            icon = category.icon,
                            budget = category.budget,
                            categoryType = category.categoryType,
                            description = category.description,)
                    }
            }
        }
    }

    init {
        categoryDetailUiState =
            if (categoryId != -1)
            {
                categoryDetailUiState.copy(isViewing = true)
            }
            else
            {
                categoryDetailUiState.copy(isViewing = false)
            }
    }

    /**
     * On change functions -------------------------------------------------------------------------
     */
    fun onTitleChange(title: String)
    {
        categoryDetailUiState = categoryDetailUiState.copy(title = title)
    }

    fun onIconChange(icon: String)
    {
        categoryDetailUiState = categoryDetailUiState.copy(icon = icon)
    }

    fun onBudgetChange(budget: Double)
    {
        categoryDetailUiState = categoryDetailUiState.copy(budget = budget)
    }

    fun onCategoryTypeChange(categoryType: TransactionType)
    {
        categoryDetailUiState = categoryDetailUiState.copy(categoryType = categoryType)
    }

    fun onDescriptionChange(description: String)
    {
        categoryDetailUiState = categoryDetailUiState.copy(description = description)
    }

    /**
     * Manipulate functions ------------------------------------------------------------------------
     */
    fun addCategory()
    {
        viewModelScope.launch {
            transactionWithCategoryRepository.insertCategory(
                Category(
                    title = categoryDetailUiState.title,
                    icon = categoryDetailUiState.icon,
                    categoryType = categoryDetailUiState.categoryType,
                    budget = categoryDetailUiState.budget,
                    description = categoryDetailUiState.description,)
            )
        }
    }

    fun updateCategory(categoryId: Int)
    {
        viewModelScope.launch {
            transactionWithCategoryRepository.updateCategory(
                Category(
                    id = categoryId, //Overwrite
                    title = categoryDetailUiState.title,
                    icon = categoryDetailUiState.icon,
                    budget = categoryDetailUiState.budget,
                    categoryType = categoryDetailUiState.categoryType,
                    description = categoryDetailUiState.description,)
            )
        }
    }
}

//@Suppress("UNCHECKED_CAST")
//class CategoryDetailViewModelFactor(private  val categoryId: Int): ViewModelProvider.Factory{
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return CategoryDetailViewModel(categoryId = categoryId) as T
//    }
//}