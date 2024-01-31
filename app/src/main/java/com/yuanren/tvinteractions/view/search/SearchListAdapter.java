package com.yuanren.tvinteractions.view.search;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yuanren.tvinteractions.R;
import com.yuanren.tvinteractions.base.OnKeyListener;
import com.yuanren.tvinteractions.log.Metrics;
import com.yuanren.tvinteractions.model.Movie;
import com.yuanren.tvinteractions.utils.FileUtils;
import com.yuanren.tvinteractions.view.movie_details.DetailsActivity;

import java.util.List;
import java.util.SplittableRandom;

public class SearchListAdapter extends RecyclerView.Adapter {

    private List<Movie> data;
    private boolean updateFlag = false;

    /** ----- log ----- */
    private OnKeyListener onKeyListener;
    /** --------------- */

    public SearchListAdapter(List<Movie> data) {
        this.data = data;
    }

    public void update(List<Movie> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_movie_card, parent, false);
        return new SearchMovieViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Movie movie = data.get(position);
        SearchMovieViewHolder searchMovieViewHolder = (SearchMovieViewHolder) holder;

        if (movie.getId() != -1) { // real movie, set background
            searchMovieViewHolder.title.setText("");
            Glide.with(holder.itemView.getContext())
                    .load(movie.getCardImageUrl())
                    .thumbnail(0.1f)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(searchMovieViewHolder.image);
            searchMovieViewHolder.image.setBackgroundColor(holder.itemView.getContext().getColor(R.color.transparent));
        } else { // 250 search dummy movies, set random purple background color within a range
            searchMovieViewHolder.title.setText(movie.getTitle());
            SplittableRandom random = new SplittableRandom();
            int r = random.nextInt(125,175);
            int g = random.nextInt(75,125);
            int b = random.nextInt(225,255);
            searchMovieViewHolder.image.setBackgroundColor(Color.rgb(r, g, b));
            searchMovieViewHolder.image.setImageResource(android.R.color.transparent);
        }

        searchMovieViewHolder.cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (movie.getId() == -1) {
                    return;
                }

                /** Disable this only for log */
//                Intent intent = new Intent(view.getContext(), DetailsActivity.class);
//                intent.putExtra(DetailsActivity.SELECTED_MOVIE_ID, movie.getId());
//                view.getContext().startActivity(intent);
            }
        });

        /** ----- log ----- */
        searchMovieViewHolder.cover.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                return onKeyListener.onItemClick(v, keyCode, event, holder.getLayoutPosition(), movie);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setOnKeyListener(OnKeyListener onKeyListener) {
        this.onKeyListener = onKeyListener;
    }
}
