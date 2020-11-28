package com.example.language.content

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.language.R
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragmentX

class TedFragment : Fragment(R.layout.fragment_ted), YouTubePlayer.OnInitializedListener {

    private lateinit var youtube: YouTubePlayerSupportFragmentX
    private lateinit var player: YouTubePlayer

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findViews(view)
        configureViews()
    }

    private fun configureViews() {
        youtube.initialize("AIzaSyDEC5Hn4BuT5biWkdb7Lf67uvv7zi8cn-Q", this)
    }

    private fun findViews(root: View) {
        val fragment = YouTubePlayerSupportFragmentX()
        childFragmentManager.beginTransaction()
            .add(R.id.youtube, fragment)
            .commit()
        youtube = fragment
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
