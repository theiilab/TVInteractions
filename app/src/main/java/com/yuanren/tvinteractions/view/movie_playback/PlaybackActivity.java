package com.yuanren.tvinteractions.view.movie_playback;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;

import com.yuanren.tvinteractions.R;
import com.yuanren.tvinteractions.log.Metrics;
import com.yuanren.tvinteractions.model.Movie;
import com.yuanren.tvinteractions.model.MovieList;

public class PlaybackActivity extends FragmentActivity {
    public static final String SELECTED_MOVIE_ID = "selectionMovieId";

    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playback);

        /** ----- log ----- */
        Movie selectedMovie = MovieList.getMovie(getApplicationContext(), (int)getIntent().getExtras().getLong(SELECTED_MOVIE_ID));
        Metrics metrics = (Metrics) getApplicationContext();

        if (metrics.session == 1) {
            fragment = PlaybackFragment.newInstance(getIntent().getExtras().getLong(SELECTED_MOVIE_ID));
        } else if (metrics.session == 2) { // session = 2
            fragment = PlaybackFragment2.newInstance(getIntent().getExtras().getLong(SELECTED_MOVIE_ID));
        } else {
            fragment = PlaybackFragment0.newInstance(getIntent().getExtras().getLong(SELECTED_MOVIE_ID));
        }
        /** --------------- */

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.playback_fragment, fragment)
                    .commitNow();
        }
    }

    /** ----- log ----- */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (fragment instanceof PlaybackFragment2 && requestCode == PlaybackFragment2.REQUEST_CODE_X_RAY_CONTENT) {
            ((PlaybackFragment2) fragment).onResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}