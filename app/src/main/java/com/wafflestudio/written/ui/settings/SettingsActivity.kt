package com.wafflestudio.written.ui.settings

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.wafflestudio.written.databinding.ActivitySettingsBinding
import com.wafflestudio.written.ui.login.LoginActivity
import com.wafflestudio.written.ui.settings.edit.EditUserActivity
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

@AndroidEntryPoint
class SettingsActivity : AppCompatActivity() {

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, SettingsActivity::class.java)
        }
    }

    private lateinit var binding: ActivitySettingsBinding
    private val viewModel: SettingsViewModel by viewModels()

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getUser().subscribe({
            binding.writerText.text = it.nickname
        }, {
            Timber.d(it)
        }).also { compositeDisposable.add(it) }

        binding.backText.setOnClickListener {
            finish()
        }

        binding.editProfileLayout.setOnClickListener {
            startActivity(EditUserActivity.createIntent(this))
        }

        binding.logoutLayout.setOnClickListener {
            showLogoutDialog()
            viewModel.observeConfirmLogout()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it) {
                        PreferenceManager.getDefaultSharedPreferences(this).edit {
                            remove("AUTH_TOKEN")
                        }
                        val intent = LoginActivity.createIntent(this)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                        startActivity(intent)
                    }
                }, {
                    Toast.makeText(this, "로그아웃에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                })
        }

    }

    fun showLogoutDialog() {
        val dialog = LogoutDialogFragment()
        dialog.show(supportFragmentManager, "LogoutDialogFragment")
    }
}