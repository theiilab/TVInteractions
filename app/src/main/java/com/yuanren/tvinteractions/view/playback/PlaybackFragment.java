package com.yuanren.tvinteractions.view.playback;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.leanback.app.VideoSupportFragment;
import androidx.leanback.app.VideoSupportFragmentGlueHost;
import androidx.leanback.media.MediaPlayerAdapter;
import androidx.leanback.media.PlaybackTransportControlGlue;
import androidx.leanback.widget.PlaybackControlsRow;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yuanren.tvinteractions.R;
import com.yuanren.tvinteractions.model.Movie;
import com.yuanren.tvinteractions.model.MovieList;
import com.yuanren.tvinteractions.view.movie_detail.DetailsActivity;
import com.yuanren.tvinteractions.view.movie_detail.DetailsFragment;

import org.jetbrains.annotations.NotNull;

public class PlaybackFragment extends VideoSupportFragment {
    private static final String TAG = "PlaybackFragment";

    private PlaybackTransportControlGlue<MediaPlayerAdapter> mTransportControlGlue;
    private Movie movie;

    public PlaybackFragment() {
        // Required empty public constructor
    }

    public static PlaybackFragment newInstance(long id) {
        Log.d(TAG, "Item: " + String.valueOf(id));

        PlaybackFragment fragment = new PlaybackFragment();
        Bundle args = new Bundle();
        args.putLong(PlaybackActivity.SELECTED_MOVIE_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // get selected movie
        movie = MovieList.findBy((int)getArguments().getLong(PlaybackActivity.SELECTED_MOVIE_ID));

        VideoSupportFragmentGlueHost glueHost =
                new VideoSupportFragmentGlueHost(PlaybackFragment.this);

        MediaPlayerAdapter playerAdapter = new MediaPlayerAdapter(getActivity());
        playerAdapter.setRepeatAction(PlaybackControlsRow.RepeatAction.INDEX_NONE);

        mTransportControlGlue = new PlaybackTransportControlGlue<>(getActivity(), playerAdapter);
        mTransportControlGlue.setHost(glueHost);
        mTransportControlGlue.setTitle(movie.getTitle());
        mTransportControlGlue.setSubtitle(movie.getDescription());
        mTransportControlGlue.playWhenPrepared();
        playerAdapter.setDataSource(Uri.parse(movie.getVideoUrl()));

    }

    @Override
    public void onPause() {
        super.onPause();
        if (mTransportControlGlue != null) {
            mTransportControlGlue.pause();
        }
    }
}