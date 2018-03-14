package com.player.muvi.muviplayer.demoApplication.library.core.video;

/**
 * Represents a protocol that the object can call clear.  This
 * is used to reference both the {@link com.player.muvi.muviplayer.demoApplication.library.core.video.ResizingSurfaceView
 * com.devbrackets.android.exomedia.core.video.ResizingSurfaceView}
 * and {@link com.player.muvi.muviplayer.demoApplication.library.core.video.ResizingTextureView} which can both
 * have their surfaces cleared.
 */
public interface ClearableSurface {
    void clearSurface();
}
