package com.ajay.bulksms


import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class BulkSMSApplication : Application() {

    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this
        container = AppDataContainer(this)
    }
}