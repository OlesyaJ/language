package com.example.language.onboarding.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.example.language.onboarding.data.TextPosition
import com.example.language.onboarding.data.TextToRead
import java.io.File

class ReadAloudViewModel : ViewModel() {

    private val files = mutableListOf<File>()

    private lateinit var cachePath: String

    private val textsToRead = mutableListOf<TextToRead>(
        TextToRead(1, "Read phrase", "Better to arrive late than not to come at all."),
        TextToRead(2, "Read phrase", "Doing something poorly in order to save time or money"),
        TextToRead(3, "Read phrase", "Thanks so much for cooking dinner. I really appreciate it."),
        TextToRead(4, "Read phrase", "Excuse me sir, you dropped your wallet."),
        TextToRead(
            5,
            "Read phrase",
            "I’m not sure if we should paint the room yellow or blue. What do you think?"
        )
    )

    private val _currentTextPosition = MutableLiveData<TextPosition>()
    val currentTextPosition: LiveData<TextPosition> = _currentTextPosition

    private val _currentText = MutableLiveData<TextToRead>()
    val currentText: LiveData<TextToRead> = _currentText

    private val _currentFileData = MutableLiveData<File>()
    val currentFileData: LiveData<File> = _currentFileData

    private val _navigation = MutableLiveData<NavDirections>()
    val navigation: LiveData<NavDirections> = _navigation

    fun onCreate(cachePath: String) {
        this.cachePath = cachePath

        _currentTextPosition.value = TextPosition(1, textsToRead.size)
        _currentText.value = textsToRead[0]
    }

    fun onStartRecordingClicked() {
        _currentFileData.value = File(cachePath, "read_aloud_${files.size}.mp3")
    }

    fun onNewFileReady() {
        val file = _currentFileData.value ?: return
        files.add(file)

        if (files.size == textsToRead.size) {
            _navigation.value =
                ReadAloudFragmentDirections.actionReadAloudFragmentToOnboardingFinalFragment()
        } else {
            val previous = _currentTextPosition.value?.count ?: return
            val new = previous + 1
            _currentTextPosition.value = TextPosition(new, textsToRead.size)
            _currentText.value = textsToRead[new - 1]
        }
    }
}

