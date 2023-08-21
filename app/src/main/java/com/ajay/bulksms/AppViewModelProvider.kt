package com.ajay.bulksms

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

/**
 * Provides Factory to create instance of ViewModel for the entire Inventory app
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            ContactsViewModel(inventoryApplication().container.contactsRepository)
        }
        initializer {
            MainViewModel(inventoryApplication().container.contactsRepository)
        }
    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [BulkSMSApplication].
 */
fun CreationExtras.inventoryApplication(): BulkSMSApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BulkSMSApplication)

/*
        // Initializer for ItemEditViewModel
//        initializer {
//            ItemEditViewModel(
//                this.createSavedStateHandle()
//            )
//        }
        // Initializer for ItemEntryViewModel
 */