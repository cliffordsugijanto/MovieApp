package com.test.movieapp.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.test.movieapp.databinding.ItemMovieBinding;
import com.test.movieapp.model.MovieListItem;
import com.test.movieapp.utils.GlideUtil;

import java.util.ArrayList;
import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieViewHolder> {
    List<MovieListItem> items;
    Activity context;
    OnMovieItemClickedListener listener;

    public interface OnMovieItemClickedListener{
        void onMovieItemClicked(MovieListItem item);
    }

    public MovieListAdapter(Activity context, OnMovieItemClickedListener listener) {
        this.listener = listener;
        this.context = context;
        items = new ArrayList<>();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new MovieViewHolder(ItemMovieBinding.inflate(inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        MovieListItem item = items.get(holder.getAdapterPosition());
        holder.binding.tvTitle.setText(item.getTitle());
        holder.binding.tvRating.setText(item.getVoteAverage()+"/10");
        GlideUtil.loadThumbnail(context,holder.binding.ivPoster,item);
        holder.binding.itemCard.setOnClickListener(new View.OnClickListener() {
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
        ItemMovieBinding binding;

        MovieViewHolder(ItemMovieBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
