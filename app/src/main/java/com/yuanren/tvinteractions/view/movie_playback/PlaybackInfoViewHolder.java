package com.yuanren.tvinteractions.view.movie_playback;

import android.view.View;
import android.widget.TextView;

import androidx.leanback.widget.Presenter;

import com.yuanren.tvinteractions.R;

public class PlaybackInfoViewHolder extends Presenter.ViewHolder {

    TextView title;

    public PlaybackInfoViewHolder(View view) {
        super(view);

        title = view.findViewById(R.id.title);
    }
}
