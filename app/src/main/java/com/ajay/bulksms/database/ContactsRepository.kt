package com.ajay.bulksms.database

import com.ajay.bulksms.contactlist.Contacts
import kotlinx.coroutines.flow.Flow


/**
 * Repository that provides insert, update, delete, and retrieve of item from a given data source.
 */
interface ContactsRepository {

    var allContacts: Flow<List<Contacts>>
    /**
     * Retrieve all the items from the the given data source.
     */
    fun getAllContactsStream(): Flow<List<Contacts>>

    /**
     * Retrieve an item from the given data source that matches with the [id].
     */
    fun getContactStream(id: Int): Flow<Contacts?>

    fun getContacts(vararg ids: Int): Flow<List<Contacts>?>

    /**
     * Insert item in the data source
     */
    suspend fun insertContactStream(repo: Contacts)

    /**
     * Delete item from the data source
     */
    suspend fun deleteContactStream(repo: Contacts)

    /**
     * Delete all items from the data source
     */
    suspend fun deleteAllContactsStream()

    /**
     * Update item in the data source
     */
    //suspend fun updateContact(item: Contacts)
}