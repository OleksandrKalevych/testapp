package com.ajax.ajaxtestassignment.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ajax.ajaxtestassignment.db.contacts.ContactsDao
import com.ajax.ajaxtestassignment.db.contacts.DbContact

@Database(entities = [DbContact::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): ContactsDao
}

private lateinit var INSTANCE: AppDatabase

fun getDatabase(context: Context): AppDatabase {
    synchronized(AppDatabase::class) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room
                .databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "contacts_db"
                )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
    return INSTANCE
}