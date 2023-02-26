package com.example.flickrdemo

import android.graphics.Color
import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentContainerView
import androidx.leanback.app.BackgroundManager
import com.example.flickrdemo.search.SearchFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    companion object {
        private const val TAG = "Main Activity"
    }

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.requestLatestPhotos()
        onBackPressedDispatcher.addCallback {
            val searchReset = viewModel.clearSearch()
            if (searchReset) return@addCallback
            finish()
        }
        val backgroundManager = BackgroundManager.getInstance(this)
        backgroundManager.attach(window)
        backgroundManager?.color = Color.BLACK
    }

    fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frame, fragment)
            .commit()
    }
}