package com.android.tyzen.xwalletwise.repository.geminiApi

import android.graphics.Bitmap
import android.util.Log
import com.android.tyzen.xwalletwise.model.category.Category
import com.android.tyzen.xwalletwise.model.gemini.OcrTransactionResponse
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.GenerateContentResponse
import com.google.ai.client.generativeai.type.content
import com.google.ai.client.generativeai.type.generationConfig
import javax.inject.Inject

val ocrGenerativeModel = GenerativeModel(
    modelName = "gemini-1.5-pro",
    apiKey = "AIzaSyAQw3ioVEnPT4nMnE5k4-SqA2BaQmYI_0s",
    generationConfig = generationConfig {
        temperature = 0.5f
        topK = 64
        topP = 0.95f
        maxOutputTokens = 8192
        responseMimeType = "text/plain"
    },
    systemInstruction = content { text("You are a meticulous receipt analyzer. Your task is to extract specific details from an image of a receipt. Adhere to these guidelines:" +
            "\n\n1. Output Format: Always provide your answer in a strict comma-separated format: `Expense Title, Amount, Datetime (dd/MM/yyyy HH:mm), Description, Category ID`." +
            "\n2. Field Order: Maintain the exact order of fields as specified in the output format." +
            "\n3. Required Fields: Ensure that \"Expense Title,\" \"Amount,\" and \"Category ID\" are always present. If any of these are not found, indicate this with 'N/A'." +
            "\n4. Datetime Format: The \"Datetime\" must be in 'dd/MM/yyyy HH:mm' format. If not found, use 'N/A'." +
            "\n5. Description:  Include \"Description\" only if explicitly mentioned on the receipt. If not found, leave it empty in the output." +
            "\n6. Currency and Decimal Separator: The amount should include currency symbol and use '.' for decimal separators and ',' for thousands separators." +
            "\n7. Category ID: Match the expense to the most relevant category from the provided list. If no match is found, use '-1'." +
            "\n8. Accuracy: Prioritize accuracy. If unsure, indicate so in the relevant field or use the appropriate default value.") }
)

class OcrTransactionRepository @Inject constructor()
{
    suspend fun extractTransactionDetails(
        imageBitmap: Bitmap,
        categoryList: List<Category> ): OcrTransactionResponse
    {
        Log.d("OCRTransactionRepository", "Create Request Prompt")
        val prompt = buildPrompt(categoryList)
        val request = content {
            image(imageBitmap)
            text(prompt)
        }
        Log.d("OCRTransactionRepository", "Request: $prompt")

        return try
        {
            val response = ocrGenerativeModel.generateContent(request)
            Log.d("OCRTransactionRepository", "Response: $response")
            val transactionDetails = parseResponse(response)
            transactionDetails
        }
        catch (e: Exception)
        {
            Log.e("OCRTransactionRepository", "Error: ${e.message}")
            OcrTransactionResponse(
                transactionTitle = "",
                amount = 0.0,
                datetime = "",
                description = "",
                categoryId = 0,
            )
        }
    }

    //BUILD PROMPT
    private fun buildPrompt(categoryList: List<Category>): String
    {
        Log.d("OCRTransactionBuildPrompt", "Build Prompt")
        return """
            Category List:
            ${categoryList.joinToString("\n") { "${it.id}: ${it.title}" }}
            
            Carefully examine the receipt image. Extract and present the following information in the specified comma-separated format:

            Expense Title, Amount, Datetime (dd/MM/yyyy HH:mm), Description, Category ID
            
            Example:
            Groceries, 85.000, 06/20/2024 15:45, Weekly shopping at Costco, 0511
            Dinner at Le Bistro, 380.500, 12/15/2023 19:30, Romantic dinner, -1
            Parking Fee, 12.000, 01/05/2024 10:12, City Center Parking Garage, -1
        """.trimIndent()
    }

    //PARSE RESPONSE
    private fun parseResponse(response: GenerateContentResponse): OcrTransactionResponse
    {
        val responseText = response.text ?: ""
        Log.d("OCRTransactionParseResponse", "Response Text: $responseText")

        val parts = responseText.split(",").toMutableList()

        if (parts.size < 5)
        {
            return OcrTransactionResponse(
                transactionTitle = "",
                amount = 0.0,
                datetime = "",
                description = "",
                categoryId = 0,
            )
        }

        if (parts.size == 6)
        {
            parts[1] = "${parts[1]}${parts[2]}"
            parts.removeAt(2)
            Log.d("OCRTransactionParseResponseAmountCombined", "Response Text: ${parts[1]}")
        }

        val transactionTitle = parts[0].trim()
        val amount = parts[1].trim().replace(".", "").replace(",", ".").toDoubleOrNull() ?: 0.0
        val datetime = parts[2].trim()
        val description = parts[3].trim()
        val categoryId = parts[4].trim().toIntOrNull() ?: -1

        return OcrTransactionResponse(
            transactionTitle = transactionTitle,
            amount = amount,
            datetime = datetime,
            description = description,
            categoryId = categoryId,
        )
    }

    //VALIDATE RESPONSE
    private fun isValidTransaction(transaction: OcrTransactionResponse): Boolean
    {
        return with(transaction) {
            transactionTitle.isNotBlank()
        }
    }
}