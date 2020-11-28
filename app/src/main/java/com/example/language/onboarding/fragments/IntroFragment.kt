package com.example.language.onboarding.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.language.R

class IntroFragment : Fragment(R.layout.fragment_intro) {

    private val navController by lazy { findNavController() }

    private lateinit var btnStartTest: Button
    private lateinit var btnIKnowMyLevel: Button


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnStartTest = view.findViewById(R.id.btn_start_test)
        btnIKnowMyLevel = view.findViewById(R.id.btn_i_know_my_level)

        btnStartTest.setOnClickListener {
            navController.navigate(
                IntroFragmentDirections.actionIntroFragmentToTellMeMoreFragment()
            )
        }

        btnIKnowMyLevel.setOnClickListener {
            navController.navigate(
                IntroFragmentDirections.actionIntroFragmentToIKnowMyLevelFragment()
            )
        }
    }
}


