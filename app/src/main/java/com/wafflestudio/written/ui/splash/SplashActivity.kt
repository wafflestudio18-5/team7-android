package com.wafflestudio.written.ui.splash

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.wafflestudio.written.databinding.ActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val nextActivity = if (PreferenceManager.getDefaultSharedPreferences(this)
//                .contains("ACCESS_TOKEN")) {
//            MainActivity.createIntent(this)
//        } else {
//            LoginActivity.createIntent(this)
//        }
    }

}