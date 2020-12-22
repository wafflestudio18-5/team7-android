package com.wafflestudio.written

import android.app.Application
import com.wafflestudio.written.di.AppComponent
import com.wafflestudio.written.di.DaggerAppComponent

class WrittenApplication : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.create()
    }


}