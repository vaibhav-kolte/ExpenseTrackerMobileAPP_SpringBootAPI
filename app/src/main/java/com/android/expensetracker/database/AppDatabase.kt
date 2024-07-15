package com.android.expensetracker.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.expensetracker.database.dao.ExpenseDao
import com.android.expensetracker.database.dao.LentDao
import com.android.expensetracker.database.data.Expense
import com.android.expensetracker.database.data.Lent

@Database(entities = [Expense::class, Lent::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
    abstract fun lentDao(): LentDao
}