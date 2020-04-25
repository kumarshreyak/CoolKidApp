package com.mycompany.coolkidapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerFragment
import com.google.android.youtube.player.YouTubePlayerSupportFragment
import com.mycompany.coolkidapp.databinding.ActivityPlaylistBinding

class PlaylistActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlaylistBinding
    private var urlList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_playlist)
        initData()
        initRecyclerView()
        initVideoFragment()
    }

    private fun initData() {
        urlList.add("uGrBHohIgQY")
        urlList.add("i_yLpCLMaKk")
        urlList.add("q6uuw0wwCgU")
    }

    private fun initVideoFragment() {
        val videoFragment = this.supportFragmentManager.findFragmentByTag("tag") as YouTubePlayerFragment
        videoFragment?.initialize(Config.YOUTUBE_API_KEY, object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(provider: YouTubePlayer.Provider?,
                player: YouTubePlayer?, wasRestored: Boolean) {
                if (!wasRestored) {
                    player?.cueVideo(urlList[0]) // Plays https://www.youtube.com/watch?v=q6uuw0wwCgU
                }
            }

            override fun onInitializationFailure(
                provider: YouTubePlayer.Provider?,
                result: YouTubeInitializationResult?) {

            }

        })
    }

    private fun initRecyclerView() {
        binding.rvVideoList.adapter = VideoListAdapter(urlList, this)
    }
}
