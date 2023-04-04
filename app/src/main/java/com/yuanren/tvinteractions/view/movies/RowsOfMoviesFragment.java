package com.yuanren.tvinteractions.view.movies;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.leanback.app.RowsSupportFragment;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.HeaderItem;
import androidx.leanback.widget.ListRow;
import androidx.leanback.widget.ListRowPresenter;
import androidx.leanback.widget.OnItemViewClickedListener;
import androidx.leanback.widget.OnItemViewSelectedListener;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.Row;
import androidx.leanback.widget.RowPresenter;

import com.bumptech.glide.Glide;
import com.yuanren.tvinteractions.R;
import com.yuanren.tvinteractions.base.NavigationMenuCallback;
import com.yuanren.tvinteractions.model.Movie;
import com.yuanren.tvinteractions.model.MovieList;
import com.yuanren.tvinteractions.view.base.CardPresenter;
import com.yuanren.tvinteractions.view.base.RowPresenterSelector;
import com.yuanren.tvinteractions.view.movie_details.DetailsActivity;

import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RowsOfMoviesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RowsOfMoviesFragment extends RowsSupportFragment {
    private static final String TAG = "RowsOfMoviesFragment";
    private static final int NUM_COLS = 20;

    private ImageView bannerBackgroundImage;
    private TextView bannerMovieTitle;
    private TextView bannerMovieDescription;
    private ArrayObjectAdapter mRowsAdapter = new ArrayObjectAdapter(new RowPresenterSelector());
    private NavigationMenuCallback navigationMenuCallback;
    private List<Movie> list;
    private List<Movie> dummyList;

    public RowsOfMoviesFragment() {
        // Required empty public constructor
    }

    public static RowsOfMoviesFragment newInstance() {
        RowsOfMoviesFragment fragment = new RowsOfMoviesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // prepare for the date
        list = MovieList.setupMovies(NUM_COLS);
        dummyList = MovieList.getDummyList();

        // init views on the top banner
        bannerBackgroundImage = getActivity().findViewById(R.id.background_image);
        bannerMovieTitle = getActivity().findViewById(R.id.title);
        bannerMovieDescription = getActivity().findViewById(R.id.description);
        updateBannerView(list.get(0));

        // add rows of movies
        addRowsView();

        // set selection listener for each movie
        setOnItemViewSelectedListener(new ItemViewSelectedListener());
        setOnItemViewClickedListener(new ItemViewClickedListener());
    }

    public void setNavigationMenuCallback(NavigationMenuCallback callback) {
        this.navigationMenuCallback = callback;
    }

    private void addRowsView() {
        CardPresenter cardPresenter = new CardPresenter();
        for (int i = 0; i < MovieList.NUM_MOVIE_CATEGORY; i++) {
            ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(cardPresenter);
            for (int j = 0; j < NUM_COLS; j++) {
                if (j < MovieList.NUM_REAL_MOVIE) {
                    listRowAdapter.add(list.get(i * 2 + j));
                } else {
                    listRowAdapter.add(dummyList.get(j - MovieList.NUM_REAL_MOVIE));
                }

            }
            HeaderItem header = new HeaderItem(i, (list.get(i * 2).getCategory()));
            mRowsAdapter.add(new ListRow(header, listRowAdapter));
        }
        setAdapter(mRowsAdapter);
    }

    private void updateBannerView(Movie movie) {
        bannerMovieTitle.setText(movie.getTitle());
        bannerMovieDescription.setText(movie.getDescription());
        Glide.with(this)
                .load(movie.getBackgroundImageUrl())
                .centerCrop()
                .into(bannerBackgroundImage);
    }

    public void selectFirstItem() {
        setSelectedPosition(
                0,
                true, new ListRowPresenter.SelectItemViewHolderTask(0) {
                    @Override
                    public void run(Presenter.ViewHolder holder) {
                        super.run(holder);
                        holder.view.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                holder.view.requestFocus();
                            }
                        }, 10);
                    }
                });
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

                // update banner view with selected movie
                updateBannerView((Movie) item);

                // set listener for nav menu toggle
                int indexOfItem = ((ArrayObjectAdapter)((ListRow)row).getAdapter()).indexOf(item);
                itemViewHolder.view.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View view, int i, KeyEvent keyEvent) {
                        if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                            if (i == KeyEvent.KEYCODE_DPAD_LEFT && indexOfItem == 0) {
                                Log.d(TAG, "OnKeyRight - ItemViewSelectedListener");
                                navigationMenuCallback.navMenuToggle(true);
                            }
                        }
                        return false;
                    }
                });
            }
        }
    }

    // called when user press OK/Enter button on the remote
    private final class ItemViewClickedListener implements OnItemViewClickedListener {

        @Override
        public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {
            if (item instanceof Movie) {
                Movie movie = (Movie) item;
                Log.d(TAG, "Item: " + item.toString());
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra(DetailsActivity.SELECTED_MOVIE_ID, movie.getId());

                // start detail activity
//                Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
//                        getActivity(),
//                        ((CardViewHolder) itemViewHolder).view.get,
//                        DetailsActivity.SHARED_ELEMENT_NAME)
//                        .toBundle();
                getActivity().startActivity(intent);
            }
        }
    }
}