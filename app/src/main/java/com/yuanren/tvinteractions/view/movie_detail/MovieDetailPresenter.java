package com.yuanren.tvinteractions.view.movie_detail;

import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.leanback.widget.Presenter;

import com.yuanren.tvinteractions.R;
import com.yuanren.tvinteractions.base.MovieDetailsCallback;
import com.yuanren.tvinteractions.base.NavigationMenuCallback;
import com.yuanren.tvinteractions.model.Movie;

public class MovieDetailPresenter extends Presenter {
    private static final String TAG = "MovieDetailPresenter";
    private MovieDetailsCallback movieDetailsCallback;
    private float originalY = 0;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        Log.d(TAG, "onCreateViewHolder");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_card, parent, false);
        return new MovieDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        Log.d(TAG, "onBindViewHolder");
        Movie movie = (Movie) item;
        MovieDetailViewHolder movieDetailViewHolder = (MovieDetailViewHolder) viewHolder;
        movieDetailViewHolder.title.setText(movie.getTitle());
        movieDetailViewHolder.description.setText(movie.getDescription());
        movieDetailViewHolder.studio.setText(movie.getStudio());
        movieDetailViewHolder.category.setText(" • Romance");

        // initially focused by default
        movieDetailViewHolder.playIB.setBackground(viewHolder.view.getContext().getDrawable(R.drawable.shape_round_corner_focused));

        // set listeners for image buttons
        movieDetailViewHolder.playIB.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                // is focused
                if (b) {
                    view.setBackground(view.getContext().getDrawable(R.drawable.shape_round_corner_focused));
                } else {
                    view.setBackground(view.getContext().getDrawable(R.drawable.shape_round_corner_unfocused));
                }
            }
        });
        movieDetailViewHolder.playIB.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    if (i == KeyEvent.KEYCODE_DPAD_DOWN) {
                        movieDetailsCallback.backgroundToggle();
                    }
                }
                return false;
            }
        });

        movieDetailViewHolder.restartIB.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                // is focused
                if (b) {
                    view.setBackground(view.getContext().getDrawable(R.drawable.shape_round_corner_focused));
                    movieDetailViewHolder.restartTV.setVisibility(View.VISIBLE);
                } else {
                    view.setBackground(view.getContext().getDrawable(R.drawable.shape_round_corner_unfocused));
                    movieDetailViewHolder.restartTV.setVisibility(View.GONE);
                }
            }
        });
        movieDetailViewHolder.restartIB.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    if (i == KeyEvent.KEYCODE_DPAD_DOWN) {
                        movieDetailsCallback.backgroundToggle();
                    }
                }
                return false;
            }
        });
        movieDetailViewHolder.trailerIB.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    view.setBackground(view.getContext().getDrawable(R.drawable.shape_round_corner_focused));
                    movieDetailViewHolder.trailerTV.setVisibility(View.VISIBLE);
                } else {
                    view.setBackground(view.getContext().getDrawable(R.drawable.shape_round_corner_unfocused));
                    movieDetailViewHolder.trailerTV.setVisibility(View.GONE);
                }
            }
        });
        movieDetailViewHolder.trailerIB.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    if (i == KeyEvent.KEYCODE_DPAD_DOWN) {
                        movieDetailsCallback.backgroundToggle();
                    }
                }
                return false;
            }
        });
        movieDetailViewHolder.myListIB.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                // is focused
                if (b) {
                    view.setBackground(view.getContext().getDrawable(R.drawable.shape_round_corner_focused));
                    movieDetailViewHolder.myListTV.setVisibility(View.VISIBLE);
                } else {
                    view.setBackground(view.getContext().getDrawable(R.drawable.shape_round_corner_unfocused));
                    movieDetailViewHolder.myListTV.setVisibility(View.GONE);
                }
            }
        });
        movieDetailViewHolder.myListIB.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    if (i == KeyEvent.KEYCODE_DPAD_DOWN) {
                        movieDetailsCallback.backgroundToggle();
                    }
                }
                return false;
            }
        });
    }

    public void setMovieDetailsCallback(MovieDetailsCallback callback) {
        this.movieDetailsCallback = callback;
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {

    }

}
