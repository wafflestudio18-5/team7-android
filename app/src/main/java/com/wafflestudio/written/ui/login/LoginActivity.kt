package com.wafflestudio.written.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.wafflestudio.written.databinding.ActivityLoginBinding
import com.wafflestudio.written.models.ApiErrorResponse
import com.wafflestudio.written.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.HttpException
import retrofit2.Retrofit
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    @Inject
    lateinit var retrofit: Retrofit

    companion object {
        fun createIntent(context : Context): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }

    private lateinit var binding: ActivityLoginBinding
    private lateinit var callbackManager: CallbackManager
    private val viewModel: LoginViewModel by viewModels()

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
                    // login to facebook
                    // check if user exists or not
                    loginResult?.accessToken?.let { accessToken ->
                        viewModel.login(accessToken.userId, accessToken.token)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({
                                goToMainActivity()
                            }, { t ->
                                Timber.d(t)
                                if (t is HttpException) {
                                    val converter: Converter<ResponseBody, ApiErrorResponse> =
                                        retrofit.responseBodyConverter(
                                            ApiErrorResponse::class.java, arrayOfNulls<Annotation>(0)
                                        )
                                    val response = t.response()
                                    try {
                                        response?.errorBody()?.let { converter.convert(it) }
                                    } catch (e: Exception) {
                                        null
                                    }?.let { errorResponse ->
                                        Timber.d("YAY! $errorResponse")
                                    } ?: run {
                                        Toast.makeText(this@LoginActivity, "2", Toast.LENGTH_SHORT).show()
                                    }
                                } else {
                                    Toast.makeText(this@LoginActivity, "1", Toast.LENGTH_SHORT).show()
                                }
                            })
                    }
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
