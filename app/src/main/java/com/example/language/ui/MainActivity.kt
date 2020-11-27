package com.example.language.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.language.R
import com.example.language.ui.fragments.IntroFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
                .beginTransaction()
                .add(android.R.id.content, IntroFragment())
                .commit()
    }
}
