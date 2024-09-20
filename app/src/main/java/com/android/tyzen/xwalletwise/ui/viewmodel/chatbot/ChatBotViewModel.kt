package com.android.tyzen.xwalletwise.ui.viewmodel.chatbot

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.tyzen.xwalletwise.model.ChatMessage
import com.android.tyzen.xwalletwise.model.ChatRole
import com.android.tyzen.xwalletwise.repository.geminiApi.ChatBotRepository
import com.android.tyzen.xwalletwise.repository.transaction.TransactionWithCategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ChatBotUiState(
    val prompt: String = "",
    var isLoading: Boolean = false,
    val list: MutableList<ChatMessage>? = mutableListOf(),
)

@HiltViewModel
class ChatBotViewModel @Inject constructor(
    private val transactionWithCategoryRepository: TransactionWithCategoryRepository,
    private val chatBotRepository: ChatBotRepository
): ViewModel() {
    var chatBotUiState by mutableStateOf(ChatBotUiState())
        private set

    /**
     * On Change Functions =========================================================================
     */
    //On Prompt Change
    fun onPromptChange(prompt: String) {
        chatBotUiState = chatBotUiState.copy(prompt = prompt)
    }

    //On Submit
    fun onSubmit() {
        viewModelScope.launch {
            //Add user message
            addMessage(chatBotUiState.prompt, ChatRole.USER.toString(), false)
            val prompt = chatBotUiState.prompt
            chatBotUiState = chatBotUiState.copy(prompt = "")
            //Send request
            chatBotUiState = chatBotUiState.copy(isLoading = true)
            val response = chatBotRepository.sendAndGetResponse(prompt)
            //Add bot message
            addMessage(response, ChatRole.MODEL.toString(), true)
            chatBotUiState = chatBotUiState.copy(isLoading = false)
        }
    }

    private fun addMessage(message: String, role: String, direction: Boolean)
    {
        val chatMessage = ChatMessage(message, role, direction)
        chatBotUiState = chatBotUiState.copy(list = chatBotUiState.list?.apply { add(chatMessage) })
    }
}

//@Suppress("UNCHECKED_CAST")
//class ChatBotViewModelFactor(): ViewModelProvider.Factory{
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return ChatBotViewModel() as T
//    }
//}