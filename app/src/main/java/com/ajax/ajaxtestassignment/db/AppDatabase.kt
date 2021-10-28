package com.ajax.ajaxtestassignment.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ajax.ajaxtestassignment.db.contacts.ContactsDao
import com.ajax.ajaxtestassignment.db.contacts.DbContact

@Database(entities = [DbContact::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): ContactsDao
}
