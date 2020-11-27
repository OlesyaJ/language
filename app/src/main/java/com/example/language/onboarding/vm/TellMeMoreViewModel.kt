package com.example.language.ui.fragments

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
