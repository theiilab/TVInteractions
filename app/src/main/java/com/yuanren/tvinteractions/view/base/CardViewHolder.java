package com.yuanren.tvinteractions.view.base;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.leanback.widget.Presenter;

import com.yuanren.tvinteractions.R;

public class CardViewHolder extends Presenter.ViewHolder {
    ImageView movie;
//    TextView title;

    public CardViewHolder(View view) {
        super(view);
       movie = view.findViewById(R.id.movie);
//       title = view.findViewById(R.id.title);
    }
}
