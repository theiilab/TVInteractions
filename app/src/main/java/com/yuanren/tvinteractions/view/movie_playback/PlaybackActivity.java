package com.yuanren.tvinteractions.view.movie_playback;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;

import com.yuanren.tvinteractions.R;
import com.yuanren.tvinteractions.log.Metrics;
import com.yuanren.tvinteractions.model.Movie;
import com.yuanren.tvinteractions.model.MovieList;

public class PlaybackActivity extends FragmentActivity {
    public static final String SELECTED_MOVIE_ID = "selectionMovieId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playback);

        Movie selectedMovie = MovieList.getMovie((int)getIntent().getExtras().getLong(SELECTED_MOVIE_ID));
        Metrics metrics = (Metrics) getApplicationContext();

        Fragment fragment;
        if (metrics.targetMovie.equals(selectedMovie.getTitle())) {
            fragment = PlaybackFragment.newInstance(getIntent().getExtras().getLong(SELECTED_MOVIE_ID));
        } else {
            fragment = PlaybackFragment2.newInstance(getIntent().getExtras().getLong(SELECTED_MOVIE_ID));
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.playback_fragment, fragment)
                    .commitNow();
        }
    }
}