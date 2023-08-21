package com.ajay.bulksms.database

import com.ajay.bulksms.contactlist.Contacts
import kotlinx.coroutines.flow.Flow

class ContactsRepositoryImp(private val contactsDao: ContactsDao) : ContactsRepository {

    override var allContacts: Flow<List<Contacts>> = contactsDao.getAllContacts()

    override fun getAllContactsStream(): Flow<List<Contacts>> = contactsDao.getAllContacts()

    override fun getContactStream(id: Int): Flow<Contacts?> = contactsDao.getContact(id)

    override fun getContacts(vararg ids: Int): Flow<List<Contacts>?> = contactsDao.getContact(ids.toList())

    override suspend fun insertContactStream(contact: Contacts) = contactsDao.insert(contact)

    override suspend fun deleteContactStream(contact: Contacts) = contactsDao.delete(contact)

    override suspend fun deleteAllContactsStream() = contactsDao.deleteAllContacts()


    //override suspend fun updateItem(item: Repos) = itemDao.update(item)

}