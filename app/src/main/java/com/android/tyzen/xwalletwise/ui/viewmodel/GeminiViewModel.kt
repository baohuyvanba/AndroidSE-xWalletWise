package com.android.tyzen.xwalletwise.ui.viewmodel

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.android.tyzen.xwalletwise.model.category.Category
import com.android.tyzen.xwalletwise.model.gemini.OcrTransactionResponse
import com.android.tyzen.xwalletwise.repository.geminiApi.OcrTransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.launch
import javax.inject.Inject

data class OcrTransactionUiState(
    val ocrTransaction: OcrTransactionResponse? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)

@HiltViewModel
class OCRTransactionViewModel @Inject constructor(
    private val ocrRepository: OcrTransactionRepository
): ViewModel() {
    var ocrTransactionUiState by mutableStateOf(OcrTransactionUiState())
        private set

    fun onImageCaptured(
        image: Bitmap,
        categoriesList: List<Category> )
    {
        viewModelScope.launch {
            ocrTransactionUiState = ocrTransactionUiState.copy(
                isLoading = true)

            val result = ocrRepository.extractTransactionDetails(
                imageBitmap = image,
                categoryList = categoriesList,)

            ocrTransactionUiState = ocrTransactionUiState.copy(
                ocrTransaction = result,
                isLoading = false,
                error = "null",)
        }
    }
}

//@Suppress("UNCHECKED_CAST")
//class OCRTransactionViewModelFactor(): ViewModelProvider.Factory{
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return OCRTransactionViewModel() as T
//    }
//}