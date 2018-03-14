package com.player.muvi.muviplayer.demoApplication.service;


import android.support.annotation.NonNull;

import com.devbrackets.android.playlistcore.api.MediaPlayerApi;
import com.devbrackets.android.playlistcore.components.playlisthandler.DefaultPlaylistHandler;
import com.devbrackets.android.playlistcore.components.playlisthandler.PlaylistHandler;
import com.devbrackets.android.playlistcore.service.BasePlaylistService;
import com.player.muvi.muviplayer.demoApplication.App;
import com.player.muvi.muviplayer.demoApplication.data.MediaItem;
import com.player.muvi.muviplayer.demoApplication.manager.PlaylistManager;
import com.player.muvi.muviplayer.demoApplication.playlist.AudioApi;


/**
 * A simple service that extends {@link BasePlaylistService} in order to provide
 * the application specific information required.
 */
public class MediaService extends BasePlaylistService<MediaItem, PlaylistManager> {

    @Override
    public void onCreate() {
        super.onCreate();

        // Adds the audio player implementation, otherwise there's nothing to play media with
        getPlaylistManager().getMediaPlayers().add(new AudioApi(getApplicationContext()));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // Releases and clears all the MediaPlayersMediaImageProvider
        for (MediaPlayerApi<MediaItem> player : getPlaylistManager().getMediaPlayers()) {
            player.release();
        }

        getPlaylistManager().getMediaPlayers().clear();
    }

    @NonNull
    @Override
    protected PlaylistManager getPlaylistManager() {
        return App.getPlaylistManager();
    }

    @NonNull
    @Override
    public PlaylistHandler<MediaItem> newPlaylistHandler() {
        MediaImageProvider imageProvider = new MediaImageProvider(getApplicationContext(), new MediaImageProvider.OnImageUpdatedListener() {
            @Override
            public void onImageUpdated() {
                getPlaylistHandler().updateMediaControls();
            }
        });

        return new DefaultPlaylistHandler.Builder<>(
                getApplicationContext(),
                getClass(),
                getPlaylistManager(),
                imageProvider
        ).build();
    }
}