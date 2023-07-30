package com.ajay.bulksms.services.smsservice

import com.ajay.bulksms.services.remote.RemoteService

class SMSService(private val remoteService: RemoteService) {
    fun sendSMS(to: List<String>, message: String): Boolean {
        //Request Creating
        return remoteService.execute<Boolean>()
    }
}