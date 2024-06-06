package com.example.testappinventaris.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.testappinventaris.data.entity.Alat
import com.example.testappinventaris.data.entity.Ruangan

@Dao
interface RuanganDao {
    @Query("SELECT * FROM Ruangan")
    fun getAllRuangan(): List<Ruangan>

    @Insert
    fun insert(ruangan: Ruangan): Long

    @Update
    fun update(ruangan: Ruangan)

    @Delete
    fun delete(ruangan: Ruangan)

    @Query("SELECT * FROM Ruangan WHERE alatId = :alatId")
    fun getRuanganByAlatId(alatId: Int): Ruangan?
}