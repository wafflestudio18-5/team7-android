package com.wafflestudio.written.ui.main.write

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.wafflestudio.written.databinding.FragmentWriteBinding
import com.wafflestudio.written.databinding.FragmentWriteHomeBinding
import kotlinx.android.synthetic.main.fragment_write_home.*

class WriteHomeFragment : Fragment() {

    companion object {
        fun newInstance() : WriteHomeFragment = WriteHomeFragment()
    }

    private lateinit var binding: FragmentWriteHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWriteHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        write_button.setOnClickListener {
            val intent = WriteNewActivity.createIntent(requireActivity())
            activity?.startActivity(intent)
        }

    }

}
