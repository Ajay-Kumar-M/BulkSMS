package com.ajay.bulksms.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajay.bulksms.BulkSMSApplication
import com.ajay.bulksms.database.ContactsRepository
import com.ajay.bulksms.model.Contact
import com.ajay.bulksms.model.User
import com.ajay.bulksms.services.smsservice.SMSManagerImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MainViewModel(private val contactsRepository: ContactsRepository) : ViewModel() {

    private var _contactsList = mutableListOf<User>().toMutableStateList()
    val contactsList: List<User>
        get() = _contactsList.toList()

    private val _smsMessage = mutableStateOf(TextFieldValue(""))
    val smsMessage: TextFieldValue
        get() = _smsMessage.value

    private val smsManager: SMSManagerImpl by lazy { SMSManagerImpl(BulkSMSApplication.appContext) }

    private val _messageStatus = mutableStateOf("Send SMS to view result!")
    val messageStatus: String
        get() = _messageStatus.value

    fun addSelectedContactsToMainScreen(usersList: MutableList<Int>) {
        contactsRepository.getContacts(*usersList.toIntArray())
            .collectA(viewModelScope) { contact ->
                contact?.let { contacts ->
                    _contactsList.addAll(
                        contacts.map { it.toUser() }
                    )
                }
            }
    }

    fun deleteUser(user: User) {
        _contactsList.remove(user)
    }

    fun changeSmsMessage(smsMessageNew: TextFieldValue) {
        _smsMessage.value = smsMessageNew
    }

    fun sendSMS() {
        if((smsMessage.text.isEmpty())||(contactsList.isEmpty())){
            _messageStatus.value = "Either of the input fields are empty, try after checking inputs."
        } else{
            smsManager.sendSMSMessage(contactsList.map { it.mobileNumber }, smsMessage){
                _messageStatus.value = it
            }
        }
    }

}

fun Contact.toUser(): User = User(
    this.id,
    this.initials,
    this.displayName,
    this.phoneNumber
)

fun <T> Flow<T>.collectA(scope: CoroutineScope, action: (T) -> Unit) {
    scope.launch {
        this@collectA.collect {
            action.invoke(it)
        }
    }
}

/*
Bug codes


Text change did not work when using String type -> TextFieldValue
fun changeSmsMessage(smsMessageNew: String) {

    _smsMessage.value = smsMessageNew
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


        val isMessageDelivered = smsService.sendSMS(
            to = listOf("+918973745057"),
            message = "Hai all"
        )
        println(isMessageDelivered)



*/
