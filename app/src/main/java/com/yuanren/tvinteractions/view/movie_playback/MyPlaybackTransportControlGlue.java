package com.yuanren.tvinteractions.view.movie_playback;

import android.content.Context;

import androidx.leanback.media.PlaybackTransportControlGlue;
import androidx.leanback.media.PlayerAdapter;
import androidx.leanback.widget.PlaybackRowPresenter;
import androidx.leanback.widget.PlaybackTransportRowPresenter;

public class MyPlaybackTransportControlGlue<PlayerAdapter> extends PlaybackTransportControlGlue {
    /**
     * Constructor for the glue.
     *
     * @param context
     * @param impl    Implementation to underlying media player.
     */
    public MyPlaybackTransportControlGlue(Context context, PlayerAdapter impl) {
        super(context, (androidx.leanback.media.PlayerAdapter) impl);
    }

    @Override
    protected PlaybackRowPresenter onCreateRowPresenter() {
        PlaybackTransportRowPresenter presenter = (PlaybackTransportRowPresenter) super.onCreateRowPresenter();
        presenter.setDescriptionPresenter(new PlaybackInfoPresenter());
        return presenter;
    }
}
