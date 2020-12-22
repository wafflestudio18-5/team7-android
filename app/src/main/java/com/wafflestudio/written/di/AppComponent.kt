package com.wafflestudio.written.di

import com.wafflestudio.written.MainActivity
import dagger.Component

@Component(modules = [NetworkModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(): AppComponent
    }

    fun inject(activity: MainActivity)

}
