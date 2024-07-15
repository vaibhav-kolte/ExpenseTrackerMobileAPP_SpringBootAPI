package com.android.expensetracker.interfaces

import com.android.expensetracker.database.data.Expense

interface DeleteExpense {
    fun onDeleteExpense(expense: Expense)
}