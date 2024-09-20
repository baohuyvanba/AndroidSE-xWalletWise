package com.android.tyzen.xwalletwise.model.category

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.android.tyzen.xwalletwise.model.transaction.TransactionType
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    //Get all categories
    @Query("""
        SELECT *
        FROM categories
        """)
    fun getAllCategories(): Flow<List<Category>>

    /**
     * MANIPULATE DATA -----------------------------------------------------------------------------
     */
    //Insert category
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: Category)

    //Update category
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateCategory(category: Category)

    //Delete category
    @Delete
    suspend fun deleteCategory(category: Category)

    /**
     * QUERY FUNCTIONS -----------------------------------------------------------------------------
     */
    //by Category ID
    @Query("""
        SELECT * FROM categories
        WHERE category_id = :categoryId
        """)
    suspend fun getCategoryById(categoryId: Int): Category

    //by Category Type
    @Query("""
        SELECT * FROM categories
        WHERE categoryType = :categoryType
        """)
    fun getCategoryByType(categoryType: TransactionType): Flow<List<Category>>

    /**
     * CALCULATE FUNCTIONS ------------------------------------------------------------------------
     */
    //Total Expenses (unchanged, still relevant)
    @Query("""
        SELECT IFNULL(SUM(amount), 0.0) 
        FROM transactions 
        WHERE categoryIdFK = :categoryId
        AND strftime('%m', datetime / 1000, 'unixepoch') = strftime('%m', 'now')
        AND type = 'EXPENSE'
    """)
    fun getCategoryExpensesForMonth(categoryId: Int): Flow<Double>

    //Total Budgets
    @Query("""
        SELECT IFNULL(SUM(budget), 0.0)
        FROM categories
        WHERE budget IS NOT NULL
        """)
    fun getTotalBudgets(): Flow<Double>
}