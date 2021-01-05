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
import androidx.appcompat.app.AppCompatActivity
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

        keyboard_more_button.setOnClickListener {
            bottom_app_bar_write_new.visibility = View.VISIBLE
        }

        more_button.setOnClickListener {
            bottom_app_bar_write_new.visibility = View.INVISIBLE
        }

        copy_button.setOnClickListener {
            var clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            var clip = ClipData.newPlainText("label", edit_text_write_new.text)
            clipboard.setPrimaryClip(clip)
            Snackbar.make(
                coordinator_layout_snackbar,
                R.string.copy_snackbar_text,
                Snackbar.LENGTH_SHORT
            ).show()
        }

        var centerAlignment = false
        text_align_button.setOnClickListener {
            if (!centerAlignment) {
                text_align_button.setImageResource(R.drawable.text_align_left_button)
                centerAlignment = true
                edit_text_write_new.gravity = Gravity.CENTER_HORIZONTAL
            } else {
                text_align_button.setImageResource(R.drawable.text_align_center_button)
                centerAlignment = false
                edit_text_write_new.gravity = Gravity.LEFT
            }
        }

        edit_text_write_new.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val length = edit_text_write_new.length()
                val text = "$length 자"
                number_of_characters_text.setText(text)
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

}
