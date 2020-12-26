package com.wafflestudio.written.ui.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object {
        fun createIntent(context : Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
    private val viewModel: MainViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<WriteFragment>(R.id.fragment_container_view)
            }
        }

        menu_button.setOnClickListener {
            if(!drawer_layout.isDrawerOpen(GravityCompat.START)) {
                drawer_layout.openDrawer(GravityCompat.START, false)
            }
        }

        drawer_write.setOnClickListener {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<WriteFragment>(R.id.fragment_container_view)
                addToBackStack("name")
                drawer_layout.closeDrawer(GravityCompat.START, false)
            }
        }

        drawer_title.setOnClickListener {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<TitleFragment>(R.id.fragment_container_view)
                addToBackStack("name")
                drawer_layout.closeDrawer(GravityCompat.START, false)
            }
        }

        drawer_my.setOnClickListener {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<MyFragment>(R.id.fragment_container_view)
                addToBackStack("name")
                drawer_layout.closeDrawer(GravityCompat.START, false)
            }
        }

        drawer_subscribe.setOnClickListener {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<SubscribeFragment>(R.id.fragment_container_view)
                addToBackStack("name")
                drawer_layout.closeDrawer(GravityCompat.START, false)
            }
        }

        drawer_saved.setOnClickListener {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<SavedFragment>(R.id.fragment_container_view)
                addToBackStack("name")
                drawer_layout.closeDrawer(GravityCompat.START, false)
            }
        }


    }

}
