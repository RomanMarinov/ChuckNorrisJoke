package com.dev_marinov.chucknorrisjoke.presentation.exitdialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.dev_marinov.chucknorrisjoke.databinding.FragmentExitDialogBinding

class ExitDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentExitDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExitDialogBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.btNo.setOnClickListener { dismiss() }
        binding.btYes.setOnClickListener {
            requireActivity().finishAndRemoveTask() }
    }
}