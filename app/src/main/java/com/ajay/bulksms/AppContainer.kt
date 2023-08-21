package com.ajay.bulksms

import android.content.Context
import com.ajay.bulksms.database.ContactsDatabase
import com.ajay.bulksms.database.ContactsRepository
import com.ajay.bulksms.database.ContactsRepositoryImp

/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val contactsRepository: ContactsRepository
}

/**
 * [AppContainer] implementation that provides instance of [OfflineReposRepository]
 */
class AppDataContainer(
    private val context: Context
) : AppContainer {
    /**
     * Implementation for [RepoRepository]
     */
    override val contactsRepository: ContactsRepository by lazy {
        ContactsRepositoryImp(ContactsDatabase.getDatabase(context).contactsDao())
    }
}