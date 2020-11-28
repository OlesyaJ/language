package com.example.language.onboarding.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.language.R
import com.example.language.onboarding.adapter.GridSpacingItemDecoration
import com.example.language.onboarding.adapter.TopicAdapter
import com.example.language.onboarding.vm.ChooseTopicsViewModel

class ChooseTopicsFragment : Fragment(R.layout.fragment_choose_topics) {

    private val viewModel by viewModels<ChooseTopicsViewModel>()

    private lateinit var clHeader: ConstraintLayout
    private lateinit var nscContent: NestedScrollView
    private lateinit var rvTopics: RecyclerView
    private lateinit var btnProceed: Button

    private val topicAdapter = TopicAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findViews(view)

        configureViews()
        configureListeners()
        configureObservers()
    }

    private fun configureListeners() {
        nscContent.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, _, _, _ ->
            clHeader.isSelected = v?.canScrollVertically(-1) ?: false
        })
    }

    private fun configureObservers() {
        viewModel.topics.observe(viewLifecycleOwner) {
            topicAdapter.setItems(it)
        }
    }

    private fun configureViews() {
        rvTopics.adapter = topicAdapter
        rvTopics.addItemDecoration(GridSpacingItemDecoration(2, 14, true))
    }

    private fun findViews(root: View) {
        rvTopics = root.findViewById(R.id.rv_topics)
        nscContent = root.findViewById(R.id.nsv_content)
        btnProceed = root.findViewById(R.id.btn_proceed)
        clHeader = root.findViewById(R.id.v_top_background)
    }
}
