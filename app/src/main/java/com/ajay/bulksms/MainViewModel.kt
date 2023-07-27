package com.ajay.bulksms

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import com.ajay.bulksms.contactlist.User

class MainViewModel : ViewModel() {
    private var _contactsList = mutableListOf<User>().toMutableStateList()
    val contactsList: List<User>
        get() = _contactsList.toList()

    private val _smsMessage = mutableStateOf(TextFieldValue(""))
    val smsMessage: TextFieldValue
        get() = _smsMessage.value

    private val _startRange = mutableStateOf(TextFieldValue(""))
    val startRange: TextFieldValue
        get() = _startRange.value

    private val _endRange = mutableStateOf(TextFieldValue(""))
    val endRange: TextFieldValue
        get() = _endRange.value
    
    fun addContact(initials: String, name: String, mobileNumber: String) {
//        _contactsList.value.add(User(1,initials, name, mobileNumber))
    }

    init {
        _contactsList.addAll(
            listOf(
                User(1,"AK","Ajay Kumar1", "9876543210"),
                User(2,"AK","Ajay Kumar2", "9876543210"),
                User(3,"AK","Ajay Kumar3", "9876543210"),
                User(4,"AK","Ajay Kumar4", "9876543210"),
                User(5,"AK","Ajay Kumar5", "9876543210"),
                User(6,"AK","Ajay Kumar6", "9876543210"),
                User(7,"AK","Ajay Kumar7", "9876543210"),
                User(8,"AK","Ajay Kumar8", "9876543210"),
                User(9,"AK","Ajay Kumar9", "9876543210"),
                User(10,"AK","Ajay Kumar10", "9876543210"),
                User(11,"AK","Ajay Kumar11", "9876543210"),
                User(12,"AK","Ajay Kumar12", "9876543210"),
                User(13,"AK","Ajay Kumar13", "9876543210"),
                User(14,"AK","Ajay Kumar14", "9876543210"),
                User(15,"AK","Ajay Kumar15", "9876543210"),
                User(16,"AK","Ajay Kumar16", "9876543210")
            )
        )
    }

    fun deleteUser(user: User) {
        _contactsList.remove(user)
    }

    fun changeStartRange(startRange: TextFieldValue){
        _startRange.value = startRange
    }

    fun changeEndRange(endRange: TextFieldValue){
        _endRange.value = endRange
    }

    fun changeSmsMessage(smsMessageNew: TextFieldValue) {
        _smsMessage.value = smsMessageNew
    }
}


/*
Bug codes


Text change did not work when using String type -> TextFieldValue
fun changeSmsMessage(smsMessageNew: String) {
println("aaaa "+smsMessageNew)
println("bbbb "+smsMessage.value)
    _smsMessage.value = smsMessageNew
println("cccc "+smsMessageNew)
println("dddd "+smsMessage.value)
}

        val removeIndex = _contactsList.filter { (id) -> id.equals(removeID) }
        val index = _contactsList.withIndex() { removeIndex }
        _contactsList.value.removeAt(removeID)

        val index3 = findIndex(_contactsList.value, removeID)
        val index1 = _contactsList.withIndex( it.ID == it.value )



    fun findIndex(contactsList: MutableList<User>, item: Int): Int {
        for (i in contactsList.indices) {
            if (contactsList.User.id[i] == item) {
                return i
            }
        }
        return -1
    }
*/