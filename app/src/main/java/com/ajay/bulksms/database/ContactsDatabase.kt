package com.ajay.bulksms.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ajay.bulksms.contactlist.Contacts

@Database(entities = [Contacts::class], version = 1, exportSchema = false)
abstract class ContactsDatabase : RoomDatabase() {
    abstract fun contactsDao(): ContactsDao
    companion object {
        @Volatile
        private var Instance: ContactsDatabase? = null

        fun getDatabase(context: Context): ContactsDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context,  ContactsDatabase::class.java, "Repos_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}