package com.kidx.kidvoo

import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.android.youtube.player.*
import com.kidx.kidvoo.databinding.ActivitySampleBinding

class SampleActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {

    private lateinit var binding: ActivitySampleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sample)
        initView()
    }

    private fun initView() {
        binding.ytView.initialize(Config.YOUTUBE_API_KEY, this)
    }

    override fun onInitializationSuccess(provider: YouTubePlayer.Provider?, player: YouTubePlayer?,
        wasRestored: Boolean) {
        if (!wasRestored) {
            player?.cueVideo("q6uuw0wwCgU"); // Plays https://www.youtube.com/watch?v=q6uuw0wwCgU
        }
    }

    override fun onInitializationFailure(provider: YouTubePlayer.Provider?,
        errorReason: YouTubeInitializationResult?) {
        Toast.makeText(this, errorReason.toString(), Toast.LENGTH_SHORT).show()
    }

}
