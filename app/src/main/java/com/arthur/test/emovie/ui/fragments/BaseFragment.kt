package com.arthur.test.emovie.ui.fragments

import android.content.Context
import androidx.fragment.app.Fragment
import com.arthur.test.emovie.ui.BaseActivity

open class BaseFragment: Fragment() {

    // Safe context access
    lateinit var safeActivity: BaseActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        safeActivity = context as BaseActivity
    }
}