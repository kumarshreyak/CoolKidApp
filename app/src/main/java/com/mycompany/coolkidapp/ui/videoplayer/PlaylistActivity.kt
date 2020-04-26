package com.mycompany.coolkidapp.ui.videoplayer

import android.app.FragmentManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.databinding.DataBindingUtil
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerFragment
import com.mycompany.coolkidapp.Config
import com.mycompany.coolkidapp.R
import com.mycompany.coolkidapp.databinding.ActivityPlaylistBinding
import com.mycompany.coolkidapp.model.VideoItem
import io.reactivex.Observable

class PlaylistActivity : AppCompatActivity(),
    VideoListAdapter.ItemClickInterface {

    private lateinit var binding: ActivityPlaylistBinding
    private var videoList = ArrayList<VideoItem>()
    private lateinit var videoFragment: YouTubePlayerFragment
    private lateinit var fm: FragmentManager
    private var isInitializing = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_playlist
        )
        fm = fragmentManager
        initData()
        initRecyclerView()
        initVideoFragment()
    }

    private fun initData() {
        videoList.add(VideoItem("uGrBHohIgQY", "Winning Google Kickstart Round A 2020 + Facecam"))
        videoList.add(VideoItem("i_yLpCLMaKk", "Charlie Puth & Selena Gomez - We Don't Talk Anymore [Official Live Performance]"))
        videoList.add(VideoItem("q6uuw0wwCgU", "Naruto AMV - Superhero"))
        videoList.add(VideoItem("q6uuw0wwCgU", "Naruto AMV - Superhero"))
        videoList.add(VideoItem("q6uuw0wwCgU", "Naruto AMV - Superhero"))
        videoList.add(VideoItem("q6uuw0wwCgU", "Naruto AMV - Superhero"))
        videoList.add(VideoItem("q6uuw0wwCgU", "Naruto AMV - Superhero"))
        videoList.add(VideoItem("q6uuw0wwCgU", "Naruto AMV - Superhero"))
        videoList.add(VideoItem("q6uuw0wwCgU", "Naruto AMV - Superhero"))
    }

    private fun initVideoFragment() {
        videoFragment = YouTubePlayerFragment.newInstance()
        videoFragment?.initialize(Config.YOUTUBE_API_KEY, object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(provider: YouTubePlayer.Provider?,
                                                 player: YouTubePlayer?, wasRestored: Boolean) {
                if (!wasRestored) {
                    player?.cueVideo(videoList[0].url) // Plays https://www.youtube.com/watch?v=q6uuw0wwCgU
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
        binding.rvVideoList.adapter =
            VideoListAdapter(videoList, this, this)
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
