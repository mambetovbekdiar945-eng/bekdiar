package com.example.movieapp;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.movieapp.databinding.ItemMovieBinding;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {
    public interface OnMovieClick { void onClick(Movie movie); }
    private final List<Movie> movies; private final OnMovieClick listener;
    public MovieAdapter(List<Movie> movies, OnMovieClick listener) { this.movies=movies; this.listener=listener; }
    @NonNull @Override public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MovieHolder(ItemMovieBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }
    @Override public void onBindViewHolder(@NonNull MovieHolder holder, int position) {
        Movie movie=movies.get(position); holder.binding.movieTitle.setText(movie.title); holder.binding.movieType.setText(movie.type);
        holder.binding.movieRating.setText(movie.rating); holder.binding.posterInitial.setText(movie.initials);
        holder.binding.movieCard.setBackgroundResource(movie.background);
        holder.binding.movieCard.setOnClickListener(v -> listener.onClick(movie));
    }
    @Override public int getItemCount() { return movies.size(); }
    static class MovieHolder extends RecyclerView.ViewHolder {
        final ItemMovieBinding binding;
        MovieHolder(ItemMovieBinding binding) { super(binding.getRoot()); this.binding=binding; }
    }
}
