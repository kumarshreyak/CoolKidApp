package com.kidx.kidvoo.ui.videoplayer

import android.app.FragmentManager
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerFragment
import com.kidx.kidvoo.Config
import com.kidx.kidvoo.Config.Companion.BASE_URL
import com.kidx.kidvoo.Config.Companion.EXTRA_CATEGORY_CODE
import com.kidx.kidvoo.R
import com.kidx.kidvoo.databinding.ActivityPlaylistBinding
import com.kidx.kidvoo.model.VideoItem
import com.kidx.kidvoo.network.CoolNetworkService
import com.kidx.kidvoo.network.response.GetPlaylistResponse
import java.util.*
import kotlin.collections.ArrayList

class PlaylistActivity : AppCompatActivity(), VideoListAdapter.ItemClickInterface,
    YouTubePlayer.OnFullscreenListener, YouTubePlayer.PlayerStateChangeListener, PlaylistContract.View {

    private lateinit var binding: ActivityPlaylistBinding
    private var videoList = ArrayList<VideoItem>()
    private lateinit var videoFragment: YouTubePlayerFragment
    private lateinit var fm: FragmentManager
    private var mPlayer: YouTubePlayer? = null
    private var selectedPos = -1
    private var isFullscreen = false
    private lateinit var presenter: PlaylistContract.Presenter
    private var savedInstanceState: Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_playlist)
        fm = fragmentManager
        this.savedInstanceState = savedInstanceState
        initPresenter()
    }

    private fun initPresenter() {
        presenter = PlaylistPresenter(this, CoolNetworkService.getCoolNetworkService(BASE_URL))

        presenter.getPlaylist(Collections.singletonList(intent.getStringExtra(EXTRA_CATEGORY_CODE)))
    }

    override fun getPlaylistSuccess(response: GetPlaylistResponse) {
        videoList = ArrayList()
        for(item in response.responses) {
            for(videoItem in item.playlist) {
                videoList.add(VideoItem(videoItem.contentDetails.videoId, videoItem.snippet.title, false))
            }
        }
        initViews(savedInstanceState)
    }

    override fun apiFailure(failureMessage: String) {
        Snackbar.make(binding.root, failureMessage, Snackbar.LENGTH_SHORT).show()
    }

    private fun initViews(savedInstanceState: Bundle?) {
        if(resources.configuration.orientation != Configuration.ORIENTATION_LANDSCAPE)
            initRecyclerView()
        initVideoFragment(savedInstanceState)
        binding.btnFullscreen.setOnClickListener { mPlayer?.setFullscreen(true) }
    }

    private fun initData() {
        videoList.add(VideoItem("uGrBHohIgQY", "Winning Google Kickstart Round A 2020 + Facecam", false))
        videoList.add(VideoItem("i_yLpCLMaKk", "Charlie Puth & Selena Gomez - We Don't Talk Anymore [Official Live Performance]", false))
        videoList.add(VideoItem("q6uuw0wwCgU", "Naruto AMV - Superhero", false))
        videoList.add(VideoItem("q6uuw0wwCgU", "Naruto AMV - Superhero", false))
        videoList.add(VideoItem("q6uuw0wwCgU", "Naruto AMV - Superhero", false))
        videoList.add(VideoItem("q6uuw0wwCgU", "Naruto AMV - Superhero", false))
        videoList.add(VideoItem("q6uuw0wwCgU", "Naruto AMV - Superhero", false))
        videoList.add(VideoItem("q6uuw0wwCgU", "Naruto AMV - Superhero", false))
        videoList.add(VideoItem("q6uuw0wwCgU", "Naruto AMV - Superhero", false))
    }

    private fun initVideoFragment(savedInstanceState: Bundle?) {
        videoFragment = YouTubePlayerFragment.newInstance()
        videoFragment?.initialize(Config.YOUTUBE_API_KEY, object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(provider: YouTubePlayer.Provider?,
                                                 player: YouTubePlayer?, wasRestored: Boolean) {
                if (!wasRestored) {
                    mPlayer = player
                    mPlayer?.setOnFullscreenListener(this@PlaylistActivity)
                    mPlayer?.setPlayerStateChangeListener(this@PlaylistActivity)
                    if(selectedPos >= 0)
                        videoList[selectedPos].isPlaying = false
                    if(savedInstanceState != null) {
                        selectedPos = savedInstanceState.getInt(Config.VIDEO_NUM)
                        mPlayer?.loadVideo(videoList[selectedPos].url,
                            savedInstanceState.getString(Config.VIDEO_PLAYBACK_TIME)?.toInt()!!)
                        if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
                            mPlayer?.setFullscreen(true)
                    } else {
                        selectedPos = 0
                        mPlayer?.loadVideo(videoList[0].url)
                    }
                    if(selectedPos >= 0)
                        videoList[selectedPos].isPlaying = true
                    binding.rvVideoList.adapter?.notifyDataSetChanged()
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
                    player?.loadVideo(url) // Plays https://www.youtube.com/watch?v=q6uuw0wwCgU
                    mPlayer = player
                    mPlayer?.setOnFullscreenListener(this@PlaylistActivity)
                    mPlayer?.setPlayerStateChangeListener(this@PlaylistActivity)
                    if(selectedPos >= 0)
                        videoList[selectedPos].isPlaying = false
                    selectedPos = pos
                    videoList[selectedPos].isPlaying = true
                    binding.rvVideoList.adapter?.notifyDataSetChanged()
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

    // Auto play logic
    override fun onVideoEnded() {
        if(mPlayer != null) {
            if(selectedPos < videoList.size - 1) {
                selectedPos ++
                videoList[selectedPos - 1].isPlaying = false
                mPlayer?.loadVideo(videoList[selectedPos].url)
                videoList[selectedPos].isPlaying = true
                binding.rvVideoList.adapter?.notifyDataSetChanged()
            } else {
                if(selectedPos == videoList.size - 1) {
                    // Load new playlist
                }
            }
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        // Save player state across config change
        outState.putString(Config.VIDEO_PLAYBACK_TIME, mPlayer?.currentTimeMillis.toString())
        outState.putInt(Config.VIDEO_NUM, selectedPos)
        super.onSaveInstanceState(outState)
    }

    override fun onFullscreen(p0: Boolean) {
        isFullscreen = p0
        // For fullscreen -> portrait
        if(!p0 && resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun onAdStarted() {
    }

    override fun onLoading() {
    }

    override fun onVideoStarted() {
    }

    override fun onLoaded(p0: String?) {
    }

    override fun onError(p0: YouTubePlayer.ErrorReason?) {
    }

    override fun onBackPressed() {
        if(isFullscreen && resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            isFullscreen = false
        } else {
            super.onBackPressed()
        }
    }
}
