package com.example.language.ui.fragments

import android.animation.Animator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.media.MediaRecorder
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.language.R
import com.visualizer.amplitude.AudioRecordView
import java.io.File
import java.util.*
import kotlin.math.roundToInt


class TellMeMoreFragment : Fragment(R.layout.fragment_tell_me_more) {

    private val viewModel by viewModels<TellMeMoreViewModel>()
    private val tellMeMoreFile by lazy {
        File(
            requireContext().cacheDir.absolutePath,
            "tell_me_more.mp3"
        )
    }

    private var timer: Timer? = null

    private lateinit var btnPushToSpeak: View
    private lateinit var vInnerCircle: ImageView
    private lateinit var vOuterCircle: ImageView
    private lateinit var recorderView: AudioRecordView

    private var smallSize: Int = 0
    private var largeSize: Int = 0

    private var pttAnimation: Animator? = null
    private lateinit var mediaRecorder: MediaRecorder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.onCreate(tellMeMoreFile)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        findViews(view)

        configureListeners()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun configureListeners() {
        btnPushToSpeak.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    animateExp()
                    startRecording()
                    true
                }
                MotionEvent.ACTION_UP -> {
                    animateCol()
                    stopRecording()
                    true
                }
                else -> false
            }
        }
    }

    private fun findViews(root: View) {
        btnPushToSpeak = root.findViewById(R.id.btn_push_to_speak)
        vInnerCircle = root.findViewById(R.id.iv_round_inner)
        vOuterCircle = root.findViewById(R.id.iv_round_outer)
        recorderView = root.findViewById(R.id.adv_recorder)
    }

    private fun calculateSizes() {
        if (smallSize == 0 || largeSize == 0) {
            smallSize = vInnerCircle.measuredHeight
            largeSize = (vOuterCircle.measuredHeight * 0.8).roundToInt()
        }
    }

    private fun animateExp() {
        calculateSizes()

        pttAnimation?.cancel()

        val anim = ValueAnimator.ofInt(vInnerCircle.height, largeSize)

        anim.addUpdateListener { valueAnimator ->
            val animatedValue = valueAnimator.animatedValue as Int
            val layoutParams: ViewGroup.LayoutParams = vInnerCircle.layoutParams
            layoutParams.height = animatedValue
            layoutParams.width = animatedValue

            vInnerCircle.layoutParams = layoutParams
        }
        anim.duration = 300
        anim.start()

        pttAnimation = anim
    }

    private fun animateCol() {
        calculateSizes()

        pttAnimation?.cancel()

        val anim = ValueAnimator.ofInt(vInnerCircle.height, smallSize)

        anim.addUpdateListener { valueAnimator ->
            val animatedValue = valueAnimator.animatedValue as Int
            val layoutParams: ViewGroup.LayoutParams = vInnerCircle.layoutParams
            layoutParams.height = animatedValue
            layoutParams.width = animatedValue
            vInnerCircle.layoutParams = layoutParams
        }
        anim.duration = 300
        anim.start()

        pttAnimation = anim
    }

    private fun initMediaRecorder() {
        mediaRecorder = MediaRecorder()
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
        mediaRecorder.setOutputFile(tellMeMoreFile.absolutePath)
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
        mediaRecorder.prepare()
    }

    private fun startRecording() {
        initMediaRecorder()
        startTimer()
        mediaRecorder.start()
    }

    private fun startTimer() {
        timer?.cancel()
        timer = Timer()
        timer?.schedule(object : TimerTask() {
            override fun run() {
                val currentMaxAmplitude = mediaRecorder.maxAmplitude
                recorderView.update(currentMaxAmplitude)
            }
        }, 0, 30)
    }

    private fun stopRecording() {
        mediaRecorder.stop()
        endTimer()
        viewModel.sendTellMeMore()
    }

    private fun endTimer() {
        timer?.cancel()
    }
}


