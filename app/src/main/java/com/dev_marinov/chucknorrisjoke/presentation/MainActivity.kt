package com.dev_marinov.chucknorrisjoke.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.dev_marinov.chucknorrisjoke.R
import com.dev_marinov.chucknorrisjoke.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var mySavedInstanceState: Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mySavedInstanceState = savedInstanceState
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    override fun onBackPressed() = showExitDialog()

    private fun showExitDialog() {
        binding.navHosFragment.findNavController()
            .navigate(R.id.action_jokesFragment_to_exitDialogFragment)
    }
}