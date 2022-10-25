package com.yuanren.tvinteractions.view.movie_playback;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.yuanren.tvinteractions.R;

public class PlaybackActivity extends FragmentActivity {
    public static final String SELECTED_MOVIE_ID = "selectionMovieId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playback);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.playback_fragment, PlaybackFragment.newInstance(getIntent().getExtras().getLong(SELECTED_MOVIE_ID)))
                    .commitNow();
        }
    }
}