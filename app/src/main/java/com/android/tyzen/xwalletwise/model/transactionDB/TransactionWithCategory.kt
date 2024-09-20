package com.android.tyzen.xwalletwise.model.transactionDB

import androidx.room.Embedded
import com.android.tyzen.xwalletwise.model.category.Category
import com.android.tyzen.xwalletwise.model.transaction.Transaction

data class TransactionWithCategory(
    @Embedded val transaction: Transaction,
    @Embedded val category   : Category,
)
