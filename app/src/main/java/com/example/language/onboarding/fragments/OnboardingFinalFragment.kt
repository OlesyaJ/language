package com.example.language.onboarding.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.language.R

class OnboardingFinalFragment : Fragment(R.layout.fragment_onboarding_final) {

    private val navController by lazy { findNavController() }

    private lateinit var btnDone: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        findViews(view)
        configureListeners()
    }

    private fun configureListeners() {
        btnDone.setOnClickListener {
            navController.navigate(
                OnboardingFinalFragmentDirections.actionOnboardingFinalFragmentToChooseTopicsFragment()
            )
        }
    }

    private fun findViews(root: View) {
        btnDone = root.findViewById(R.id.btn_done)
    }
}


