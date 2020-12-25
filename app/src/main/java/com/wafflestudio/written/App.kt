package com.wafflestudio.written

import android.app.Application
import com.facebook.appevents.AppEventsLogger
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        AppEventsLogger.activateApp(this)
    }
}
