package com.diego.tweetssentimentsanalyzer

import android.app.Application
import android.content.Context
import android.util.Log
import com.diego.tweetssentimentsanalyzer.di.module
import com.twitter.sdk.android.core.Twitter
import org.koin.android.ext.android.startKoin
import com.twitter.sdk.android.core.TwitterAuthConfig
import com.twitter.sdk.android.core.DefaultLogger
import com.twitter.sdk.android.core.TwitterConfig



class App : Application() {

    companion object {
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this.applicationContext

        startKoin(this, listOf(module))

        Twitter.initialize(this)
    }
}