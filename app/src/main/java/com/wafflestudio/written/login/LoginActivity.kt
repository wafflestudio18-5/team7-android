package com.wafflestudio.written.login

import android.content.Context
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
import com.wafflestudio.written.databinding.ActivityLoginBinding
import com.wafflestudio.written.ui.main.MainActivity
import timber.log.Timber


class LoginActivity : AppCompatActivity() {

    companion object {
        fun createIntent(context : Context): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }

    private lateinit var binding: ActivityLoginBinding
    private lateinit var callbackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Timber.d(AccessToken.getCurrentAccessToken()?.token)
        if (isLoggedIn()) {
            Timber.d("User already logged in")
            // Show the Activity with the logged in user
            goToMainActivity()
        }

        initializeFacebookLogin()
        initializeViews()
    }

    private fun initializeFacebookLogin() {
        callbackManager = CallbackManager.Factory.create()
        LoginManager.getInstance()
            .registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
                override fun onSuccess(loginResult: LoginResult?) {
                    Toast.makeText(this@LoginActivity, "Success Login", Toast.LENGTH_SHORT).show()
                    goToMainActivity()
                }
                override fun onCancel() {
                    Toast.makeText(this@LoginActivity, "Login Cancelled", Toast.LENGTH_SHORT).show()
                }
                override fun onError(exception: FacebookException) {
                    Toast.makeText(this@LoginActivity, exception.message, Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun initializeViews() {
        binding.loginButton.setOnClickListener {
            LoginManager.getInstance().logInWithReadPermissions(this, listOf("public_profile"))
        }
    }

    private fun goToMainActivity() {
        startActivity(MainActivity.createIntent(this))
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    private fun isLoggedIn(): Boolean = AccessToken.getCurrentAccessToken()?.isExpired == true
}
