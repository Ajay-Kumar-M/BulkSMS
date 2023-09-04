package com.ajay.bulksms.viewModel

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.ajay.bulksms.BulkSMSApplication
import com.ajay.bulksms.services.smsservice.SMSManagerImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CSVActivityViewModel : ViewModel() {

    private val _csvSmsMessage = mutableStateOf(TextFieldValue(""))
    val csvSmsMessage: TextFieldValue
        get() = _csvSmsMessage.value

    private val _startRange = mutableStateOf(TextFieldValue(""))
    val startRange: TextFieldValue
        get() = _startRange.value

    private val _endRange = mutableStateOf(TextFieldValue(""))
    val endRange: TextFieldValue
        get() = _endRange.value

    private val smsManager: SMSManagerImpl by lazy {  SMSManagerImpl(BulkSMSApplication.appContext) }

    private val _messageStatus = mutableStateOf("Send SMS to view result!")
    val messageStatus: String
        get() = _messageStatus.value

    private var csvNumbers : MutableList<String> = mutableListOf("")

    private val _isSendSMSButtonEnable = MutableStateFlow(true)
    val isSendSMSButtonEnable: StateFlow<Boolean>
        get() = _isSendSMSButtonEnable.asStateFlow()

    fun changeCSVSmsMessage(smsMessageNew: TextFieldValue) {
        _csvSmsMessage.value = smsMessageNew
    }

    fun changeStartRange(startRange: TextFieldValue) {
        _startRange.value = startRange
    }

    fun changeEndRange(endRange: TextFieldValue) {
        _endRange.value = endRange
    }

    fun sendSMS() {
        if((csvSmsMessage.text.isEmpty())||(csvNumbers.isEmpty())){
            _messageStatus.value = "Either of the input fields are empty, try after checking inputs."
        } else{
            _isSendSMSButtonEnable.value = false
            smsManager.sendSMSMessage(csvNumbers.subList(startRange.text.toInt()-1,endRange.text.toInt()).toList(), csvSmsMessage){
                _messageStatus.value = it
                _isSendSMSButtonEnable.value = true
            }
        }
    }

    fun extractCSVFile(uri: Uri?, context: Context) {
        csvNumbers.clear()
        uri?.let {
            try {
                val inputStream = context.contentResolver.openInputStream(it)
                inputStream?.bufferedReader()?.use { reader ->
                    var line: String? = reader.readLine()
                    while (line != null) {
                        val data = line.split(",")
                        csvNumbers.add(data[0])
                        Log.d("CSVView","CSV data: $data")
                        line = reader.readLine()
                    }
                }
                inputStream?.close()
            } catch (e: SecurityException) {
                // Handle any errors that may occur during file reading
                Log.d("CSVView","Error reading CSV file: ${e.message}")
                Log.d("CSVView","Error reading CSV file: ${e.stackTrace}")
                if (e.message?.contains("com.android.externalstorage has no access to content") == true){
                    if (Build.VERSION.SDK_INT >= 30) {
                        val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                        intent.data = Uri.parse("package:com.android.externalstorage")
                        ContextCompat.startActivity(context, intent, null)
                    } else {
                        Toast.makeText(context, "Security issue, please check storage permissions.", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(context, "Security issue, please check storage permissions.", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Log.d("CSVView","Error reading CSV file: ${e.message}")
                Log.d("CSVView","Error reading CSV file: ${e.stackTrace}")
            }
        }
    }
}