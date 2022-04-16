package com.lucki.hamur.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.onesignal.OneSignal

class MyApplication: Application() {

    private val oneSignalKey = "02184247-b9a5-413a-833f-e1fa68d22094"
    private val keyDevAppsflyer = "gmP73NkxS5HPpFhTzg9DRH"
    private var getData = false
    companion object{
        var liveDataAppsFlyer = MutableLiveData<MutableMap<String, Any>>()
    }

    override fun onCreate() {
        super.onCreate()
        AppsFlyerLib.getInstance().init(keyDevAppsflyer, appsFlyerConversion(), this)
        AppsFlyerLib.getInstance().start(this)
        OneSignal.initWithContext(this);
        OneSignal.setAppId(oneSignalKey)
    }

    private fun appsFlyerConversion(): AppsFlyerConversionListener {

        return object : AppsFlyerConversionListener {
            override fun onConversionDataSuccess(data: MutableMap<String, Any>?) {
                if (!getData){
                    data?.let {
                        liveDataAppsFlyer.postValue(it)
                    }
                    getData = true
                }
            }

            override fun onConversionDataFail(error: String?) {
                Log.d("error", "$error")
            }

            override fun onAppOpenAttribution(data: MutableMap<String, String>?) {
                Log.d("data", "$data")
            }

            override fun onAttributionFailure(error: String?) {
                Log.d("error", "$error")
            }
        }
    }
}