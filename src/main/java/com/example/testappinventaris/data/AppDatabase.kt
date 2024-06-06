package com.example.testappinventaris.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.testappinventaris.data.dao.AlatDao
import com.example.testappinventaris.data.dao.AlatRuanganDao
import com.example.testappinventaris.data.dao.RuanganDao
import com.example.testappinventaris.data.entity.Alat
import com.example.testappinventaris.data.entity.Ruangan

@Database(entities = [Alat::class, Ruangan::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun alatDao(): AlatDao
    abstract fun ruanganDao(): RuanganDao
    abstract fun alatRuanganDao(): AlatRuanganDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "app-database")
                .build()
    }
}