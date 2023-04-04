package com.yuanren.tvinteractions.view.search;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yuanren.tvinteractions.R;
import com.yuanren.tvinteractions.model.Movie;
import com.yuanren.tvinteractions.view.movie_details.DetailsActivity;

import java.util.List;

public class SearchListAdapter extends RecyclerView.Adapter {

    private List<Movie> data;

    public SearchListAdapter(List<Movie> data) {
        this.data = data;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_movie_card, parent, false);
        return new SearchMovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Movie movie = data.get(position);
        SearchMovieViewHolder searchMovieViewHolder = (SearchMovieViewHolder) holder;
        Glide.with(holder.itemView.getContext())
                .load(movie.getCardImageUrl())
                .centerCrop()
                .into(searchMovieViewHolder.image);

        searchMovieViewHolder.cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailsActivity.class);
                intent.putExtra(DetailsActivity.SELECTED_MOVIE_ID, movie.getId());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
