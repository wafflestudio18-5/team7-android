package com.wafflestudio.written.ui.main.write

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.wafflestudio.written.databinding.ActivityWriteCompletedBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WriteCompletedActivity: AppCompatActivity() {

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, WriteCompletedActivity::class.java)
        }
    }

    private lateinit var binding: ActivityWriteCompletedBinding
    private val viewModel: WriteCompletedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWriteCompletedBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

}
