package com.example.language.content

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.language.R
import kotlin.random.Random

class RetellingResultFragment : Fragment(R.layout.fragment_retelling_result) {

    private val navController by lazy { findNavController() }

    private lateinit var tvScore: TextView
    private lateinit var btnBack: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvScore = view.findViewById(R.id.tv_score)
        btnBack = view.findViewById(R.id.btn_go_back)

        tvScore.text = "${Random.nextInt(60, 100)}/100"
        btnBack.setOnClickListener {
            navController.navigate(
                RetellingResultFragmentDirections.actionRetellingResultFragmentToTasks()
            )
        }
    }
}
