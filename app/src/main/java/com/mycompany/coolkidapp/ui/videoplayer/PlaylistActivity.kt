package com.mycompany.coolkidapp.ui.videoplayer

import android.app.FragmentManager
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
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
    private var mPlayer: YouTubePlayer? = null
    private var selectedPos = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_playlist
        )
        fm = fragmentManager
        initData()
        initViews(savedInstanceState)

    }

    private fun initViews(savedInstanceState: Bundle?) {
        initRecyclerView()
        initVideoFragment(savedInstanceState)
        binding.btnFullscreen.setOnClickListener {
            mPlayer?.setFullscreen(true)
        }
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

    private fun initVideoFragment(savedInstanceState: Bundle?) {
        videoFragment = YouTubePlayerFragment.newInstance()
        videoFragment?.initialize(Config.YOUTUBE_API_KEY, object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(provider: YouTubePlayer.Provider?,
                                                 player: YouTubePlayer?, wasRestored: Boolean) {
                if (!wasRestored) {
                    mPlayer = player
                    if(savedInstanceState != null) {
                        selectedPos = savedInstanceState.getInt(Config.VIDEO_NUM)
                        mPlayer?.cueVideo(videoList[selectedPos].url,
                            savedInstanceState.getString(Config.VIDEO_PLAYBACK_TIME)?.toInt()!!)
                        if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
                            mPlayer?.setFullscreen(true)
                    } else {
                        selectedPos = 0
                        mPlayer?.cueVideo(videoList[0].url)
                    }
                }
            }
            override fun onInitializationFailure(
                provider: YouTubePlayer.Provider?,
                result: YouTubeInitializationResult?) {
                mPlayer = null
                Toast.makeText(this@PlaylistActivity, result.toString(), Toast.LENGTH_SHORT).show()
            }
        })
        fm.beginTransaction().add(R.id.video_frame, videoFragment).commit()
    }

    private fun initRecyclerView() {
        binding.rvVideoList.adapter =
            VideoListAdapter(videoList, this, this)
    }

    override fun onItemClick(pos: Int, url: String) {
        videoFragment = YouTubePlayerFragment.newInstance()
        videoFragment?.initialize(Config.YOUTUBE_API_KEY, object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(provider: YouTubePlayer.Provider?,
                                                 player: YouTubePlayer?, wasRestored: Boolean) {
                if (!wasRestored) {
                    player?.cueVideo(url) // Plays https://www.youtube.com/watch?v=q6uuw0wwCgU
                    mPlayer = player
                    selectedPos = pos
                }
            }
            override fun onInitializationFailure(
                provider: YouTubePlayer.Provider?,
                result: YouTubeInitializationResult?) {
                mPlayer = null
                Toast.makeText(this@PlaylistActivity, result.toString(), Toast.LENGTH_SHORT).show()
            }
        })
        fm.beginTransaction().replace(R.id.video_frame, videoFragment).commitNow()
    }


    override fun onSaveInstanceState(outState: Bundle) {
        // Save player state across config change
        outState.putString(Config.VIDEO_PLAYBACK_TIME, mPlayer?.currentTimeMillis.toString())
        outState.putInt(Config.VIDEO_NUM, selectedPos)
        super.onSaveInstanceState(outState)
    }
}
