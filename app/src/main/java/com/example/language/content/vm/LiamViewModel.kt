package com.example.language.content.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.language.content.data.TextMessage
import com.example.language.content.data.TextMessageResponse
import com.example.language.content.network.ApiImpl
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers

class LiamViewModel : ViewModel() {

    private val api = ApiImpl.api

    private val _chatMessage = MutableLiveData<TextMessage>()
    val chatMessage: LiveData<TextMessage> = _chatMessage

    private val disposables = CompositeDisposable()

    fun onCreate() {
        disposables += api.getStartPhrase()
            .map { TextMessage(false, it.text) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { message, exception ->
                if (message != null) {
                    _chatMessage.value = message
                } else {
                    exception.printStackTrace()
                }
            }
    }

    fun sendMessage(text: String) {
        _chatMessage.value = TextMessage(true, text)

        disposables += api.getAnswer(TextMessageResponse(text))
            .map {
                val string = it.body()?.string() ?: throw RuntimeException()
                val response = Gson().fromJson(string, TextMessageResponse::class.java)
                return@map if (response.text.isEmpty()) response.copy("Sorry, what?")
                else response
            }
            .map { TextMessage(false, it.text) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { message, exception ->
                if (message != null) {
                    _chatMessage.value = message
                } else {
                    exception.printStackTrace()
                }
            }
    }
}
