package com.example.testappinventaris.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Alat")
data class Alat(
    @PrimaryKey(autoGenerate = true) var alatId: Int? = null,
    var namaAlat: String?,
    var spesifikasi: String?,
    var jumlah: String?,
    var kegunaan: String?
)
