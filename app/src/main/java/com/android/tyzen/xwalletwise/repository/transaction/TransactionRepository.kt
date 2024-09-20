package com.android.tyzen.xwalletwise.repository.transaction
//
//import com.finance.android.walletwise.model.Transaction
//import com.finance.android.walletwise.model.transaction.TransactionDao
//import kotlinx.coroutines.flow.Flow
//import javax.inject.Inject
//
//class TransactionRepository @Inject constructor(
//    private val transactionDao: TransactionDao)
//{
//    val allTransactions: Flow<List<Transaction>> =
//        transactionDao.getAllTransactions()
//
//    //Insert Transaction
//    suspend fun insertTransaction(transaction: Transaction)
//    {
//        transactionDao.insertTransaction(transaction)
//    }
//    //Update Transaction
//    suspend fun updateTransaction(transaction: Transaction)
//    {
//        transactionDao.updateTransaction(transaction)
//    }
//    //Delete Transaction
//    suspend fun deleteTransaction(transaction: Transaction)
//    {
//        transactionDao.deleteTransaction(transaction)
//    }
//}