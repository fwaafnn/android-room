package com.example.testappinventaris.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.testappinventaris.data.entity.AlatRuangan

@Dao
interface AlatRuanganDao {
    @Transaction
    @Query("SELECT * FROM Alat")
    fun getAlatDanRuangan(): List<AlatRuangan>
}