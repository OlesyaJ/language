package com.example.language.content

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
import androidx.recyclerview.widget.RecyclerView
import com.example.language.R
import com.example.language.content.vm.LiamViewModel
import com.google.android.material.chip.ChipGroup
import java.io.File
import kotlin.math.roundToInt

class LiamFragment : Fragment(R.layout.fragment_liam) {

    private val viewModel by viewModels<LiamViewModel>()

    private lateinit var chipGroup: ChipGroup
    private lateinit var rvMessages: RecyclerView
    private lateinit var btnPushToSpeak: View
    private lateinit var vInnerCircle: ImageView
    private lateinit var vOuterCircle: ImageView

    private var pttAnimation: Animator? = null
    private lateinit var mediaRecorder: MediaRecorder

    private var smallSize: Int = 0
    private var largeSize: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        findViews(view)
        configureListeners()
    }

    private fun findViews(root: View) {
        chipGroup = root.findViewById(R.id.suggestion_chips)
        rvMessages = root.findViewById(R.id.rv_messages)
        btnPushToSpeak = root.findViewById(R.id.btn_push_to_speak)
        vInnerCircle = root.findViewById(R.id.iv_round_inner)
        vOuterCircle = root.findViewById(R.id.iv_round_outer)
    }

    private fun calculateSizes() {
        if (smallSize == 0 || largeSize == 0) {
            smallSize = vInnerCircle.measuredHeight
            largeSize = (vOuterCircle.measuredHeight * 0.8).roundToInt()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun configureListeners() {
        btnPushToSpeak.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    animateExp()
                    startRecording(File(requireActivity().cacheDir, "test.mp3"))
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
        mediaRecorder.start()
    }

    private fun stopRecording() {
        try {
            mediaRecorder.stop()
        } catch (ex: Exception) {
            // as intended
        }
        viewModel.onNewFileReady()
    }
}
