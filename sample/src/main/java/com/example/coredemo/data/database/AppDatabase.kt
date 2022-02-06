package com.example.coredemo.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.coredemo.data.database.dao.SpellDao
import com.example.coredemo.data.database.dao.SpellRemoteKeysDao
import com.example.coredemo.data.database.entity.Spell
import com.example.coredemo.data.database.entity.SpellRemoteKeys
import com.example.coredemo.data.database.utils.DBConstants

@Database(entities = [Spell::class, SpellRemoteKeys::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun spellDao(): SpellDao
    abstract fun spellRemoteKeysDao(): SpellRemoteKeysDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context, AppDatabase::class.java, DBConstants.DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}