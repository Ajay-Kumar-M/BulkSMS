package com.ajay.bulksms.database

import com.ajay.bulksms.model.Contact
import kotlinx.coroutines.flow.Flow

class ContactsRepositoryImp(private val contactsDao: ContactsDao) : ContactsRepository {

    override var allContacts: Flow<List<Contact>> = contactsDao.getAllContacts()

    override fun getAllContactsStream(): Flow<List<Contact>> = contactsDao.getAllContacts()

    override fun getContactStream(id: Int): Flow<Contact?> = contactsDao.getContact(id)

    override fun getContacts(vararg ids: Int): Flow<List<Contact>?> = contactsDao.getContact(ids.toList())

    override suspend fun insertContactStream(contact: Contact) = contactsDao.insert(contact)

    override suspend fun deleteContactStream(contact: Contact) = contactsDao.delete(contact)

    override suspend fun deleteAllContactsStream() = contactsDao.deleteAllContacts()

    //override suspend fun updateItem(item: Repos) = itemDao.update(item)

}