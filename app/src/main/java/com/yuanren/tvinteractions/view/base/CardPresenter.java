package com.yuanren.tvinteractions.view.base;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.leanback.widget.Presenter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yuanren.tvinteractions.R;
import com.yuanren.tvinteractions.model.Movie;

public class CardPresenter extends Presenter {
    private static final String TAG = "CardPresenter";

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        Log.d(TAG, "onCreateViewHolder");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card, parent, false);
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        Log.d(TAG, "onBindViewHolder");
        Movie movie = (Movie) item;
        CardViewHolder cardViewHolder = (CardViewHolder) viewHolder;
//        cardViewHolder.title.setText(movie.getTitle());
        Glide.with(viewHolder.view.getContext())
                .load(movie.getCardImageUrl())
                .thumbnail(0.1f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(cardViewHolder.movie);
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {
        Log.d(TAG, "onUnbindViewHolder");
        CardViewHolder cardViewHolder = (CardViewHolder) viewHolder;
//        cardViewHolder.title.setText(null);
        cardViewHolder.movie.setImageDrawable(null);
    }
}
