package com.ajay.bulksms

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.view.View
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.text.input.TextFieldValue
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.ajay.bulksms.contactlist.User
import com.ajay.bulksms.services.remote.RemoteService
import com.ajay.bulksms.services.smsservice.SMSService

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

    private val smsService = SMSService(RemoteService())

    private val permissionRequest = 101

    
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

    fun sendSMS() {
        val isMessageDelivered = smsService.sendSMS(
            to = listOf("+918973745057"),
            message = "Hai all"
        )
        println(isMessageDelivered)
    }

    fun sendSMSTemp(){

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


public void sendLongSMS() {
    String phoneNumber = "0123456789";
    String message = "Hello World! Now we are going to demonstrate " +
        "how to send a message with more than 160 characters from your Android application.";
    SmsManager smsManager = SmsManager.getDefault();
    ArrayList<String> parts = smsManager.divideMessage(message);
    smsManager.sendMultipartTextMessage(phoneNumber, null, parts, null, null);
}



    fun smsPermissionCheck(context: Context){
//        val permissionCheck = ContextCompat.checkSelfPermission(getApplication(), android.Manifest.permission.SEND_SMS)
//        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
//            //myMessage()
//        } else {
//            ActivityCompat.requestPermissions(
//                context, arrayOf(android.Manifest.permission.SEND_SMS),
//                permissionRequest)
//        }
    }

 (app: Application)
    : AndroidViewModel(app) {
    //: ViewModel() { //AndroidViewModel(Application()) {

*/