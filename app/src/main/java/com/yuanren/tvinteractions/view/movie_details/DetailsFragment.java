package com.yuanren.tvinteractions.view.movie_details;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.leanback.app.RowsSupportFragment;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.HeaderItem;
import androidx.leanback.widget.ListRow;
import androidx.leanback.widget.OnItemViewSelectedListener;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.Row;
import androidx.leanback.widget.RowPresenter;

import com.yuanren.tvinteractions.R;
import com.yuanren.tvinteractions.base.DetailsAnimationCallback;
import com.yuanren.tvinteractions.model.Movie;
import com.yuanren.tvinteractions.model.MovieList;
import com.yuanren.tvinteractions.view.base.CardPresenter;
import com.yuanren.tvinteractions.view.base.RowPresenterSelector;

import org.jetbrains.annotations.NotNull;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsFragment extends RowsSupportFragment implements DetailsAnimationCallback {
    private static final String TAG = "DetailsFragment";

    private FrameLayout backgroundContainer;
    private ImageView backgroundImage;

    private float originalY;


    // num of related movies/recommendations
    private static final int NUM_COLS = 15;
    private ArrayObjectAdapter mRowsAdapter = new ArrayObjectAdapter(new RowPresenterSelector());

    private Movie movie;
    private List<Movie> list;

    public DetailsFragment() {
        // Required empty public constructor
    }

    public static DetailsFragment newInstance(long id) {
        Log.d(TAG, "Item: " + String.valueOf(id));
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putLong(DetailsActivity.SELECTED_MOVIE_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // get movies list and selected movie
        list = MovieList.getList();
        movie = MovieList.findBy((int)getArguments().getLong(DetailsActivity.SELECTED_MOVIE_ID));

        backgroundContainer = getActivity().findViewById(R.id.background_container);
        originalY = backgroundContainer.getY();
        backgroundImage = getActivity().findViewById(R.id.background_image);
        Glide.with(getContext())
                .load(movie.getBackgroundImageUrl())
                .centerCrop()
                .into(backgroundImage);

        addRowView();

        setOnItemViewSelectedListener(new ItemViewSelectedListener());
    }

    private void addRowView() {
        // movie details view
        DetailsPresenter detailsPresenter = new DetailsPresenter();
        detailsPresenter.setMovieDetailsCallback(this);
        ArrayObjectAdapter detailsAdapter = new ArrayObjectAdapter(detailsPresenter);
        detailsAdapter.add(movie);
        mRowsAdapter.add(new ListRow(detailsAdapter));

        // related movies view
        CardPresenter cardPresenter = new CardPresenter();
        ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(cardPresenter);
        for (int i = 0; i < NUM_COLS; i++) {
            listRowAdapter.add(list.get(i));
        }
        HeaderItem header = new HeaderItem(1, "Relate Movies");
        mRowsAdapter.add(new ListRow(header, listRowAdapter));
        setAdapter(mRowsAdapter);
    }

    @Override
    public void backgroundToggle() {
        animateBgImage(true);
    }

    private void animateBgImage(boolean isVisible) {
        Animation animation;
        if (isVisible) {
            animation = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_up);
            backgroundContainer.setY(originalY - backgroundContainer.getHeight() / 2);
        } else {
            animation = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_down);
            backgroundContainer.setY(originalY);
        }
        backgroundContainer.startAnimation(animation);
    }

    // called when user navigate to the item and focus on it
    private final class ItemViewSelectedListener implements OnItemViewSelectedListener {
        @Override
        public void onItemSelected(
                Presenter.ViewHolder itemViewHolder,
                Object item,
                RowPresenter.ViewHolder rowViewHolder,
                Row row) {

            if (item instanceof Movie) {
                Log.d(TAG, "ItemViewSelectedListener");

                itemViewHolder.view.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View view, int i, KeyEvent keyEvent) {
                        if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                            if (i == KeyEvent.KEYCODE_DPAD_UP) {
                                animateBgImage(false);
                            }
                        }
                        return false;
                    }
                });
            }
        }
    }
}