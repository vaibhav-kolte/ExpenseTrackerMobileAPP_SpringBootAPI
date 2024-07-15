package com.android.expensetracker.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.android.expensetracker.database.data.Lent
import kotlinx.coroutines.flow.Flow

@Dao
interface LentDao {
    @Query("SELECT * FROM lent")
    fun getAllLent(): Flow<List<Lent>>

    @Insert
    suspend fun insertLent(expense: Lent)

    @Update
    fun updateLent(expense: Lent)

    @Delete
    fun deleteLent(expense: Lent)
}