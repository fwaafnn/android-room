package com.example.testappinventaris.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "Ruangan",
    foreignKeys = [
        ForeignKey(entity = Alat::class, parentColumns = ["alatId"], childColumns = ["alatId"], onDelete = ForeignKey.CASCADE)
    ]
)
data class Ruangan(
    @PrimaryKey(autoGenerate = true) var ruanganId: Int? = null,
    var namaRuangan: String?,
    var alatId: Int?
)
