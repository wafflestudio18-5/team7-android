package com.wafflestudio.written.ui.main.write

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.google.android.material.snackbar.Snackbar
import com.wafflestudio.written.R
import com.wafflestudio.written.databinding.ActivityWriteNewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_write_new.*
import kotlinx.android.synthetic.main.content_write_new.*
import timber.log.Timber

@AndroidEntryPoint
class WriteNewActivity : AppCompatActivity(), CloseDialogFragment.CloseDialogListener {

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, WriteNewActivity::class.java)
        }
    }

    private lateinit var binding: ActivityWriteNewBinding
    private val viewModel: WriteNewViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWriteNewBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                centerAlignment = true
                content_edit_text.gravity = Gravity.CENTER_HORIZONTAL
            } else {
                text_align_button.setImageResource(R.drawable.text_align_center_button)
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
            startActivity(WriteCompletedActivity.createIntent(this))
            finish()
        }

        viewModel.observeExpand().subscribe { expand ->
            Timber.d("expand: $expand")
            binding.closeText.visibility = if (!expand) View.VISIBLE else View.GONE
            binding.completedText.visibility = if (!expand) View.VISIBLE else View.GONE
            binding.titleText.visibility = if (!expand) View.VISIBLE else View.GONE
            binding.editTitleText.visibility = if (expand) View.VISIBLE else View.GONE
            binding.dummyBackground.visibility = if (expand) View.VISIBLE else View.GONE
            content_edit_text.isEnabled = !expand
            binding.keyboardMoreButton.visibility =
                if (!expand && !appBarVisible) View.VISIBLE else View.GONE
            binding.bottomAppBar.visibility =
                if (!expand && appBarVisible) View.VISIBLE else View.GONE
        }

        binding.titleText.setOnClickListener {
//            val transition = ChangeBounds()
//            transition.duration = 1000L
//            TransitionManager.beginDelayedTransition(top_transition_container, transition)
            viewModel.expand()
        }

        binding.dummyBackground.setOnClickListener {
            viewModel.close()
        }

    }

    fun showCloseDialog() {
        val dialog = CloseDialogFragment()
        dialog.show(supportFragmentManager, "CloseDialogFragment")
    }

    override fun onDialogLeaveClick(dialog: DialogFragment) {
        finish()
    }

    override fun onDialogContinueClick(dialog: DialogFragment) {
        dialog.dismiss()
    }

}
