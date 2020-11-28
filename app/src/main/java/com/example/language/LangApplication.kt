package com.example.language

import android.app.Application
import ru.yandex.speechkit.SpeechKit
import java.util.*

class LangApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        try {
            SpeechKit.getInstance().init(this, "29665837-882b-46c5-83f7-1b981f0d1978")
            SpeechKit.getInstance().uuid = UUID.randomUUID().toString()
        } catch (throwable: Throwable) {
            // Official samples do the same ¯\_(ツ)_/¯
        }
    }
}
