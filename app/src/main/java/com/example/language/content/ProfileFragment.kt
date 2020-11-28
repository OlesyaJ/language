package com.example.language.content

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.fragment.app.Fragment
import com.example.language.R

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var btnSignIn: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnSignIn = view.findViewById(R.id.btn_sign_in)
        btnSignIn.setOnClickListener {
            Toast.makeText(requireContext(), "Soon", LENGTH_SHORT).show()
        }
    }
}
