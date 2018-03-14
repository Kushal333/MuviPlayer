package com.player.muvi.muviplayer.demoApplication.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.player.muvi.muviplayer.demoApplication.library.ui.widget.VideoView;

/**
 * Created by Kushal on 3/13/2018.
 */

public class VideoIndividualActivity extends VideoPlayerActivity  {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (videoView.getVideoControls() != null) {
        //    videoView.getVideoControls().setVisibilityListener(new ControlsVisibilityListener());
        }
    }
}
