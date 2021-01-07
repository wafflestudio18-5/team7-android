package com.wafflestudio.written.ui.signup

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.wafflestudio.written.databinding.ActivitySignupBinding
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
class SignUpActivity: AppCompatActivity() {

    companion object {
        fun createIntent(context: Context, facebookid: String, accessToken: String): Intent {
            return Intent(context, SignUpActivity::class.java).apply {
                putExtra("facebookid", facebookid)
                putExtra("accessToken", accessToken)
            }
        }
    }

    @Inject
    lateinit var retrofit: Retrofit
    private lateinit var binding: ActivitySignupBinding
    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signUpButton.setOnClickListener {
            lateinit var facebookid: String
            lateinit var accessToken: String
            intent.getStringExtra("facebookid")?.let {
                facebookid = it
            }
            intent.getStringExtra("accessToken")?.let {
                accessToken = it
            }
            val nickname = binding.nicknameEditText.text.toString()
            viewModel.signup(facebookid, accessToken, nickname)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({
                    goToMainActivity()
                }, {t->
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
                            if (errorResponse.errorcode == 10001L) {
                                Toast.makeText(this, "Facebook Login Error", Toast.LENGTH_SHORT).show()
                            } else if (errorResponse.errorcode == 10002L) {
                                Toast.makeText(this, "Username already exists", Toast.LENGTH_SHORT).show()
                            } else if (errorResponse.errorcode == 10005L) {
                                Toast.makeText(this, "User already signed up", Toast.LENGTH_SHORT).show()
                                goToMainActivity()
                            }
                        } ?: run {
                            Toast.makeText(this, "Null response", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this, "Failed to parse", Toast.LENGTH_SHORT).show()
                    }
                })
        }

    }

    private fun goToMainActivity() {
        startActivity(MainActivity.createIntent(this))
        finish()
    }

}
