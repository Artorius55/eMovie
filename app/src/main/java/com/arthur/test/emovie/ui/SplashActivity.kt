package com.arthur.test.emovie.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.arthur.test.emovie.R
import com.arthur.test.emovie.ui.viewmodels.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : BaseActivity() {

    private val viewmodel by viewModels<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        collectFromViewModel()
    }

    /**
     * Method for loading primary data from server.
     *
     * @author: Arturo Segura
     * @since: 1.0
     */
    private fun collectFromViewModel() = lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            delay(1500)
            viewmodel.dataLoaded.collect(::collectDataLoaded)
        }
    }

    /**
     * Fun to collect
     *
     * @author: Arturo Segura
     * @since: 1.0
     */
    private fun collectDataLoaded(isLoaded: Boolean) {
        if (isLoaded) {
            navigateToMain()
        }
    }

    /**
     * Fun to navigates to next screen
     *
     * @author: Arturo Segura
     * @since: 1.0
     */
    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

}