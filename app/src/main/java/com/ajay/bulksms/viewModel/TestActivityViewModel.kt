package com.ajay.bulksms.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajay.bulksms.BulkSMSApplication
import com.ajay.bulksms.services.smsservice.SMSManagerImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

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

    private val _isSendSMSButtonEnable = MutableStateFlow(true)
    val isSendSMSButtonEnable: StateFlow<Boolean>
        get() = _isSendSMSButtonEnable.asStateFlow()

    fun changeTestSmsMessage(smsMessageNew: TextFieldValue) {
        _testSmsMessage.value = smsMessageNew
    }

    fun changePhoneNumber(phoneNumberNew: TextFieldValue) {
        _phoneNumber.value = phoneNumberNew
    }

    fun sendTestSMS() {
        if((testSmsMessage.text.isEmpty())||(phoneNumber.text.isEmpty())){
            _messageStatus.value = "Either of the input fields are empty, try after checking inputs."
        } else {
            _isSendSMSButtonEnable.value = false
            tempNumber.clear()
            tempNumber.add(phoneNumber.text)
            viewModelScope.launch(Dispatchers.IO) {
                smsManager.sendSMSMessage(tempNumber.toList(), testSmsMessage) {
                    _messageStatus.value = it
                    _isSendSMSButtonEnable.value = true
                }
            }
        }
    }

}