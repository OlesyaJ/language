package com.example.language.onboarding.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.language.R

class IKnowMyLevelFragment : Fragment(R.layout.fragment_i_know_my_level) {

    private val navController by lazy { findNavController() }

    private lateinit var btnBeginner: Button
    private lateinit var btnIntermediate: Button
    private lateinit var btnAdvanced: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        findViews(view)
        configureListeners()
    }

    private fun configureListeners() {
        btnBeginner.setOnClickListener {
            navController.navigate(
                IKnowMyLevelFragmentDirections.actionIKnowMyLevelFragmentToChooseTopicsFragment()
            )
        }
        btnIntermediate.setOnClickListener {
            navController.navigate(
                IKnowMyLevelFragmentDirections.actionIKnowMyLevelFragmentToChooseTopicsFragment()
            )
        }
        btnAdvanced.setOnClickListener {
            navController.navigate(
                IKnowMyLevelFragmentDirections.actionIKnowMyLevelFragmentToChooseTopicsFragment()
            )
        }
    }

    private fun findViews(root: View) {
        btnBeginner = root.findViewById(R.id.btn_beginner)
        btnIntermediate = root.findViewById(R.id.btn_intermediate)
        btnAdvanced = root.findViewById(R.id.btn_advanced)
    }
}


