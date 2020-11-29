package com.example.language.content.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import java.io.File

class RetellingViewModel : ViewModel() {

    private val _currentFileData = MutableLiveData<File>()
    val currentFileData: LiveData<File> = _currentFileData

    private val _navigation = MutableLiveData<NavDirections>()
    val navigation: LiveData<NavDirections> = _navigation

    private lateinit var cachePath: String

    fun onCreate(cachePath: String) {
        this.cachePath = cachePath
    }

    fun onStartRecordingClicked() {
        _currentFileData.value = File(cachePath, "retelling.mp3")
    }
}
