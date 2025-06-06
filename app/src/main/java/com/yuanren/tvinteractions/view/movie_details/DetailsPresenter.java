package com.yuanren.tvinteractions.view.movie_details;

import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.leanback.widget.Presenter;

import com.yuanren.tvinteractions.R;
import com.yuanren.tvinteractions.base.DetailsAnimationCallback;
import com.yuanren.tvinteractions.model.Movie;
import com.yuanren.tvinteractions.view.movie_playback.PlaybackActivity;

public class DetailsPresenter extends Presenter {
    private static final String TAG = "MovieDetailPresenter";
    private DetailsAnimationCallback detailsCallback;
    private long movieId;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        Log.d(TAG, "onCreateViewHolder");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_card, parent, false);
        return new DetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        Log.d(TAG, "onBindViewHolder");
        Movie movie = (Movie) item;
        movieId = movie.getId();
        DetailsViewHolder detailsViewHolder = (DetailsViewHolder) viewHolder;
        detailsViewHolder.title.setText(movie.getTitle());
        detailsViewHolder.description.setText(movie.getDescription());
        detailsViewHolder.studio.setText(movie.getStudio());
        detailsViewHolder.category.setText(" • " + movie.getCategory());

        // initially focused by default
        detailsViewHolder.playIB.setBackground(viewHolder.view.getContext().getDrawable(R.drawable.shape_details_round_corner_focused));

        // set listeners for image buttons
        detailsViewHolder.playIB.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                // is focused
                if (b) {
                    view.setBackground(view.getContext().getDrawable(R.drawable.shape_details_round_corner_focused));
                } else {
                    view.setBackground(view.getContext().getDrawable(R.drawable.shape_details_round_corner_unfocused));
                }
            }
        });
        detailsViewHolder.playIB.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {

                    switch (i) {
                        case KeyEvent.KEYCODE_DPAD_DOWN:
                            detailsCallback.backgroundToggle();
                            break;
                        case KeyEvent.KEYCODE_ENTER:
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                            Intent intent = new Intent(detailsViewHolder.view.getContext(), PlaybackActivity.class);
                            intent.putExtra(PlaybackActivity.SELECTED_MOVIE_ID, movieId);
                            detailsViewHolder.view.getContext().startActivity(intent);
                            break;
                    }
                }
                return false;
            }
        });

        detailsViewHolder.restartIB.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                // is focused
                if (b) {
                    view.setBackground(view.getContext().getDrawable(R.drawable.shape_details_round_corner_focused));
                    detailsViewHolder.restartTV.setVisibility(View.VISIBLE);
                } else {
                    view.setBackground(view.getContext().getDrawable(R.drawable.shape_details_round_corner_unfocused));
                    detailsViewHolder.restartTV.setVisibility(View.GONE);
                }
            }
        });
        detailsViewHolder.restartIB.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (i) {
                        case KeyEvent.KEYCODE_DPAD_DOWN:
                            detailsCallback.backgroundToggle();
                            break;
                    }
                }
                return false;
            }
        });
//        detailsViewHolder.trailerIB.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//                if (b) {
//                    view.setBackground(view.getContext().getDrawable(R.drawable.shape_details_round_corner_focused));
//                    detailsViewHolder.trailerTV.setVisibility(View.VISIBLE);
//                } else {
//                    view.setBackground(view.getContext().getDrawable(R.drawable.shape_details_round_corner_unfocused));
//                    detailsViewHolder.trailerTV.setVisibility(View.GONE);
//                }
//            }
//        });
//        detailsViewHolder.trailerIB.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View view, int i, KeyEvent keyEvent) {
//                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
//                    switch (i) {
//                        case KeyEvent.KEYCODE_DPAD_DOWN:
//                            detailsCallback.backgroundToggle();
//                            break;
//                    }
//                }
//                return false;
//            }
//        });
        detailsViewHolder.myListIB.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                // is focused
                if (b) {
                    view.setBackground(view.getContext().getDrawable(R.drawable.shape_details_round_corner_focused));
                    detailsViewHolder.myListTV.setVisibility(View.VISIBLE);
                } else {
                    view.setBackground(view.getContext().getDrawable(R.drawable.shape_details_round_corner_unfocused));
                    detailsViewHolder.myListTV.setVisibility(View.GONE);
                }
            }
        });
        detailsViewHolder.myListIB.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (i) {
                        case KeyEvent.KEYCODE_DPAD_DOWN:
                            detailsCallback.backgroundToggle();
                            break;
                    }
                }
                return false;
            }
        });
    }

    public void setMovieDetailsCallback(DetailsAnimationCallback callback) {
        this.detailsCallback = callback;
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {

    }

}
