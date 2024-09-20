package com.android.tyzen.xwalletwise.repository.transaction

import android.util.Log
import com.android.tyzen.xwalletwise.model.category.Category
import com.android.tyzen.xwalletwise.model.category.CategoryDao
import com.android.tyzen.xwalletwise.model.transaction.Transaction
import com.android.tyzen.xwalletwise.model.transaction.TransactionDao
import com.android.tyzen.xwalletwise.model.transaction.TransactionType
import com.android.tyzen.xwalletwise.model.transactionDB.TransactionWithCategory
import com.android.tyzen.xwalletwise.model.transactionDB.TransactionWithCategoryDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TransactionWithCategoryRepository(
    private val transactionDao: TransactionDao,
    private val categoryDao: CategoryDao,
    private val transactionWithCategoryDao: TransactionWithCategoryDao, )
{
    /**
     * QUERY FUNCTIONS: TRANSACTION WITH CATEGORY --------------------------------------------------
     */
    val allTransactionsWithCategories = transactionWithCategoryDao.getAllTransactionsWithCategories()

    val totalIncome = transactionDao.getTotalIncome()
    val totalExpense = transactionDao.getTotalExpense()
    val totalBalance = transactionDao.getTotalBalance()

    //Get transaction with category by Transaction ID
    fun getTransactionWithCategoryById(transactionId: Long): Flow<TransactionWithCategory> {
        return transactionWithCategoryDao.getTransactionWithCategoryByTransactionId(transactionId)
    }

    // Get transactions with categories by Category ID
    fun getTransactionsWithCategoriesByCategory(categoryId: Int): Flow<List<TransactionWithCategory>> {
        return transactionWithCategoryDao.getTransactionsWithCategoriesByCategoryId(categoryId)
    }

    // Get transactions with categories by type
    fun getTransactionsWithCategoriesByType(transactionType: TransactionType): Flow<List<TransactionWithCategory>> {
        return transactionWithCategoryDao.getTransactionsWithCategoriesByType(transactionType)
    }

    /* === BY TIME === */
    // Get transactions with categories by day
    fun getTransactionsWithCategoriesByDay(date: Long): Flow<List<TransactionWithCategory>> {
        return transactionWithCategoryDao.getTransactionsWithCategoriesByDay(date)
    }

    // Get transactions with categories by week
    fun getTransactionsWithCategoriesByWeek(date: Long): Flow<List<TransactionWithCategory>> {
        return transactionWithCategoryDao.getTransactionsWithCategoriesByWeek(date)
    }

    // Get transactions with categories by month
    fun getTransactionsWithCategoriesByMonth(date: Long): Flow<List<TransactionWithCategory>> {
        return transactionWithCategoryDao.getTransactionsWithCategoriesByMonth(date)
    }

    // Get transactions with categories by year
    fun getTransactionsWithCategoriesByYear(date: Long): Flow<List<TransactionWithCategory>> {
        return transactionWithCategoryDao.getTransactionsWithCategoriesByYear(date)
    }

    /**
     * TRANSACTION ---------------------------------------------------------------------------------
     */
    //Insert Transaction
    suspend fun insertTransaction(transaction: Transaction) =
        transactionDao.insertTransaction(transaction)
    //Update Transaction
    suspend fun updateTransaction(transaction: Transaction) =
        transactionDao.updateTransaction(transaction)
    //Delete Transaction
    suspend fun deleteTransaction(transaction: Transaction) =
        transactionDao.deleteTransaction(transaction)

    /**
     * CATEGORY ------------------------------------------------------------------------------------
     */
    //Get all Categories
    val allCategories = categoryDao.getAllCategories()
    //Calculate total budgets
    val totalBudgets = categoryDao.getTotalBudgets()

    //Insert Category
    suspend fun insertCategory(category: Category) =
        categoryDao.insertCategory(category)
    //Update Category
    suspend fun updateCategory(category: Category) =
        categoryDao.updateCategory(category)
    //Delete Category
    suspend fun deleteCategory(category: Category) =
        categoryDao.deleteCategory(category)

    //Update total expenses for a category by month
    fun updateTotalExpenses(categoryId: Int): Flow<Double>
    {
        try
        {
            val total = categoryDao.getCategoryExpensesForMonth(categoryId)
            return total
        }
        catch (e: Exception)
        {
            Log.e("CategoryRepository", "Error updating total expenses", e)
            return flow { emit(0.0) }
        }
    }

    //Query Functions: CATEGORY --------------------------------------------------------------------
    //Get Category by ID
    fun getCategoryById(categoryId: Int): Flow<Category> {
        return flow {
            emit(categoryDao.getCategoryById(categoryId))
        }
    }
    //Get Category by Type
    fun getCategoryByType(categoryType: TransactionType): Flow<List<Category>> =
        categoryDao.getCategoryByType(categoryType)
}