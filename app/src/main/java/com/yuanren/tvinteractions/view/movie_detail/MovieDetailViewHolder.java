package com.yuanren.tvinteractions.view.movie_detail;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.leanback.widget.Presenter;

import com.yuanren.tvinteractions.R;

public class MovieDetailViewHolder extends Presenter.ViewHolder {
//    ImageView backgroundImage;
    TextView title;
    TextView description;
    TextView studio;
    TextView category;
    ImageButton playIB;
    TextView playTV;
    ImageView restartIB;
    TextView restartTV;
    ImageButton trailerIB;
    TextView trailerTV;
    ImageButton myListIB;
    TextView myListTV;

    public MovieDetailViewHolder(View view) {
        super(view);

//        backgroundImage = view.findViewById(R.id.background_image);
        title = view.findViewById(R.id.title);
        description = view.findViewById(R.id.description);
        studio = view.findViewById(R.id.studio);
        category = view.findViewById(R.id.category);
        playIB = view.findViewById(R.id.play_IB);
        playTV = view.findViewById(R.id.play_TV);
        restartIB = view.findViewById(R.id.restart_IB);
        restartTV = view.findViewById(R.id.restart_TV);
        trailerIB = view.findViewById(R.id.trailer_IB);
        trailerTV = view.findViewById(R.id.trailer_TV);
        myListIB = view.findViewById(R.id.watch_list_IB);
        myListTV = view.findViewById(R.id.watch_list_TV);
    }
}
