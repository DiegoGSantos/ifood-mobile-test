package com.diego.tweetssentimentsanalyzer.manager

import android.content.Context
import android.net.ConnectivityManager
import com.diego.tweetssentimentsanalyzer.App

class NetManager() {

    val isConnectedToInternet: Boolean
        get() {

            val conManager = App.appContext.getSystemService(Context.CONNECTIVITY_SERVICE)
                    as ConnectivityManager

            val ni = conManager.activeNetworkInfo
            return ni != null && ni.isConnected
        }
}