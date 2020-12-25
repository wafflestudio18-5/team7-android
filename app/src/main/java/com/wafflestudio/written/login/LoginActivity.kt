package com.wafflestudio.written.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.wafflestudio.written.MainActivity
import com.wafflestudio.written.databinding.ActivityLoginBinding
import timber.log.Timber


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var callbackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (isLoggedIn()) {
            // Show the Activity with the logged in user
            startActivity(MainActivity.createIntent(this))
            finish()
        }

        callbackManager = CallbackManager.Factory.create()
        LoginManager.getInstance()
            .registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
                override fun onSuccess(loginResult: LoginResult?) {
                    Timber.d("Success Login")
                    // Get User's Info
                }
                override fun onCancel() {
                    Toast.makeText(this@LoginActivity, "Login Cancelled", Toast.LENGTH_LONG).show()
                }
                override fun onError(exception: FacebookException) {
                    Toast.makeText(this@LoginActivity, exception.message, Toast.LENGTH_LONG).show()
                }
            })

        binding.loginButton.setOnClickListener {
            LoginManager.getInstance().logInWithReadPermissions(this, listOf("public_profile"))
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun isLoggedIn(): Boolean {
        val accessToken = AccessToken.getCurrentAccessToken()
        val isLoggedIn = accessToken?.isExpired == true
        return isLoggedIn
    }

}