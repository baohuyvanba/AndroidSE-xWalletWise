package com.android.tyzen.xwalletwise.model.transaction

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

/**
 * TRANSACTION ENTITY
 */
@Entity(tableName = "transactions")
data class Transaction(
    @ColumnInfo(name = "transaction_id")
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    //
    val categoryIdFK: Int,           //Foreign Key: Reference to the Category
    val type: TransactionType,       //Expense or Income
    val transactionTitle: String,
    val amount: Double,
    val datetime: Date,
    val transactionDescription: String? = null,
)

/**
 * TRANSACTION TYPES
 */
enum class TransactionType {
    EXPENSE,
    INCOME
}