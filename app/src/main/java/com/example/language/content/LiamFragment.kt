package com.example.language.content

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.language.R
import com.example.language.content.adapter.MessagesAdapter
import com.example.language.content.vm.LiamViewModel

class LiamFragment : Fragment(R.layout.fragment_liam) {

    private val viewModel by viewModels<LiamViewModel>()

    private lateinit var rvMessages: RecyclerView
    private lateinit var btnSend: Button
    private lateinit var etMessage: EditText
    private val adapter = MessagesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.onCreate()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        findViews(view)
        configureObservers()
        configureViews()
    }

    private fun configureViews() {
        rvMessages.adapter = adapter
        btnSend.setOnClickListener {
            viewModel.sendMessage(etMessage.text.toString())
            etMessage.text.clear()
        }

    }

    private fun configureObservers() {
        viewModel.chatMessage.observe(viewLifecycleOwner) {
            adapter.appendItems(it)
        }
    }

    private fun findViews(root: View) {
        rvMessages = root.findViewById(R.id.rv_messages)
        btnSend = root.findViewById(R.id.btn_send)
        etMessage = root.findViewById(R.id.et_message)
    }
}
