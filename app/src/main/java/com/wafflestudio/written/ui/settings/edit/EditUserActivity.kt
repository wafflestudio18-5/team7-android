package com.wafflestudio.written.ui.settings.edit

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.wafflestudio.written.databinding.ActivityEditUserBinding
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

@AndroidEntryPoint
class EditUserActivity : AppCompatActivity() {

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, EditUserActivity::class.java)
        }
    }

    private lateinit var binding: ActivityEditUserBinding
    private val viewModel: EditUserViewModel by viewModels()

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getUser().subscribe({
            binding.writerEdittext.setText(it.nickname)
            binding.descriptionEdittext.setText(it.description)
        }, {
            Timber.d(it)
        }).also { compositeDisposable.add(it) }

        binding.backText.setOnClickListener {
            finish()
        }

        binding.doneText.setOnClickListener {
            val updatedUserName = binding.writerEdittext.text.toString()
            val updatedDescription = binding.descriptionEdittext.text.toString()
            viewModel.updateUser(updatedUserName, updatedDescription)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Toast.makeText(this, "사용자 정보가 변경되었습니다.", Toast.LENGTH_SHORT).show()
                    viewModel.resetuser()
                    finish()
                }, {
                    Toast.makeText(this, "정보가 변경되지 않았습니다.", Toast.LENGTH_SHORT).show()
                    Timber.d(it)
                }).also { compositeDisposable.add(it) }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}