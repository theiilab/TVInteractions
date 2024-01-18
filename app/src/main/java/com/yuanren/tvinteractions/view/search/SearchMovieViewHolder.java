package com.yuanren.tvinteractions.view.search;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yuanren.tvinteractions.R;

import org.jetbrains.annotations.NotNull;

public class SearchMovieViewHolder extends RecyclerView.ViewHolder {
    ImageView image;

    TextView title;

    View cover;

    public SearchMovieViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.movie);
        title = itemView.findViewById(R.id.title);
        cover = itemView.findViewById(R.id.selectable_cover);
    }
}
