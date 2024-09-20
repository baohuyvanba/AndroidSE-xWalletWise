package com.android.tyzen.xwalletwise.model.transaction

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    //Get all transactions
    @Query("""
        SELECT *
        FROM transactions
        """)
    fun getAllTransactions(): Flow<List<Transaction>>

    /**
     * MANIPULATE DATA -----------------------------------------------------------------------------
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: Transaction)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTransaction(transaction: Transaction)

    @Delete
    suspend fun deleteTransaction(transaction: Transaction)

    /**
     * QUERY FUNCTIONS -----------------------------------------------------------------------------
     */
    //by Transaction ID
    @Query("""
        SELECT * FROM transactions
        WHERE transaction_id = :transactionId 
        """)
    fun getTransactionById(transactionId: Long): Flow<Transaction>

    //by Category ID
    @Query("""
        SELECT * FROM transactions
        WHERE categoryIdFK = :categoryId
        """)
    fun getTransactionsByCategory(categoryId: Int): Flow<List<Transaction>>

    //by Transaction Type
    @Query("""
        SELECT * FROM transactions
        WHERE type = :transactionType
        """)
    fun getTransactionsByType(transactionType: TransactionType): Flow<List<Transaction>>

    /* BY TIME */
    //by specific Date (dd/MM/yyyy format)
    @Query("""
        SELECT * FROM transactions
        WHERE strftime('%d/%m/%Y', datetime / 1000, 'unixepoch') = :date
        """)
    fun getTransactionsByDate(date: String): Flow<List<Transaction>>

    //by Week (weeks start on Monday)
    @Query("""
        SELECT * FROM transactions
        WHERE DATE(datetime / 1000, 'unixepoch', 'weekday 0') = DATE(:date / 1000, 'unixepoch', 'weekday 0')
        """)
    fun getTransactionsByWeek(date: Long): Flow<List<Transaction>>

    //by Month
    @Query("""
        SELECT * FROM transactions
        WHERE strftime('%m-%Y', datetime / 1000, 'unixepoch') = strftime('%m-%Y', :date / 1000, 'unixepoch')
        """)
    fun getTransactionsByMonth(date: Long): Flow<List<Transaction>>

    /**
     * CALCULATE DATA ------------------------------------------------------------------------------
     */
    //Get Total Income
    @Query("""
        SELECT IFNULL(SUM(amount), 0.0)
        FROM transactions
        WHERE type = "INCOME"
        """)
    fun getTotalIncome(): Flow<Double>

    //Get Total Expense
    @Query("""
        SELECT IFNULL(SUM(amount), 0.0)
        FROM transactions
        WHERE type = "EXPENSE"
        """)
    fun getTotalExpense(): Flow<Double>

    //Get Total Balance
    @Query("""
        SELECT IFNULL(
            (
                SELECT IFNULL(SUM(amount), 0.0) 
                FROM transactions 
                WHERE type = "INCOME"
            ) -
            (
                SELECT IFNULL(SUM(amount), 0.0) 
                FROM transactions 
                WHERE type = "EXPENSE"
            ), 0.0)
        AS totalBalance
        """)
    fun getTotalBalance(): Flow<Double>
}