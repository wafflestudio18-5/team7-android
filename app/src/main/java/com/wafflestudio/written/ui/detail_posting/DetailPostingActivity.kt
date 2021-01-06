package com.wafflestudio.written.ui.detail_posting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.wafflestudio.written.databinding.ActivityDetailPostingBinding
import com.wafflestudio.written.databinding.ActivityMainBinding
import com.wafflestudio.written.ui.main.MainViewModel

class DetailPostingActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityDetailPostingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPostingBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}