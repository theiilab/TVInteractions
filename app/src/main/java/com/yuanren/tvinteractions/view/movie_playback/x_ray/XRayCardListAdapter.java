package com.yuanren.tvinteractions.view.movie_playback.x_ray;

import android.content.ClipData;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yuanren.tvinteractions.R;
import com.yuanren.tvinteractions.model.XRayItem;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class XRayCardListAdapter extends RecyclerView.Adapter {
    private static final String TAG = "XRayCardListAdapter";

    private List<XRayItem> data;

    public XRayCardListAdapter(List<XRayItem> data) {
        this.data = data;
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.x_ray_card, parent, false);
        return new XRayCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        // get the current item in x-ray
        XRayItem item = data.get(position);

        XRayCardViewHolder xRayCardViewHolder = (XRayCardViewHolder) holder;
        xRayCardViewHolder.name.setText(item.getName());
        Glide.with(xRayCardViewHolder.itemView.getContext())
                .load(item.getImageUrl())
                .centerCrop()
                .into(xRayCardViewHolder.image);

        xRayCardViewHolder.cover.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    xRayCardViewHolder.cover.setBackgroundResource(R.drawable.shape_x_ray_focused);

                } else {
                    xRayCardViewHolder.cover.setBackgroundResource(R.drawable.shape_x_ray_unfocused);
                }
            }
        });

        xRayCardViewHolder.cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "setOnClickListener");
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
