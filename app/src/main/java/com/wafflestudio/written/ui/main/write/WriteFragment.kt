package com.wafflestudio.written.ui.main.write

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.wafflestudio.written.databinding.FragmentWriteBinding

class WriteFragment : Fragment() {

    companion object {
        const val TAG = "WRITE"
    }

    private lateinit var binding: FragmentWriteBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWriteBinding.inflate(inflater, container, false)
        return binding.root
    }

}