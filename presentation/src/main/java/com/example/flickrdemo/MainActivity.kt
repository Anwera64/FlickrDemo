package com.example.flickrdemo

import android.graphics.Color
import android.os.Bundle
import android.view.KeyEvent
import android.view.MotionEvent
import android.widget.Toast
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
    var onKeyPressedAction: ((keyCode: Int) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.requestLatestPhotos()
        // Setup the back behaviour to cancel any search queries if possible and avoid closing the app
        onBackPressedDispatcher.addCallback {
            val searchReset = viewModel.clearSearch()
            if (searchReset) return@addCallback
            // If there are no search queries, close the activity as intended
            finish()
        }
        val backgroundManager = BackgroundManager.getInstance(this)
        backgroundManager.attach(window)
        backgroundManager?.color = Color.BLACK
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        onKeyPressedAction?.invoke(keyCode)
        return super.onKeyDown(keyCode, event)
    }

    fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frame, fragment)
            .commit()
    }
}