package com.example.testappinventaris.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.testappinventaris.data.entity.Alat

@Dao
interface AlatDao {
    @Query("SELECT * FROM Alat")
    fun getAllAlat(): List<Alat>

    @Query("SELECT * FROM Alat WHERE alatId = :alatId")
    fun getById(alatId: Int): Alat?

    @Insert
    fun insert(alat: Alat): Long

    @Update
    fun update(alat: Alat)

    @Delete
    fun delete(alat: Alat)
}