package com.example.language.onboarding.fragments

import android.animation.Animator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.media.MediaRecorder
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.example.language.R
import com.visualizer.amplitude.AudioRecordView
import java.io.File
import java.lang.Exception
import java.util.*
import kotlin.math.roundToInt


class ReadAloudFragment : Fragment(R.layout.fragment_read_aloud) {

    private val viewModel by viewModels<ReadAloudViewModel>()

    private val navController by lazy { findNavController() }
    private lateinit var currentFile: File

    private var timer: Timer? = null

    private lateinit var tvTitle: TextView
    private lateinit var tvSubtitle: TextView
    private lateinit var btnPushToSpeak: View
    private lateinit var vInnerCircle: ImageView
    private lateinit var vOuterCircle: ImageView
    private lateinit var recorderView: AudioRecordView
    private lateinit var tvTextCounter: TextView

    private var smallSize: Int = 0
    private var largeSize: Int = 0

    private var pttAnimation: Animator? = null
    private lateinit var mediaRecorder: MediaRecorder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.onCreate(requireActivity().cacheDir.absolutePath)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        findViews(view)

        configureListeners()
        configureObservers()
    }

    private fun configureObservers() {
        viewModel.currentFileData.observe(viewLifecycleOwner) {
            startRecording(it)
        }
        viewModel.currentText.observe(viewLifecycleOwner) {
            tvTitle.text = it.title
            tvSubtitle.text = it.text
        }
        viewModel.currentTextPosition.observe(viewLifecycleOwner) {
            tvTextCounter.text = "Text ${it.count} from ${it.maxCount}"
        }
        viewModel.navigation.observe(viewLifecycleOwner) {
            navController.navigate(it)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun configureListeners() {
        btnPushToSpeak.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    animateExp()
                    viewModel.onStartRecordingClicked()
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
        tvTextCounter = root.findViewById(R.id.tv_counter)
        tvTitle = root.findViewById(R.id.tv_title)
        tvSubtitle = root.findViewById(R.id.tv_subtitle)
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

    private fun initMediaRecorder(file: File) {
        mediaRecorder = MediaRecorder()
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
        mediaRecorder.setOutputFile(file.absolutePath)
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
        mediaRecorder.prepare()
    }

    private fun startRecording(file: File) {
        initMediaRecorder(file)
        startTimer()
        mediaRecorder.start()
    }

    private fun startTimer() {
        timer?.cancel()
        timer = Timer()
        timer?.schedule(object : TimerTask() {
            override fun run() {
                val currentMaxAmplitude = mediaRecorder.maxAmplitude
                try {
                    recorderView.update(currentMaxAmplitude)
                } catch (ex: Exception) {

                }
            }
        }, 0, 30)
    }

    private fun stopRecording() {
        mediaRecorder.stop()
        endTimer()
        recorderView.recreate()
        viewModel.onNewFileReady()
    }

    private fun endTimer() {
        timer?.cancel()
    }
}


