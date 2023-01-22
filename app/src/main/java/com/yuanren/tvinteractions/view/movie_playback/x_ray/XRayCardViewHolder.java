package com.yuanren.tvinteractions.view.movie_playback.x_ray;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yuanren.tvinteractions.R;

import org.jetbrains.annotations.NotNull;

public class XRayCardViewHolder extends RecyclerView.ViewHolder {
    ImageView image;
    TextView name;


    public XRayCardViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);

        image = itemView.findViewById(R.id.info_image);
        name = itemView.findViewById(R.id.info_details);
    }
}
