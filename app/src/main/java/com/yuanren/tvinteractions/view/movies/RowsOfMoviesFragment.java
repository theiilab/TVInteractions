package com.yuanren.tvinteractions.view.movies;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yuanren.tvinteractions.R;
import com.yuanren.tvinteractions.base.NavigationMenuCallback;
import com.yuanren.tvinteractions.log.Action;
import com.yuanren.tvinteractions.log.ActionType;
import com.yuanren.tvinteractions.log.Metrics;
import com.yuanren.tvinteractions.log.TaskType;
import com.yuanren.tvinteractions.model.Movie;
import com.yuanren.tvinteractions.model.MovieList;
import com.yuanren.tvinteractions.utils.FileUtils;
import com.yuanren.tvinteractions.view.base.CardPresenter;
import com.yuanren.tvinteractions.view.base.RowPresenterSelector;
import com.yuanren.tvinteractions.view.movie_details.DetailsActivity;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RowsOfMoviesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RowsOfMoviesFragment extends RowsSupportFragment {
    private static final String TAG = "RowsOfMoviesFragment";
    public static final int REQUEST_CODE_DETAILS = 101;
    private static final int NUM_COLS = 20;

    private ImageView bannerBackgroundImage;
    private TextView bannerMovieTitle;
    private TextView bannerMovieDescription;
    private ArrayObjectAdapter mRowsAdapter = new ArrayObjectAdapter(new RowPresenterSelector());
    private NavigationMenuCallback navigationMenuCallback;
    private List<Movie> list;
//    private List<Movie> dummyList;

    private Row currentSelectedRow;

    /** ----- log ----- */
    private Metrics metrics;
    private Long startTime;
    private Long endTime;
    private Long actionStartTime;
    private boolean findFlag = false;
    private int actionCount = 0;
    /** --------------- */

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
        /** ----- log ----- */
        metrics = (Metrics)getActivity().getApplicationContext();
        /** --------------- */

        // prepare for the date
        list = MovieList.getList();

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
//                if (j < MovieList.NUM_REAL_MOVIE) {
//                    listRowAdapter.add(list.get(i * 2 + j));
//                } else {
//                    listRowAdapter.add(dummyList.get(j - MovieList.NUM_REAL_MOVIE));
//                }
                listRowAdapter.add(list.get(i * NUM_COLS + j));
            }
            HeaderItem header = new HeaderItem(i, MovieList.MOVIE_CATEGORY[i]);
            mRowsAdapter.add(new ListRow(header, listRowAdapter));
        }
        setAdapter(mRowsAdapter);
    }

    private void updateBannerView(Movie movie) {
        bannerMovieTitle.setText(movie.getTitle());
        bannerMovieDescription.setText(movie.getDescription());
        Glide.with(this)
                .load(movie.getBackgroundImageUrl())
                .thumbnail(0.1f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(bannerBackgroundImage);
    }

    public void selectFirstItem() {
        setSelectedPosition(
                (int) currentSelectedRow.getId(),
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

    /** This is only used for restrict the tv app from restoring its ancestor item (memorize selected item between rows).
     * Used for user study, and should be remove for regular use */
    public void selectRowItemByCol(int row, int col) {
        setSelectedPosition(
                row,
                true,
                new ListRowPresenter.SelectItemViewHolderTask(col) {
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

    @Override
    public void onResume() {
        super.onResume();
    }

    /** ----- log ----- */
    public void onResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == REQUEST_CODE_DETAILS) {
            if (metrics.targetMovie.equals(metrics.selectedMovie)) {
                clearLogData();
                metrics.nextBlock();
            } else {
                actionCount++;  // for press back button from the wrong movie details
            }
        }
    }

    private void clearLogData() {
        startTime = 0L;
        endTime = 0L;
        actionStartTime = 0L;
        findFlag = false;
        actionCount = 0;
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
                currentSelectedRow = row;  // record the current row for auto focus when quit from nav menu
                int indexOfItemInRow = ((ArrayObjectAdapter)((ListRow)row).getAdapter()).indexOf(item);
                Log.d(TAG, "Row: " + row.getId() + ", Col: " + String.valueOf(indexOfItemInRow));

                itemViewHolder.view.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View view, int i, KeyEvent keyEvent) {
                        if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                            /** ----- log ----- */
                            if (!findFlag) {
                                findFlag = true;
                                startTime = System.currentTimeMillis();
                            }
                            actionCount++;
                            actionStartTime = System.currentTimeMillis();
                            /** --------------- */

                            if (i == KeyEvent.KEYCODE_DPAD_LEFT && indexOfItemInRow == 0) {
                                Log.d(TAG, "OnKeyRight - ItemViewSelectedListener");
                                navigationMenuCallback.navMenuToggle(true);
                            } else if (i == KeyEvent.KEYCODE_DPAD_UP && row.getId() == 0) { // navigate up on the first movie
                                return true; // handle it by self view, do nothing, so that the focus won't change or lost
                            }/** Below are only used for restrict the tv app from restoring its ancestor item (memorize selected item between rows).
                             * Used for user study, and should be remove for regular use */
                            else if (i == KeyEvent.KEYCODE_DPAD_UP) {
                                int rowIndex = (int) Math.max(0, row.getId() - 1);
                                selectRowItemByCol(rowIndex, indexOfItemInRow);
                                return true;
                            } else if (i == KeyEvent.KEYCODE_DPAD_DOWN) {
                                int rowIndex = (int) Math.min(MovieList.NUM_MOVIE_CATEGORY - 1, row.getId() + 1);
                                selectRowItemByCol(rowIndex, indexOfItemInRow);
                                return true;
                            }
                        } else if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
                            /** ----- log ----- */
                            Action action;
                            switch (i) {
                                case KeyEvent.KEYCODE_DPAD_LEFT:
                                    action = new Action(metrics, ((Movie) item).getTitle(),
                                            ActionType.TYPE_ACTION_LEFT.name, TAG, actionStartTime, System.currentTimeMillis());
                                    break;
                                case KeyEvent.KEYCODE_DPAD_RIGHT:
                                    action = new Action(metrics, ((Movie) item).getTitle(),
                                            ActionType.TYPE_ACTION_RIGHT.name, TAG, actionStartTime, System.currentTimeMillis());
                                    break;
                                case KeyEvent.KEYCODE_DPAD_UP:
                                    action = new Action(metrics, ((Movie) item).getTitle(),
                                            ActionType.TYPE_ACTION_UP.name, TAG, actionStartTime, System.currentTimeMillis());
                                    break;
                                case KeyEvent.KEYCODE_DPAD_DOWN:
                                    action = new Action(metrics, ((Movie) item).getTitle(),
                                            ActionType.TYPE_ACTION_DOWN.name, TAG, actionStartTime, System.currentTimeMillis());
                                    break;
                                case KeyEvent.KEYCODE_DPAD_CENTER:
                                case KeyEvent.KEYCODE_ENTER:
                                    action = new Action(metrics, ((Movie) item).getTitle(),
                                            ActionType.TYPE_ACTION_ENTER.name, TAG, actionStartTime, System.currentTimeMillis());
                                    break;
                                default:
                                    action = new Action(metrics, ((Movie) item).getTitle(),
                                            ActionType.TYPE_ACTION_DIRECTION.name, TAG, actionStartTime, System.currentTimeMillis());
                            }
                            FileUtils.writeRaw(getContext(), action);
                            /** --------------- */
                        }
                        return false; // handle the UI behavior by parent
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
                /** ----- log ----- */
                endTime = System.currentTimeMillis();
                String selectedMovie = ((Movie) item).getTitle();
                if (metrics.targetMovie.equals(selectedMovie)) {
                    metrics.selectedMovie = selectedMovie;
                    metrics.actionsPerTask = actionCount;
                    metrics.startTime = startTime;
                    metrics.endTime = endTime;

                    // only reflect navigation time in log for session 1
                    if (metrics.session == 1) {
                        FileUtils.write(getContext(), metrics);
                        metrics.nextTask();
                    }
                }
                /** --------------- */

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
                getActivity().startActivityForResult(intent, REQUEST_CODE_DETAILS);
            }
        }
    }
}