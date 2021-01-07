package com.wafflestudio.written

import android.app.Application
import android.content.Context
import com.facebook.appevents.AppEventsLogger
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application() {

    companion object {
        lateinit var APP: Context
    }

    override fun onCreate() {
        super.onCreate()

        APP = this

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        AppEventsLogger.activateApp(this)
    }
}
