package com.wafflestudio.written.ui.main.my.detail_posting

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.wafflestudio.written.R
import com.wafflestudio.written.databinding.ActivityMyDetailPostingBinding
import com.wafflestudio.written.extension.StringExtension
import com.wafflestudio.written.models.PostingDto
import com.wafflestudio.written.ui.main.write.CloseDialogFragment
import com.wafflestudio.written.ui.main.write.WriteNewActivity
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

@AndroidEntryPoint
class MyDetailPostingActivity : AppCompatActivity() {

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, MyDetailPostingActivity::class.java)
        }
    }

    private val viewModel: MyDetailPostingViewModel by viewModels()
    private lateinit var binding: ActivityMyDetailPostingBinding

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMyDetailPostingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.observePosting()
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                modelView(it)
            }

        val intent = intent
        val posting: PostingDto = intent.getParcelableExtra("posting") ?: run {
            finish()
            return
        }

        viewModel.setPosting(posting)
        viewModel.getPostingDetail(posting.id)

        binding.bottomAppBar.backText.setOnClickListener {
            finish()
        }

        binding.bottomAppBar.publicText.setOnClickListener {
            viewModel.changePublic()
        }

        binding.bottomAppBar.editText.setOnClickListener {
            startActivity(WriteNewActivity.createIntent(this, posting.title, posting))
        }

        binding.bottomAppBar.deleteText.setOnClickListener {
            showDeleteDialog()
            viewModel.observeConfirmDelete()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    Timber.d("boolean! $it")
                    if (it) {
                        viewModel.deletePosting()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({
                                Toast.makeText(this, "deleted!", Toast.LENGTH_SHORT).show()
                                finish()
                            }, {
                                Timber.d(it)
                            })
                            .also { compositeDisposable.add(it) }
                    }
                }
        }


    }

    private fun modelView(posting: PostingDto) {

        when (posting.isPublic) {
            true -> {
                binding.bottomAppBar.publicText.background =
                    ContextCompat.getDrawable(this, R.drawable.layout_public_background)
                binding.bottomAppBar.publicText.setTextColor(
                    ContextCompat.getColor(
                        this,
                        R.color.white
                    )
                )
            }
            false -> {
                binding.bottomAppBar.publicText.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.transparent
                    )
                )
                binding.bottomAppBar.publicText.setTextColor(
                    ContextCompat.getColor(
                        this,
                        R.color.black
                    )
                )
            }
        }

        binding.contentText.gravity = when (posting.alignment) {
            "LEFT" -> Gravity.LEFT
            "CENTER" -> Gravity.CENTER
            else -> Gravity.CENTER
        }

        binding.titleText.text = posting.title
        binding.contentText.text = posting.content
        binding.writerText.text = posting.writer.nickname
        binding.createdAtText.text = StringExtension().modifyCreatedAt(posting.createdAt)
    }

    fun showDeleteDialog() {
        val dialog = DeleteDialogFragment()
        dialog.show(supportFragmentManager, "DeleteDialogFragment")
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}
