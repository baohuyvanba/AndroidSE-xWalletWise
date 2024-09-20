package com.android.tyzen.xwalletwise.model.transactionDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.android.tyzen.xwalletwise.model.category.Category
import com.android.tyzen.xwalletwise.model.category.CategoryDao
import com.android.tyzen.xwalletwise.model.converters.DateConverter
import com.android.tyzen.xwalletwise.model.converters.TransactionTypeConverter
import com.android.tyzen.xwalletwise.model.transaction.Transaction
import com.android.tyzen.xwalletwise.model.transaction.TransactionDao
import com.android.tyzen.xwalletwise.model.transaction.TransactionType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * TRANSACTION DATABASE IMPLEMENTATION
 */
@TypeConverters(value = [DateConverter::class, TransactionTypeConverter::class])
@Database(
    entities =
    [
        Transaction::class,
        Category::class
    ],
    version = 1,
    exportSchema = false,
)
abstract class TransactionDB: RoomDatabase()
{
    abstract fun transactionDao(): TransactionDao
    abstract fun categoryDao(): CategoryDao
    abstract fun transactionWithCategoryDao(): TransactionWithCategoryDao

    //Use companion object to create a SINGLE instance of the database
    companion object {
        @Volatile
        var INSTANCE: TransactionDB? = null
        fun getDatabase(context: Context) : TransactionDB {
            return INSTANCE ?: synchronized(this)    //Only 1 thread able to access at a time
            {
                val instance = Room.databaseBuilder(
                    context,
                    TransactionDB::class.java,
                    "transaction_database",
                )
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            //Default category
                            val categoryDao = getDatabase(context).categoryDao()
                            CoroutineScope(Dispatchers.IO).launch {
                                categoryDao.insertCategory(
                                    Category(
                                        id = 1,
                                        title = "Default Income",
                                        icon  = "Other",
                                        categoryType = TransactionType.INCOME,
                                        description = "The default category for Income transactions",)
                                )
                                categoryDao.insertCategory(
                                    Category(
                                        id = 2,
                                        title = "Default Expense",
                                        icon  = "Other",
                                        categoryType = TransactionType.EXPENSE,
                                        description = "The default category for Expense transactions",)
                                )
                            }
                        }
                    }
                    )
                    //.allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}