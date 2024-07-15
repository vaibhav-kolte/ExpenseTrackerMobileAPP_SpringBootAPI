package com.android.expensetracker.dataModel

import com.android.expensetracker.database.data.Expense

data class ExpenseData(

    val date: String,
    val expense: String,
    val expenseDetails: List<Expense>
)
