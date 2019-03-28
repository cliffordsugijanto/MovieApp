package com.test.movieapp.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.movieapp.databinding.ItemSearchBinding;
import com.test.movieapp.model.MovieListItem;
import com.test.movieapp.utils.GlideUtil;

import java.util.ArrayList;
import java.util.List;

public class MovieSearchAdapter extends RecyclerView.Adapter<MovieSearchAdapter.MovieViewHolder> {
    List<MovieListItem> items;
    Activity context;
    OnMovieItemClickedListener listener;

    public interface OnMovieItemClickedListener{
        void onMovieItemClicked(MovieListItem item);
    }

    public MovieSearchAdapter(Activity context, List<MovieListItem> items, OnMovieItemClickedListener listener) {
        this.listener = listener;
        this.context = context;
        this.items = items;
    }

    @Override
    public MovieSearchAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new MovieSearchAdapter.MovieViewHolder(ItemSearchBinding.inflate(inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(MovieSearchAdapter.MovieViewHolder holder, int position) {
        MovieListItem item = items.get(holder.getAdapterPosition());
        holder.binding.tvTitle.setText(item.getTitle());
        holder.binding.tvRating.setText(item.getVoteAverage()+"/10");
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onMovieItemClicked(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }


    public void addData(List<MovieListItem> results) {
        items.addAll(results);
        notifyDataSetChanged();
    }

    public void refreshData(List<MovieListItem> movies) {
        items.clear();
        if (movies != null && movies.size() > 0) {
            items.addAll(movies);
        }
        notifyDataSetChanged();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        ItemSearchBinding binding;

        MovieViewHolder(ItemSearchBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
