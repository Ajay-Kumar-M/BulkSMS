package com.ajay.bulksms

import android.content.Context
import android.provider.ContactsContract
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.ajay.bulksms.contactlist.Contact

class ContactsViewModel: ViewModel() {

    private var _deviceContactsList = mutableListOf<Contact>().toMutableStateList()
    val deviceContactsList: List<Contact>
        get() = _deviceContactsList.toList()

    fun getAllContacts(context: Context) { //: List<Contact> {
        val projection = arrayOf(
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        )

        context.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            projection,
            null,
            null,
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY
        )?.use { cursor ->
            val idColumn = cursor.getColumnIndex(ContactsContract.Contacts._ID)
            val nameColumn = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)
            val numberColumn = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)

            _deviceContactsList.clear()

            while (cursor.moveToNext()) {
                val id = cursor.getString(idColumn)
                val displayName = cursor.getString(nameColumn)
                val phoneNumber = cursor.getString(numberColumn)
                val initials: String = if(displayName.isEmpty()) {
                    phoneNumber.takeWhile { it.isDigit() }.take(2)
                } else {
                    displayName.take(2)
                }
                val contact = Contact(id, initials, displayName, phoneNumber, isSelected = false)
                _deviceContactsList.add(contact)
            }
        }
    }

    fun addContactstoUser(){

    }


}

