package com.android.tyzen.xwalletwise.model.gemini

data class OcrTransactionResponse(
    val transactionTitle: String,
    val amount: Double,
    val datetime: String,
    val description: String?,
    val categoryId: Int
)

