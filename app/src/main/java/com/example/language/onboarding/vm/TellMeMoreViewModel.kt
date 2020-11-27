package com.example.language.onboarding.vm

import androidx.lifecycle.ViewModel
import java.io.File

class TellMeMoreViewModel : ViewModel() {

    private lateinit var tellMeMoreFile: File

    fun onCreate(tellMeMoreFile: File) {
        this.tellMeMoreFile = tellMeMoreFile
    }

    fun sendTellMeMore() {
        tellMeMoreFile.exists()
        print("something")
    }
}
