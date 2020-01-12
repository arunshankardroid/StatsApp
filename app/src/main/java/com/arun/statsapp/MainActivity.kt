package com.arun.statsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.arun.statsapp.ui.main.MainFragment
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        configureToolbar()
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }

    private fun configureToolbar() {
        toolbar?.apply {
            setSupportActionBar(this)
        }
    }

}
