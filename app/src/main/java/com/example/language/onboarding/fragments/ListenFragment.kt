package com.example.language.onboarding.fragments

import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.language.R


class ListenFragment : Fragment(R.layout.fragment_listen_tell_me_more) {

    private val navController by lazy { findNavController() }

    private val args by navArgs<ListenFragmentArgs>()

    private lateinit var btnSend: Button
    private lateinit var btnRedo: Button
    private lateinit var btnListen: View
    private lateinit var ivPlayPause: ImageView

    private lateinit var loader: ProgressBar

    private var isPlaying = false

    private var player: MediaPlayer? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findViews(view)

        configureListeners()
    }

    private fun findViews(root: View) {
        btnListen = root.findViewById(R.id.btn_listen)
        btnSend = root.findViewById(R.id.btn_send)
        btnRedo = root.findViewById(R.id.btn_redo)
        ivPlayPause = root.findViewById(R.id.iv_play)
    }

    private fun configureListeners() {
        btnListen.setOnClickListener {
            isPlaying = if (isPlaying) {
                ivPlayPause.setImageResource(R.drawable.ic_baseline_play_circle_filled_24)
                player?.stop()
                false
            } else {
                ivPlayPause.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24)
                startPlayer()
                true
            }
        }

        btnSend.setOnClickListener {
            navController.navigate(
                ListenFragmentDirections.actionListenFragmentToReadAloudFragment()
            )
        }

        btnRedo.setOnClickListener {
            navController.navigateUp()
        }
    }

    private fun startPlayer() {
        val mp = MediaPlayer()

        try {
            mp.setDataSource(args.fileAbsolutePath)
            mp.setOnCompletionListener {
                ivPlayPause.setImageResource(R.drawable.ic_baseline_play_circle_filled_24)
                isPlaying = false
            }
            mp.prepare()
            mp.start()
            player = mp
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}


