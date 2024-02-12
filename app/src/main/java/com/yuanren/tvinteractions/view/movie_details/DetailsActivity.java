package com.yuanren.tvinteractions.view.movie_details;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import com.yuanren.tvinteractions.R;
import com.yuanren.tvinteractions.log.Block;
import com.yuanren.tvinteractions.log.Session;
import com.yuanren.tvinteractions.log.Task;

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

    /** ----- log ----- */
    @Override
    public void onBackPressed() {
        Session session = (Session) getApplicationContext();
        Block block = session.getCurrentBlock();
        Task task = block.getCurrentTask();
        if ((session.id == 1 || session.id == 2) && session.method.equals("Remote") && task.id < 7 && block.targetMovie.equals(block.selectedMovie)) {
            return;
        }
        super.onBackPressed();
    }
}