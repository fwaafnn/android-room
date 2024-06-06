package com.example.testappinventaris.data.entity

import androidx.room.Embedded
import androidx.room.Relation

data class AlatRuangan(
    @Embedded val alat: Alat,
    @Relation(
        parentColumn = "alatId",
        entityColumn = "alatId"
    )
    val ruangan: Ruangan
)
