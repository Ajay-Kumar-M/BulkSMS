package com.ajay.bulksms

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import com.ajay.bulksms.contactlist.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {
    private var _contactsList = MutableStateFlow<MutableList<User>>(mutableListOf())
    public var contactsList = _contactsList.asStateFlow()

//    private val _mainViewData = MutableStateFlow(MainViewData())
//    val mainViewData: StateFlow<MainViewData> = _mainViewData.asStateFlow()
//
    //private val _smsMessage = MutableStateFlow("")
    //val smsMessage = _smsMessage.asStateFlow()

    //var smsMessage by mutableStateOf("")

    private val _smsMessage = MutableStateFlow("")
    val smsMessage = _smsMessage.asStateFlow()

    fun addContact(initials: String, name: String, mobileNumber: String) {
        _contactsList.value.add(User(initials, name, mobileNumber))
    }

    init {
        _contactsList.value = mutableListOf(
            User("AK","Ajay Kumar", "9876543210"),
            User("AK","Ajay Kumar", "9876543210"),
            User("AK","Ajay Kumar", "9876543210"),
            User("AK","Ajay Kumar", "9876543210"),
            User("AK","Ajay Kumar", "9876543210"),
            User("AK","Ajay Kumar", "9876543210"),
            User("AK","Ajay Kumar", "9876543210"),
            User("AK","Ajay Kumar", "9876543210"),
            User("AK","Ajay Kumar", "9876543210"),
            User("AK","Ajay Kumar", "9876543210"),
            User("AK","Ajay Kumar", "9876543210"),
            User("AK","Ajay Kumar", "9876543210"),
            User("AK","Ajay Kumar", "9876543210"),
            User("AK","Ajay Kumar", "9876543210"),
            User("AK","Ajay Kumar", "9876543210"),
            User("AK","Ajay Kumar", "9876543210")
        )
    }

    fun deleteUser(removeAt: Int) {
        _contactsList.value.removeAt(removeAt)
    }

//    fun changeStartRange(startRange: String){
//        _mainViewData.value.startRange = startRange
//    }
//
//    fun changeEndRange(endRange: String){
//        _mainViewData.value.endRange = endRange
//    }
//
    fun changeSmsMessage(smsMessageNew: String) {
    println("aaaa "+smsMessageNew)
    println("bbbb "+smsMessage.value)
        _smsMessage.value = smsMessageNew
    println("cccc "+smsMessageNew)
    println("dddd "+smsMessage.value)
    }
}