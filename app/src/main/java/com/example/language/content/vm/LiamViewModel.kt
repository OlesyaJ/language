package com.example.language.content.vm

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.yandex.speechkit.*


class LiamViewModel : ViewModel(), RecognizerListener {

    private var recogniser: OnlineRecognizer? = null

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    @SuppressLint("MissingPermission")
    fun onStartRecordingClicked() {
        try {
            val rec =
                OnlineRecognizer.Builder(Language.ENGLISH, OnlineModel.QUERIES, this)
                    .setDisableAntimat(false)
                    .setEnablePunctuation(true)
                    .build()
            rec.prepare()
            rec.startRecording()

            recogniser = rec
            _message.value = "It works!"
        } catch (ex: Exception) {
            _message.value = "Error, ex = ${ex.message}"
        }
    }

    @SuppressLint("MissingPermission")
    fun onNewFileReady() {
        val recognizer: OnlineRecognizer =
            OnlineRecognizer.Builder(Language.ENGLISH, OnlineModel.QUERIES, this)
                .setDisableAntimat(false)
                .setEnablePunctuation(true)
                .build()
        recognizer.prepare()
        recognizer.startRecording()
    }

    override fun onRecordingBegin(recognizer: Recognizer) {
        print("")
    }

    override fun onSpeechDetected(recognizer: Recognizer) {
        print("")
    }

    override fun onSpeechEnds(recognizer: Recognizer) {
        print("")
    }

    override fun onRecordingDone(recognizer: Recognizer) {
        print("")
    }

    override fun onPowerUpdated(recognizer: Recognizer, p1: Float) {
        print("")
    }

    override fun onPartialResults(recognizer: Recognizer, recognition: Recognition, p2: Boolean) {
        print("")
    }

    override fun onRecognitionDone(p0: Recognizer) {
        print("")
    }

    override fun onRecognizerError(p0: Recognizer, p1: Error) {
        print("")
    }

    override fun onMusicResults(p0: Recognizer, p1: Track) {
        print("")
    }

    fun onStopRecordingClicked() {
        recogniser?.stopRecording()
    }
}
