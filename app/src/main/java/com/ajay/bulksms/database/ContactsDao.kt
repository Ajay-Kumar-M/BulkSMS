package com.ajay.bulksms.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ajay.bulksms.model.Contact
import kotlinx.coroutines.flow.Flow


@Dao
interface ContactsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(contact: Contact)

    @Delete
    suspend fun delete(contact: Contact)

    @Query("SELECT * from Contacts WHERE id = :id")
    fun getContact(id: Int): Flow<Contact?>

    @Query("SELECT * from Contacts WHERE id IN (:ids)")
    fun getContact(ids: List<Int>): Flow<List<Contact>?>

    @Query("SELECT * from Contacts ORDER BY displayName ASC")
    fun getAllContacts(): Flow<List<Contact>>

    @Query("DELETE from Contacts")
    suspend fun deleteAllContacts()

}