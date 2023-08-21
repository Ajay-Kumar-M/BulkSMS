package com.ajay.bulksms.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ajay.bulksms.contactlist.Contacts
import kotlinx.coroutines.flow.Flow


@Dao
interface ContactsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(contacts: Contacts)

    @Delete
    suspend fun delete(contacts: Contacts)

    @Query("SELECT * from Contacts WHERE id = :id")
    fun getContact(id: Int): Flow<Contacts?>

    @Query("SELECT * from Contacts WHERE id IN (:ids)")
    fun getContact(ids: List<Int>): Flow<List<Contacts>?>

    @Query("SELECT * from Contacts ORDER BY displayName ASC")
    fun getAllContacts(): Flow<List<Contacts>>

    @Query("DELETE from Contacts")
    suspend fun deleteAllContacts()

}