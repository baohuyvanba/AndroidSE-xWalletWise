package com.android.tyzen.xwalletwise.repository.transaction
//
//import android.util.Log
//import com.android.tyzen.xwalletwise.model.category.Category
//import com.finance.android.walletwise.model.category.CategoryDao
//import kotlinx.coroutines.flow.Flow
//import javax.inject.Inject
//
//class CategoryRepository @Inject constructor(
//    private val categoryDao: CategoryDao)
//{
//    val allCategories: Flow<List<Category>> = categoryDao.getAllCategories()
//
//    //Insert Category
//    suspend fun insert(category: Category) =
//        categoryDao.insertCategory(category)
//    //Update Category
//    suspend fun update(category: Category) =
//        categoryDao.updateCategory(category)
//    //Delete Category
//    suspend fun delete(category: Category) =
//        categoryDao.deleteCategory(category)
//
//    /**
//     * Update total expenses for a category by month
//     */
//    suspend fun updateTotalExpenses(categoryId: Int)
//    {
//        try
//        {
//            val total = categoryDao.getCategoryExpensesForMonth(categoryId)
//            val category = categoryDao.getCategoryById(categoryId)
//
//            if (category != null)
//            {
//                category.totalExpenses = total
//                categoryDao.updateCategory(category)
//            }
//
//        }
//        catch (e: Exception)
//        {
//            Log.e("CategoryRepository", "Error updating total expenses", e)
//        }
//    }
//}