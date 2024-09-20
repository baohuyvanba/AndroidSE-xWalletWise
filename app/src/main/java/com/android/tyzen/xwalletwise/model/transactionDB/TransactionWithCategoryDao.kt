package com.android.tyzen.xwalletwise.model.transactionDB

import androidx.room.Dao
import androidx.room.Query
import com.android.tyzen.xwalletwise.model.transaction.TransactionType
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionWithCategoryDao {
    /**
     * QUERY FUNCTIONS -----------------------------------------------------------------------------
     */
    //Get all Transactions with Categories
    @Query("""
        SELECT *
        FROM transactions INNER JOIN categories
        ON transactions.categoryIdFK = categories.category_id
        """)
    fun getAllTransactionsWithCategories(): Flow<List<TransactionWithCategory>>

    //(by Category) Get all Transactions with Categories
    @Query("""
        SELECT *
        FROM transactions INNER JOIN categories
        ON transactions.categoryIdFK = categories.category_id
        WHERE transactions.categoryIdFK = :categoryId
        """)
    fun getTransactionsWithCategoriesByCategoryId(categoryId: Int): Flow<List<TransactionWithCategory>>

    //(by Transaction ID) Get all Transactions with Categories
    @Query("""
        SELECT *
        FROM transactions INNER JOIN categories
        ON transactions.categoryIdFK = categories.category_id
        WHERE transactions.transaction_id = :transactionId
        """)
    fun getTransactionWithCategoryByTransactionId(transactionId: Long): Flow<TransactionWithCategory>

    //(by Transaction Type) Get all Transactions with Categories
    @Query("""
        SELECT *
        FROM transactions INNER JOIN categories
        ON transactions.categoryIdFK = categories.category_id
        WHERE transactions.type = :transactionType
        """)
    fun getTransactionsWithCategoriesByType(transactionType: TransactionType): Flow<List<TransactionWithCategory>>

    /**
     * FILTER BY TIME
     */
    //(by Today) Get all Transactions with Categories
    @Query("""
        SELECT *
        FROM transactions INNER JOIN categories
        ON transactions.categoryIdFK = categories.category_id
        WHERE date(datetime / 1000, 'unixepoch', 'localtime') = date(:date / 1000, 'unixepoch', 'localtime')
        """)
    fun getTransactionsWithCategoriesByDay(date: Long): Flow<List<TransactionWithCategory>>

    //(by Week) Get all Transactions with Categories
    @Query("""
        SELECT *
        FROM transactions INNER JOIN categories
        ON transactions.categoryIdFK = categories.category_id
        WHERE DATE(transactions.datetime / 1000, 'unixepoch', 'weekday 0') = DATE(:date / 1000, 'unixepoch', 'weekday 0')
        """)
    fun getTransactionsWithCategoriesByWeek(date: Long): Flow<List<TransactionWithCategory>>

    //(by Month) Get all Transactions with Categories
    @Query("""
        SELECT *
        FROM transactions INNER JOIN categories
        ON transactions.categoryIdFK = categories.category_id
        WHERE strftime('%m-%Y', transactions.datetime / 1000, 'unixepoch') = strftime('%m-%Y', :date / 1000, 'unixepoch')
        """)
    fun getTransactionsWithCategoriesByMonth(date: Long): Flow<List<TransactionWithCategory>>

    //(by Year) Get all Transactions with Categories
    @Query("""
        SELECT *
        FROM transactions INNER JOIN categories
        ON transactions.categoryIdFK = categories.category_id
        WHERE strftime('%Y', datetime / 1000, 'unixepoch', 'localtime') = strftime('%Y', :date / 1000, 'unixepoch', 'localtime')
        """)
    fun getTransactionsWithCategoriesByYear(date: Long): Flow<List<TransactionWithCategory>>
}