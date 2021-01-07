package com.wafflestudio.written.ui.splash

import android.os.Bundle
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity
import com.wafflestudio.written.ui.login.LoginActivity
import com.wafflestudio.written.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        startActivity(LoginActivity.createIntent(this))
//        finish()
        val nextActivity = if (PreferenceManager.getDefaultSharedPreferences(this)
                .contains("ACCESS_TOKEN")) {
            MainActivity.createIntent(this)
        } else {
            LoginActivity.createIntent(this)
        }
        startActivity(nextActivity)
        finish()
    }

}