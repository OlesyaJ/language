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
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.language.R
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragmentX
import kotlin.math.roundToInt

class TedFragment : Fragment(R.layout.fragment_ted), YouTubePlayer.OnInitializedListener {

    private lateinit var youtube: YouTubePlayerSupportFragmentX
    private lateinit var player: YouTubePlayer

    private lateinit var btnPushToSpeak: View
    private lateinit var vInnerCircle: ImageView
    private lateinit var vOuterCircle: ImageView
    private lateinit var tvQuestions: TextView
    private lateinit var tvQuestion: TextView

    private var smallSize: Int = 0
    private var largeSize: Int = 0

    private var pttAnimation: Animator? = null
    private lateinit var mediaRecorder: MediaRecorder

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findViews(view)
        configureViews()
        configureListeners()
    }

    private fun configureViews() {
        youtube.initialize("AIzaSyDEC5Hn4BuT5biWkdb7Lf67uvv7zi8cn-Q", this)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun configureListeners() {
        btnPushToSpeak.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    animateExp()
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

    private fun stopRecording() {
        try {
            mediaRecorder.stop()
        } catch (ex: Exception) {
            // as intended
        }
        vOuterCircle.isVisible = false
        vInnerCircle.isVisible = false
        tvQuestions.text = "Answers"
        tvQuestion.text = "1. Answer"
    }

    private fun findViews(root: View) {
        val fragment = YouTubePlayerSupportFragmentX()
        childFragmentManager.beginTransaction()
            .add(R.id.youtube, fragment)
            .commit()
        youtube = fragment
        tvQuestion = root.findViewById(R.id.tv_question)
        tvQuestions = root.findViewById(R.id.tv_questions)
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

    override fun onInitializationSuccess(
        p0: YouTubePlayer.Provider,
        player: YouTubePlayer,
        p2: Boolean
    ) {
        this.player = player
        player.cueVideo("3VTsIju1dLI")
    }

    override fun onInitializationFailure(
        p0: YouTubePlayer.Provider,
        p1: YouTubeInitializationResult
    ) = Unit
}
