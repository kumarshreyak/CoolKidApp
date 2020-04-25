package com.mycompany.coolkidapp

import android.app.FragmentManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerFragment
import com.google.android.youtube.player.YouTubePlayerSupportFragment
import com.mycompany.coolkidapp.databinding.ActivityPlaylistBinding

class PlaylistActivity : AppCompatActivity(), VideoListAdapter.ItemClickInterface {

    private lateinit var binding: ActivityPlaylistBinding
    private var urlList = ArrayList<String>()
    private lateinit var videoFragment: YouTubePlayerFragment
    private lateinit var fm: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_playlist)
        fm = fragmentManager
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
        videoFragment = YouTubePlayerFragment.newInstance()
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
        fm.beginTransaction().add(R.id.video_frame, videoFragment).commit()
    }

    private fun initRecyclerView() {
        binding.rvVideoList.adapter = VideoListAdapter(urlList, this, this)
    }

    override fun onItemClick(url: String) {
        videoFragment = YouTubePlayerFragment.newInstance()
        videoFragment?.initialize(Config.YOUTUBE_API_KEY, object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(provider: YouTubePlayer.Provider?,
                                                 player: YouTubePlayer?, wasRestored: Boolean) {
                if (!wasRestored) {
                    player?.cueVideo(url) // Plays https://www.youtube.com/watch?v=q6uuw0wwCgU
                }
            }
            override fun onInitializationFailure(
                provider: YouTubePlayer.Provider?,
                result: YouTubeInitializationResult?) {

            }
        })
        fm.beginTransaction().replace(R.id.video_frame, videoFragment).commitNow()
    }
}
