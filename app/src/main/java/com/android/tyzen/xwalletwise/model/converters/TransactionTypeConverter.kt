package com.android.tyzen.xwalletwise.model.converters

import androidx.room.TypeConverter
import com.android.tyzen.xwalletwise.model.transaction.TransactionType

open class TransactionTypeConverter {
    @TypeConverter
    fun toTransactionType(value: String): TransactionType {
        return TransactionType.valueOf(value)
    }

    @TypeConverter
    fun fromTransactionType(type: TransactionType): String {
        return type.name
    }
}