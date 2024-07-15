package com.android.expensetracker.database.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses")
data class Expense(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "expense") val expense: String,
    @ColumnInfo(name = "amount") val amount: Float,
    @ColumnInfo(name = "tag") val tag: String? = null // Optional tag
)