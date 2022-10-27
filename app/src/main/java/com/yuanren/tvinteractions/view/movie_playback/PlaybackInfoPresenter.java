package com.yuanren.tvinteractions.view.movie_playback;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.leanback.widget.Presenter;

import com.yuanren.tvinteractions.R;
import com.yuanren.tvinteractions.model.Movie;

public class PlaybackInfoPresenter extends Presenter {
    private static final String TAG = "PlaybackInfoPresenter";

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.playback_info, parent, false);
        return new PlaybackInfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        Movie movie = (Movie) item;
        PlaybackInfoViewHolder playbackInfoViewHolder = (PlaybackInfoViewHolder)viewHolder;
        playbackInfoViewHolder.title.setText(movie.getTitle());
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {

    }
}
