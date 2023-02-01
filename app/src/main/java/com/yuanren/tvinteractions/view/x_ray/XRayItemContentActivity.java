package com.yuanren.tvinteractions.view.x_ray;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.yuanren.tvinteractions.R;
import com.yuanren.tvinteractions.model.Movie;
import com.yuanren.tvinteractions.model.MovieList;
import com.yuanren.tvinteractions.model.XRayItem;
import com.yuanren.tvinteractions.view.movie_playback.PlaybackActivity;

public class XRayItemContentActivity extends Activity {
    public static final String SELECTED_MOVIE_ID = "selectionMovieId";
    public static final String SELECTED_XRAY_ITEM_ID = "selectionXRayItemId";

    private ImageView image;
    private TextView title;

    private Movie movie;
    private XRayItem item;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xray_item_content);

        movie = MovieList.findBy((int)getIntent().getLongExtra(SELECTED_MOVIE_ID, 0));
        item = movie.getXRayItems().get((int)getIntent().getLongExtra(SELECTED_XRAY_ITEM_ID, 0));

        image = findViewById(R.id.x_ray_image);
        title = findViewById(R.id.x_ray_title);

        title.setText(item.getName());
        Glide.with(getApplicationContext())
                .load(item.getImageUrl())
                .centerCrop()
                .into(image);
    }
}
