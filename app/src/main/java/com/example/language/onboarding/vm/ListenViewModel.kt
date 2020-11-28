package com.example.language.onboarding.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.example.language.onboarding.fragments.TellMeMoreFragmentDirections
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.util.concurrent.TimeUnit

class ListenViewModel : ViewModel() {

    private lateinit var tellMeMoreFile: File

    private val _navigationAction = MutableLiveData<NavDirections>()
    val navigationAction: LiveData<NavDirections> = _navigationAction

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val disposables = CompositeDisposable()


    fun onCreate(tellMeMoreFile: File) {
        this.tellMeMoreFile = tellMeMoreFile
    }

    fun sendTellMeMore() {
        if (!tellMeMoreFile.exists()) {
            _message.value = "Error!"
            return
        }

        _loading.value = true

        disposables += Single.timer(2, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnEvent { _, _ -> _loading.value = false }
            .subscribe { value, exception ->
                if (value != null) {
                    _navigationAction.value =
                        TellMeMoreFragmentDirections.actionTellMeMoreFragmentToListenFragment(
                            fileAbsolutePath = tellMeMoreFile.absolutePath
                        )
                } else {
                    _message.value = "error"
                }
            }
    }
}
