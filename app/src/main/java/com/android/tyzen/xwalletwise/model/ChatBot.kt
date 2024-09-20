package com.android.tyzen.xwalletwise.model

data class ChatMessage(
    val message: String,
    val role: String,
    val direction: Boolean,
)

enum class ChatRole(val value: String) {
    USER("user"),
    MODEL("model")
}
