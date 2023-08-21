package com.ajay.bulksms.components

import android.app.Activity
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.telephony.PhoneNumberUtils
import android.telephony.SmsManager
import android.util.Log
import androidx.compose.ui.text.input.TextFieldValue
import androidx.core.content.ContextCompat


class SMSManagerImpl(private val context: Context) {

    private val smsManager: SmsManager =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context.getSystemService(SmsManager::class.java)
        } else {
            SmsManager.getDefault()
        }

    fun sendSMSMessage(
        phoneNumber: List<String>,
        smsMessage: TextFieldValue,
        completion: (String) -> Unit
    ) {

        val myMsg: String = smsMessage.text.trim()

        var finalMessage = ""

        val SENT = "SMS_SENT"
        val DELIVERED = "SMS_DELIVERED"
        var deliveredMessageCount = 0
        var spaceRemovedNumber = ""

        ContextCompat.registerReceiver(context, object : BroadcastReceiver() {
            override fun onReceive(arg0: Context, arg1: Intent) {
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        finalMessage += "\n${arg1.getStringExtra("phoneNumber")} - SMS sent"
                    }

                    SmsManager.RESULT_ERROR_GENERIC_FAILURE -> finalMessage += "\n${
                        arg1.getStringExtra(
                            "phoneNumber"
                        )
                    } - Generic failure"

                    SmsManager.RESULT_ERROR_NO_SERVICE -> finalMessage += "\n${arg1.getStringExtra("phoneNumber")} - No service"

                    SmsManager.RESULT_ERROR_NULL_PDU -> finalMessage += "\n${arg1.getStringExtra("phoneNumber")} - Null PDU"

                    SmsManager.RESULT_ERROR_RADIO_OFF -> finalMessage += "\n${arg1.getStringExtra("phoneNumber")} - Radio Off"

                    SmsManager.RESULT_CANCELLED -> finalMessage += "\n${arg1.getStringExtra("phoneNumber")} - Result Cancelled"

                    SmsManager.RESULT_ENCODING_ERROR -> finalMessage += "\n${arg1.getStringExtra("phoneNumber")} - Encoding Error"

                    SmsManager.RESULT_ERROR_NONE -> finalMessage += "\n${arg1.getStringExtra("phoneNumber")} - Error None"

                    SmsManager.RESULT_INVALID_ARGUMENTS -> finalMessage += "\n${
                        arg1.getStringExtra(
                            "phoneNumber"
                        )
                    } - Invalid Arguments"

                    SmsManager.RESULT_INTERNAL_ERROR -> finalMessage += "\n${arg1.getStringExtra("phoneNumber")} - Internal Error"

                    SmsManager.RESULT_INVALID_SMS_FORMAT -> finalMessage += "\n${
                        arg1.getStringExtra(
                            "phoneNumber"
                        )
                    } - Invalid SMS Format"

                    SmsManager.RESULT_INVALID_STATE -> finalMessage += "\n${arg1.getStringExtra("phoneNumber")} - Invalid state"

                    SmsManager.RESULT_NO_DEFAULT_SMS_APP -> finalMessage += "\n${
                        arg1.getStringExtra(
                            "phoneNumber"
                        )
                    } - No Default SMS App"

                    SmsManager.RESULT_NETWORK_REJECT -> finalMessage += "\n${arg1.getStringExtra("phoneNumber")} - Network Reject"

                    SmsManager.RESULT_RECEIVE_DISPATCH_FAILURE -> finalMessage += "\n${
                        arg1.getStringExtra(
                            "phoneNumber"
                        )
                    } - Receive Dispatch Failure"

                    SmsManager.RESULT_NETWORK_ERROR -> finalMessage += "\n${arg1.getStringExtra("phoneNumber")} - Network error (or) Invalid number"
                }

                deliveredMessageCount++
                if (deliveredMessageCount == phoneNumber.size) {
                    completion(finalMessage)
                }
            }
        }, IntentFilter(SENT), ContextCompat.RECEIVER_EXPORTED)

        ContextCompat.registerReceiver(context, object : BroadcastReceiver() {
            override fun onReceive(arg0: Context, arg1: Intent) {
                when (resultCode) {
                    Activity.RESULT_OK -> Log.i(
                        "SMSManager",
                        "\n${arg1.getStringExtra("phoneNumber")} - SMS delivered"
                    )

                    Activity.RESULT_CANCELED -> Log.i(
                        "SMSManager",
                        "\n${arg1.getStringExtra("phoneNumber")} - SMS not delivered"
                    )
                }
            }
        }, IntentFilter(DELIVERED), ContextCompat.RECEIVER_EXPORTED)

        phoneNumber.forEach { singlePhoneNumber ->

            // Process each number if required
            spaceRemovedNumber = singlePhoneNumber.replace(" ","",true)

            val sentPI = PendingIntent.getBroadcast(
                context,
                0,
                Intent(SENT).putExtra("phoneNumber", spaceRemovedNumber),
                PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            val deliveredPI = PendingIntent.getBroadcast(
                context,
                0,
                Intent(DELIVERED).putExtra("phoneNumber", spaceRemovedNumber),
                PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            if (PhoneNumberUtils.isGlobalPhoneNumber(spaceRemovedNumber)) {
                try {
                    smsManager.sendTextMessage(spaceRemovedNumber, null, myMsg, sentPI, deliveredPI)
                } catch (e: Exception) {
                    Log.d("SMSManager", "Error sending SMS for $spaceRemovedNumber")
                    Log.d("SMSManager", "Exception ${e.message}")
                    Log.d("SMSManager", "Exception ${e.stackTrace}")
                    finalMessage += "$phoneNumber - Error sending SMS"
                }
            } else {
                finalMessage += "\n${spaceRemovedNumber} - Number not valid"
            }
            completion(finalMessage)
        }
    }
}

/*

// send large text message in parts
    ArrayList<String> parts = smsManager.divideMessage(message);
    smsManager.sendMultipartTextMessage(phoneNumber, null, parts, null, null);


        //var deliveryBroadcastReciever: BroadcastReceiver = DeliverReceiver(context)

        internal class DeliverReceiver(context: Context) : BroadcastReceiver() {
    override fun onReceive(context: Context, arg1: Intent) {
        when (resultCode) {
            Activity.RESULT_OK -> Toast.makeText(
                context, "sms_delivered",
                Toast.LENGTH_SHORT
            ).show()

            Activity.RESULT_CANCELED -> Toast.makeText(
                context, "sms_not_delivered",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
 */


