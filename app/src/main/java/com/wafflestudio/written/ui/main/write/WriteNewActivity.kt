package com.wafflestudio.written.ui.main.write

import android.R.attr.visible
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.transition.TransitionSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.wafflestudio.written.R
import com.wafflestudio.written.databinding.ActivityWriteNewBinding
import kotlinx.android.synthetic.main.activity_write_new.*
import kotlinx.android.synthetic.main.content_write_new.*


class WriteNewActivity : AppCompatActivity() {

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, WriteNewActivity::class.java)
        }
    }

    private lateinit var binding: ActivityWriteNewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWriteNewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.keyboardMoreButton.setOnClickListener {
            binding.bottomAppBar.visibility = View.VISIBLE
        }

        binding.moreButton.setOnClickListener {
            binding.bottomAppBar.visibility = View.INVISIBLE
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

        content_edit_text.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val length = content_edit_text.length()
                val text = "$length 자"
                binding.numberOfCharactersText.setText(text)
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        close_text.setOnClickListener {
            TODO()
        }

        var visible = true
        binding.titleText.setOnClickListener {
            visible = true
            TransitionManager.beginDelayedTransition(top_transition_container)
            binding.closeText.visibility = if (visible) View.VISIBLE else View.GONE
            binding.completedText.visibility = if (visible) View.VISIBLE else View.GONE
            binding.titleText.visibility = if (visible) View.VISIBLE else View.GONE
            binding.editTitleText.visibility = if (!visible) View.VISIBLE else View.GONE
            binding.dummyBackground.visibility = if(!visible) View.VISIBLE else View.GONE
            content_edit_text.isEnabled = visible
        }

        binding.dummyBackground.setOnClickListener {
            visible = false
        }

    }

}
