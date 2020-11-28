package com.example.language.onboarding

import android.Manifest
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.language.R
import com.example.language.onboarding.fragments.TellMeMoreFragment

class OnboardingAcivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
        ActivityCompat.requestPermissions(
            this, arrayOf(
                Manifest.permission.RECORD_AUDIO
            ), 123
        )
    }

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        supportFragmentManager
//            .beginTransaction()
//            .add(android.R.id.content, TellMeMoreFragment())
//            .commit()
//    }
}
