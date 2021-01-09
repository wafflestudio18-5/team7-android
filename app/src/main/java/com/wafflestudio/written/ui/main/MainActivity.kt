package com.wafflestudio.written.ui.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.wafflestudio.written.R
import com.wafflestudio.written.databinding.ActivityMainBinding
import com.wafflestudio.written.ui.main.my.MyFragment
import com.wafflestudio.written.ui.main.saved.SavedFragment
import com.wafflestudio.written.ui.main.subscribe.SubscribeFragment
import com.wafflestudio.written.ui.main.title.TitleFragment
import com.wafflestudio.written.ui.main.write.WriteFragment
import com.wafflestudio.written.ui.settings.SettingsActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object {
        fun createIntent(context : Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<WriteFragment>(R.id.fragment_container_view_main)
            }
        }

        menu_button.setOnClickListener {
            if(!drawer_layout_main.isDrawerOpen(GravityCompat.START)) {
                drawer_layout_main.openDrawer(GravityCompat.START, false)
            }
        }

        binding.drawerWrite.setOnClickListener {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<WriteFragment>(R.id.fragment_container_view_main)
                drawer_layout_main.closeDrawer(GravityCompat.START, false)
            }
        }

        binding.drawerTitle.setOnClickListener {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<TitleFragment>(R.id.fragment_container_view_main)
                drawer_layout_main.closeDrawer(GravityCompat.START, false)
            }
        }

        binding.drawerMy.setOnClickListener {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<MyFragment>(R.id.fragment_container_view_main)
                drawer_layout_main.closeDrawer(GravityCompat.START, false)
            }
        }

        binding.drawerSubscribe.setOnClickListener {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<SubscribeFragment>(R.id.fragment_container_view_main)
                drawer_layout_main.closeDrawer(GravityCompat.START, false)
            }
        }

        binding.drawerSaved.setOnClickListener {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<SavedFragment>(R.id.fragment_container_view_main)
                drawer_layout_main.closeDrawer(GravityCompat.START, false)
            }
        }

        binding.settingsButton.setOnClickListener {
            startActivity(SettingsActivity.createIntent(this))
        }


    }

}

