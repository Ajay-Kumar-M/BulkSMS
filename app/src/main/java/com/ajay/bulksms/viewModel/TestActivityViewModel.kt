package com.ajay.bulksms.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import com.ajay.bulksms.BulkSMSApplication
import com.ajay.bulksms.services.smsservice.SMSManagerImpl

class TestActivityViewModel : ViewModel() {

    private val _testSmsMessage = mutableStateOf(TextFieldValue(""))
    val testSmsMessage: TextFieldValue
        get() = _testSmsMessage.value

    private val _phoneNumber = mutableStateOf(TextFieldValue(""))
    val phoneNumber: TextFieldValue
        get() = _phoneNumber.value

    private val smsManager: SMSManagerImpl by lazy { SMSManagerImpl(BulkSMSApplication.appContext) }

    private val _messageStatus = mutableStateOf("Send SMS to view result!")
    val messageStatus: String
        get() = _messageStatus.value

    private val tempNumber: MutableList<String> = mutableListOf("")

    fun changeTestSmsMessage(smsMessageNew: TextFieldValue) {
        _testSmsMessage.value = smsMessageNew
    }

    fun changePhoneNumber(phoneNumberNew: TextFieldValue) {
        _phoneNumber.value = phoneNumberNew
    }

    fun sendTestSMS() {
        tempNumber.clear()
        tempNumber.add(phoneNumber.text)
        smsManager.sendSMSMessage(tempNumber.toList(), testSmsMessage) {
            _messageStatus.value = it
        }
    }
}