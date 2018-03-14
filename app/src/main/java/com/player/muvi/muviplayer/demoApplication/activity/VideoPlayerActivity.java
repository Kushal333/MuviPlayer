package com.player.muvi.muviplayer.demoApplication.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import com.player.muvi.muviplayer.R;
import com.player.muvi.muviplayer.demoApplication.App;
import com.player.muvi.muviplayer.demoApplication.data.MediaItem;
import com.player.muvi.muviplayer.demoApplication.data.Samples;
import com.player.muvi.muviplayer.demoApplication.library.listener.VideoControlsSeekListener;
import com.player.muvi.muviplayer.demoApplication.library.listener.VideoControlsVisibilityListener;
import com.player.muvi.muviplayer.demoApplication.library.ui.widget.VideoControls;
import com.player.muvi.muviplayer.demoApplication.library.ui.widget.VideoControlsLeanback;
import com.player.muvi.muviplayer.demoApplication.library.ui.widget.VideoControlsMobile;
import com.player.muvi.muviplayer.demoApplication.library.ui.widget.VideoView;
import com.player.muvi.muviplayer.demoApplication.manager.PlaylistManager;
import com.player.muvi.muviplayer.demoApplication.playlist.VideoApi;

import java.util.LinkedList;
import java.util.List;


public class VideoPlayerActivity extends Activity implements VideoControlsSeekListener {
    public static final String EXTRA_INDEX = "EXTRA_INDEX";
    public static final int PLAYLIST_ID = 6; //Arbitrary, for the example (different from audio)

    protected VideoApi videoApi;
    protected VideoView videoView;
    protected PlaylistManager playlistManager;

    protected int selectedIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_player_activity);

        retrieveExtras();
        init();
    }

    @Override
    protected void onStop() {
        super.onStop();
        playlistManager.removeVideoApi(videoApi);
        playlistManager.invokeStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        playlistManager.invokeStop();
    }

    @Override
    public boolean onSeekStarted() {
        playlistManager.invokeSeekStarted();
        return true;
    }

    @Override
    public boolean onSeekEnded(long seekTime) {
        playlistManager.invokeSeekEnded(seekTime);
        return true;
    }

    /**
     * Retrieves the extra associated with the selected playlist index
     * so that we can start playing the correct item.
     */
    protected void retrieveExtras() {
        Bundle extras = getIntent().getExtras();
        selectedIndex = extras.getInt(EXTRA_INDEX, 0);
    }

    protected void init() {
        setupPlaylistManager();

        videoView = findViewById(R.id.video_play_activity_video_view);
        videoView.setHandleAudioFocus(false);
     //   videoView.getVideoControls().setSeekListener(this);

        VideoControlsMobile mobile = findViewById(R.id.Controls);
        mobile.setVideoView(videoView);

        try {
            videoView.setControls(mobile, true);
            VideoControls controls = videoView.getVideoControls();
            controls.setSeekListener(this);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "" + e, Toast.LENGTH_LONG).show();
        }

        videoApi = new VideoApi(videoView);
        playlistManager.addVideoApi(videoApi);
        playlistManager.play(0, false);

        videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.showControls();
            }
        });
    }

    /**
     * Retrieves the playlist instance and performs any generation
     * of content if it hasn't already been performed.
     */
    private void setupPlaylistManager() {
        playlistManager = App.getPlaylistManager();

        List<MediaItem> mediaItems = new LinkedList<>();
        for (Samples.Sample sample : Samples.getVideoSamples()) {
            MediaItem mediaItem = new MediaItem(sample, false);
            mediaItems.add(mediaItem);
        }

        playlistManager.setParameters(mediaItems, selectedIndex);
        playlistManager.setId(PLAYLIST_ID);
    }
}
