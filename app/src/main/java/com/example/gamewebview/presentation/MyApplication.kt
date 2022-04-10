package com.example.gamewebview.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.facebook.FacebookSdk
import com.facebook.applinks.AppLinkData
import com.onesignal.OneSignal

class MyApplication: Application() {

    private val oneSignalKey = "1452d412-4584-4ab2-85c7-ea75c9314125"
    private val keyDevAppsflyer = "dadvik4hiVqLnmEvwp6iHU"
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
                    data?.let {
                        Log.d("test2", "$data")
                        liveDataAppsFlyer.postValue(it)
                    }
            }

            override fun onConversionDataFail(error: String?) {
                Log.d("test1", "$error")
            }

            override fun onAppOpenAttribution(data: MutableMap<String, String>?) {
                Log.d("test1", "$data")
                    liveDataAppsFlyer.postValue(mutableMapOf())
            }

            override fun onAttributionFailure(error: String?) {
                Log.d("test1", "$error")
            }
        }
    }
}