package com.wafflestudio.written.ui.main.write

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.google.android.material.snackbar.Snackbar
import com.wafflestudio.written.R
import com.wafflestudio.written.databinding.ActivityWriteNewBinding
import com.wafflestudio.written.models.PostingDto
import com.wafflestudio.written.ui.main.my.detail_posting.MyDetailPostingActivity
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_write_new.*
import kotlinx.android.synthetic.main.content_write_new.*
import timber.log.Timber

@AndroidEntryPoint
class WriteNewActivity : AppCompatActivity() {

    companion object {
        private const val TITLE_KEY = "title"
        private const val POSTING_KEY = "posting"
        fun createIntent(context: Context, title: String, posting: PostingDto?): Intent {
            Timber.d("title $title")
            return Intent(context, WriteNewActivity::class.java).apply {
                putExtra(TITLE_KEY, title)
                putExtra(POSTING_KEY, posting)
            }
        }
    }

    private lateinit var binding: ActivityWriteNewBinding
    private val viewModel: WriteNewViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWriteNewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.getStringExtra(TITLE_KEY)?.let {
            Timber.d("intent title $it")
            binding.titleText.text = it
            binding.editTitleText.setText(it)
        }

        var posting: PostingDto? = null
        intent.getParcelableExtra<PostingDto>(POSTING_KEY)?.let {
            posting = it
            content_edit_text.setText(it.content)
            Timber.d(it.alignment)
            if (it.alignment.equals("CENTER")) {
                Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show()
                text_align_button.setImageResource(R.drawable.text_align_center_button)
                content_edit_text.gravity = Gravity.LEFT
            }
        }

        var appBarVisible = false
        binding.keyboardMoreButton.setOnClickListener {
            binding.bottomAppBar.visibility = View.VISIBLE
            binding.keyboardMoreButton.visibility = View.GONE
            appBarVisible = true
        }

        binding.moreButton.setOnClickListener {
            binding.bottomAppBar.visibility = View.INVISIBLE
            binding.keyboardMoreButton.visibility = View.VISIBLE
            appBarVisible = false
        }

        binding.copyButton.setOnClickListener {
            var clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            var clip = ClipData.newPlainText("label", content_edit_text.text)
            clipboard.setPrimaryClip(clip)
            Snackbar.make(
                binding.coordinatorLayoutSnackbar,
                R.string.copy_snackbar_text,
                Snackbar.LENGTH_SHORT
            ).show()
        }

        var centerAlignment = false
        binding.textAlignButton.setOnClickListener {
            if (!centerAlignment) {
                text_align_button.setImageResource(R.drawable.text_align_left_button)
                text_align_button.alpha = 0.5F
                centerAlignment = true
                content_edit_text.gravity = Gravity.CENTER_HORIZONTAL
            } else {
                text_align_button.setImageResource(R.drawable.text_align_center_button)
                text_align_button.alpha = 0.5F
                centerAlignment = false
                content_edit_text.gravity = Gravity.LEFT
            }
        }

        binding.spellCheckButton.setOnClickListener {
            TODO()
        }

        content_edit_text.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val length = content_edit_text.length()
                val text = "$length 자"
                binding.numberOfCharactersText.text = text
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.closeText.setOnClickListener {
            showCloseDialog()
        }

        binding.completedText.setOnClickListener {
            if (TextUtils.isEmpty(content_edit_text.text.toString())) {
                Snackbar.make(
                    binding.coordinatorLayoutSnackbar,
                    R.string.incomplete_snackbar_text,
                    Snackbar.LENGTH_SHORT
                ).show()
            } else {
                posting?.let {
                    val postingId = it.id.toLong()
                    val content = content_edit_text.text.toString()
                    val alignment = if (centerAlignment) "CENTER" else "LEFT"
                    val isPublic = it.isPublic
                    viewModel.updatePosting(postingId, content, alignment, isPublic)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            viewModel.observePosting()
                                .subscribe {
                                    val intent = MyDetailPostingActivity.createIntent(this)
                                    intent.putExtra("posting", it)
                                    Timber.d(it.content)
                                    startActivity(MyDetailPostingActivity.createIntent(this))
                                    finish()
                                }
                        }, {
                            Toast.makeText(this, "Failed to edit post", Toast.LENGTH_SHORT).show()
                        })
                } ?: run {
                    val title = binding.titleText.text.toString()
                    val content = content_edit_text.text.toString()
                    val alignment = if (centerAlignment) "CENTER" else "LEFT"
                    viewModel.writePosting(title, content, alignment)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            val intent = MyDetailPostingActivity.createIntent(this)
                            intent.putExtra("posting", it)
                            startActivity(MyDetailPostingActivity.createIntent(this))
                            finish()
                        }, {
                            Timber.d(it)
                            Toast.makeText(this, "Failed to make post", Toast.LENGTH_SHORT).show()
                        })
                }
            }
        }

        viewModel.observeExpand().subscribe { expand ->
            Timber.d("expand: $expand")
            binding.closeText.visibility = if (!expand) View.VISIBLE else View.GONE
            binding.completedText.visibility = if (!expand) View.VISIBLE else View.GONE
            binding.titleText.visibility = if (!expand) View.VISIBLE else View.GONE
            binding.editTitleText.visibility = if (expand) View.VISIBLE else View.GONE
            binding.titleText.text = binding.editTitleText.text.toString()
            binding.dummyBackground.visibility = if (expand) View.VISIBLE else View.GONE
            content_edit_text.isEnabled = !expand
            binding.keyboardMoreButton.visibility =
                if (!expand && !appBarVisible) View.VISIBLE else View.GONE
            binding.bottomAppBar.visibility =
                if (!expand && appBarVisible) View.VISIBLE else View.GONE
        }

        binding.titleText.setOnClickListener {
            val transition = ChangeBounds()
            transition.duration = 1000L
            TransitionManager.beginDelayedTransition(top_transition_container, transition)
            viewModel.expand()
        }

        binding.dummyBackground.setOnClickListener {
            viewModel.createTitle(binding.editTitleText.text.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Toast.makeText(this, "!!!", Toast.LENGTH_SHORT).show()
                }, {
                    Timber.d(it)
                    Toast.makeText(this, "Failed to create title", Toast.LENGTH_SHORT).show()
                })
            viewModel.close()
        }

    }

    fun showCloseDialog() {
        val dialog = CloseDialogFragment()
        dialog.show(supportFragmentManager, "CloseDialogFragment")
    }

}
