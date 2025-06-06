package com.yuanren.tvinteractions.view.movie_details;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import com.yuanren.tvinteractions.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailsActivity#} factory method to
 * create an instance of this fragment.
 */
public class DetailsActivity extends FragmentActivity {
    public static final String SELECTED_MOVIE_ID = "selectionMovieId";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.details_fragment, DetailsFragment.newInstance(getIntent().getExtras().getLong(SELECTED_MOVIE_ID)))
                    .commitNow();
        }
    }
}