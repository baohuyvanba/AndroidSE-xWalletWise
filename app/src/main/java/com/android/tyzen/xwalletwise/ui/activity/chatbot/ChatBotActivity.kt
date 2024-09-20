package com.finance.android.walletwise.ui.activity.chatbot

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.android.tyzen.xwalletwise.model.ChatMessage
import com.android.tyzen.xwalletwise.ui.fragment.WalletWiseTopAppBar
import com.android.tyzen.xwalletwise.ui.viewmodel.chatbot.ChatBotViewModel
import com.halilibo.richtext.markdown.Markdown
import com.halilibo.richtext.ui.material3.RichText


/**
 * CHAT SCREEN ====================================================================================
 */
@Composable
fun ChatBotScreen(
    onBackClick: () -> Unit, )
{
    val chatBotViewModel = viewModel(modelClass = ChatBotViewModel::class.java)
    val chatBotUiState = chatBotViewModel.chatBotUiState

    Scaffold(
        topBar = {
            WalletWiseTopAppBar(
                title = "Financial Chatbot",
                useIconForTitle = false,
                showNavigationButton = true,
                navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
                onNavigationClick = onBackClick,
                showActionButton = false,
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            // Chat List
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                reverseLayout = true,
                contentPadding = PaddingValues(8.dp),
                ) {
                items(chatBotUiState.list?.size ?: 0) { index -> // Handle potential null list
                    chatBotUiState.list?.reversed()?.get(index)?.let { chatMessage ->
                        ChatList(chatMessage)
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Input Section
            ChatInput(
                text = chatBotUiState.prompt,
                onTextChange = { chatBotViewModel.onPromptChange(it) },
                sendCLick = { chatBotViewModel.onSubmit() },
                progress = chatBotUiState.isLoading
            )
        }
    }
}


/**
 * CHAT COMPOSE ELEMENTS ===========================================================================
 */
//CHAT OUTPUT
/*@Preview(showBackground = true)
@Composable
fun PreviewChatList() {
    WalletWiseTheme {
        ChatList(ChatMessage(
            message = """
                        # Demo

                        ---

                        ```javascript
                        var s = "code blocks use monospace font";
                        alert(s);
                        ```

                        Markdown | Table | Extension
                        --- | --- | ---
                        *renders* | `images` | "Text 1")
                        1 | 2 | 3

                        > Blockquotes are very handy in email to emulate reply text.
                        > This line is part of the same quote.
                        """.trimIndent(),
            role = ChatRole.MODEL.toString(),
            direction = true))
    }
}*/
@Composable
fun ChatList(chat: ChatMessage)
{
    Column(
        modifier = Modifier
            .padding(start = 8.dp, end = 8.dp, bottom = 8.dp, top = 8.dp)
            .fillMaxWidth()
            .padding(
                end = if (chat.direction) 20.dp else 0.dp,
                start = if (!chat.direction) 20.dp else 0.dp
            ),
        horizontalAlignment = if (chat.direction) Alignment.Start else Alignment.End)
    {
        Card(
            shape = if (chat.direction)
                RoundedCornerShape(topStart = 0.dp, topEnd = 20.dp, bottomStart = 20.dp, bottomEnd = 20.dp)
            else
                RoundedCornerShape(topStart = 20.dp, topEnd = 0.dp, bottomStart = 20.dp, bottomEnd = 20.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary.copy(0.5f),
            ),
            modifier = Modifier.clickable {
            }
        ) {
            Box(
                contentAlignment = Alignment.Center, )
            {
                RichText(
                    modifier = Modifier
                        .padding(8.dp))
                {
                    Markdown(chat.message)
                }
            }
        }

    }

}

//CHAT INPUT
/*@Preview(showBackground = true)
@Composable
fun PreviewChatInput() {
    WalletWiseTheme {
        ChatInput(
            text = "",
            onTextChange = {},
            sendCLick = {},
            progress = false
        )
    }
}*/

@Composable
fun ChatInput(
    text: String,
    onTextChange: (String) -> Unit,
    sendCLick: () -> Unit,
    progress: Boolean, )
{
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = text,
        onValueChange = onTextChange,
        shape = MaterialTheme.shapes.extraLarge,
        colors = TextFieldDefaults.colors(
            focusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        label = {
            Text(text = "Message")
        },
        trailingIcon = {
            if (progress) {
                CircularProgressIndicator(
                    modifier = Modifier.size(32.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            else
            {
                IconButton(
                    onClick = { sendCLick() }, )
                {
                    Icon(imageVector = Icons.AutoMirrored.Filled.Send, contentDescription = null)
                }
            }

        }
    )
}
