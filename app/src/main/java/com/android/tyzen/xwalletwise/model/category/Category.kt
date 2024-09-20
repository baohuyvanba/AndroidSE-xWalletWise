package com.android.tyzen.xwalletwise.model.category

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.tyzen.xwalletwise.model.transaction.TransactionType

/**
 * CATEGORY ENTITY
 */
@Entity(tableName = "categories")
data class Category(
    @ColumnInfo(name = "category_id")
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    //
    val title: String,
    val icon: String,                  //Resource path
    val categoryType: TransactionType, //Expense or Income
    val budget: Double = 0.0,          //Optional
    val description: String? = null,   //Optional
) {
    var totalExpenses: Double = 0.0    //Calculated, not stored in DB
}