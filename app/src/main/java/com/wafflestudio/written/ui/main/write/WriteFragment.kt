package com.wafflestudio.written.ui.main.write

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.wafflestudio.written.databinding.FragmentWriteBinding
import com.wafflestudio.written.databinding.FragmentWriteHomeBinding
import com.wafflestudio.written.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_write.*
import kotlinx.android.synthetic.main.fragment_write_home.*

class WriteFragment : Fragment() {

    companion object {
        const val TAG = "WRITE"
    }

    private lateinit var binding: FragmentWriteBinding
    private lateinit var viewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewPager = write_view_pager

        val pagerAdapter = WritePagerAdapter(requireActivity())
        viewPager.adapter = pagerAdapter
    }

}