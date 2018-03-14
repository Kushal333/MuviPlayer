package com.player.muvi.muviplayer.demoApplication.playlist;


import com.devbrackets.android.playlistcore.api.MediaPlayerApi;
import com.devbrackets.android.playlistcore.listener.MediaStatusListener;
import com.player.muvi.muviplayer.demoApplication.data.MediaItem;
import com.player.muvi.muviplayer.demoApplication.library.listener.OnBufferUpdateListener;
import com.player.muvi.muviplayer.demoApplication.library.listener.OnCompletionListener;
import com.player.muvi.muviplayer.demoApplication.library.listener.OnErrorListener;
import com.player.muvi.muviplayer.demoApplication.library.listener.OnPreparedListener;
import com.player.muvi.muviplayer.demoApplication.library.listener.OnSeekCompletionListener;

import org.jetbrains.annotations.NotNull;

public abstract class BaseMediaApi implements MediaPlayerApi<MediaItem>,
        OnPreparedListener,
        OnCompletionListener,
        OnErrorListener,
        OnSeekCompletionListener,
        OnBufferUpdateListener {

    protected boolean prepared;
    protected int bufferPercent;

    protected MediaStatusListener<MediaItem> mediaStatusListener;

    @Override
    public void setMediaStatusListener(@NotNull MediaStatusListener<MediaItem> listener) {
        mediaStatusListener = listener;
    }

    @Override
    public void onCompletion() {
        if (mediaStatusListener != null) {
            mediaStatusListener.onCompletion(this);
        }
    }

    @Override
    public boolean onError(Exception e) {
        return mediaStatusListener != null && mediaStatusListener.onError(this);
    }

    @Override
    public void onPrepared() {
        prepared = true;

        if (mediaStatusListener != null) {
            mediaStatusListener.onPrepared(this);
        }
    }

    @Override
    public void onSeekComplete() {
        if (mediaStatusListener != null) {
            mediaStatusListener.onSeekComplete(this);
        }
    }

    @Override
    public void onBufferingUpdate(int percent) {
        bufferPercent = percent;

        if (mediaStatusListener != null) {
            mediaStatusListener.onBufferingUpdate(this, percent);
        }
    }
}
