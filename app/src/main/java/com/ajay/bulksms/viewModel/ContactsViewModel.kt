package com.ajay.bulksms.viewModel

import android.content.Context
import android.provider.ContactsContract
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajay.bulksms.database.ContactsRepository
import com.ajay.bulksms.model.Contact
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ContactsViewModel(private val contactsRepository: ContactsRepository): ViewModel() {

    private var _filterContact = mutableListOf<Contact>().toMutableStateList()
    val filterContact: List<Contact>
        get() = _filterContact.toList()

    private var deviceContactsList: MutableList<Contact> = mutableListOf()

    private val allContacts: Flow<List<Contact>> = contactsRepository.getAllContactsStream()

    var selectedContacts : MutableList<Int> =  mutableListOf()

    private val _contactSearchString = mutableStateOf(TextFieldValue(""))
    val contactSearchString: TextFieldValue
        get() = _contactSearchString.value

    init {
        Log.d("ContactView","init called")
        fetchAllContact()
    }

    private fun fetchAllContact() {
        allContacts.collectA(viewModelScope) {
            deviceContactsList.clear()
            deviceContactsList.addAll(it)
            _filterContact.addAll(deviceContactsList)
        }
    }

    fun addContactToSelectedList(id: Int){
        selectedContacts.add(id)
    }

    fun search() {
        if(contactSearchString.text.isNotEmpty())
        {
            _filterContact.clear()
            _filterContact.addAll(
                deviceContactsList.filter {
                    it.displayName.contains(contactSearchString.text, true) or it.phoneNumber.contains(contactSearchString.text, true)
                }
            )
        }
    }

    fun removeContactFromSelectedList(id: Int){
        selectedContacts.remove(id)
    }

    fun changeContactSearchString(searchStringNew: TextFieldValue) {
        _contactSearchString.value = searchStringNew
        if(searchStringNew.text.isEmpty()){
            _filterContact.clear()
            _filterContact.addAll(deviceContactsList)
        }
    }

    fun getAllDeviceContacts(context: Context) {

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

            viewModelScope.launch {
                //_deviceContactsList.clear()
                contactsRepository.deleteAllContactsStream()
            }

            while (cursor.moveToNext()) {
                val id = cursor.getInt(idColumn)
                val displayName = cursor.getString(nameColumn)
                val phoneNumber = cursor.getString(numberColumn)
                val initials: String = if (displayName.isEmpty()) {
                    phoneNumber.takeWhile { it.isDigit() }.take(2)
                } else {
                    displayName.take(2)
                }
                viewModelScope.launch {
                    contactsRepository.insertContactStream(
                        Contact(
                            id,
                            initials,
                            displayName,
                            phoneNumber
                        )
                    )
                }
            }
        }
    }

}

/*

    var testName: Flow<String> = flow {
        repeat(20) {
            delay(2000)
            emit("test $it")
        }
    }

    fun testFlow() {
        testName.collectA(viewModelScope) {
            Log.d("ContactsViewModel", it)
        }
    }


 */