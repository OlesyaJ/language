package com.example.language.content

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.language.R

class TasksFragment : Fragment(R.layout.fragment_tasks) {

    private val navController by lazy { findNavController() }

    private lateinit var btnTed: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        configureListeners()
    }

    private fun configureListeners() {
        btnTed.setOnClickListener {
            navController.navigate(TasksFragmentDirections.actionTasksToTedFragment())
        }
    }

    private fun initViews(root: View) {
        btnTed = root.findViewById(R.id.btn_start_ted)
    }
}
