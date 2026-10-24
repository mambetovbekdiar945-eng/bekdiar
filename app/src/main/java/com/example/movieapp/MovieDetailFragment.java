package com.example.movieapp;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;
import androidx.media3.exoplayer.ExoPlayer;
import com.example.movieapp.databinding.FragmentMovieDetailBinding;

public class MovieDetailFragment extends Fragment {
    private static final String ARG_TITLE="title", ARG_TYPE="type", ARG_RATING="rating", ARG_DIRECTOR="director", ARG_OVERVIEW="overview", ARG_IMDB="imdb", ARG_META="meta", ARG_INITIALS="initials";
    private FragmentMovieDetailBinding binding; private ExoPlayer player; private boolean expanded=false, favorite=false; private String overview;
    public static MovieDetailFragment newInstance(Movie movie) {
        MovieDetailFragment f=new MovieDetailFragment(); Bundle b=new Bundle();
        b.putString(ARG_TITLE,movie.title); b.putString(ARG_TYPE,movie.type); b.putString(ARG_RATING,movie.rating); b.putString(ARG_DIRECTOR,movie.director);
        b.putString(ARG_OVERVIEW,movie.overview); b.putString(ARG_IMDB,movie.imdb); b.putString(ARG_META,movie.metacritic); b.putString(ARG_INITIALS,movie.initials); f.setArguments(b); return f;
    }
    @Nullable @Override public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=FragmentMovieDetailBinding.inflate(inflater,container,false); Bundle a=getArguments();
        if (a!=null) {
            binding.detailTitle.setText(a.getString(ARG_TITLE)); binding.detailDirector.setText("by "+a.getString(ARG_DIRECTOR));
            binding.detailRating.setText(a.getString(ARG_RATING)); binding.detailPosterInitial.setText(a.getString(ARG_INITIALS));
            binding.imdbRating.setText(a.getString(ARG_IMDB)); binding.metacriticRating.setText(a.getString(ARG_META)); overview=a.getString(ARG_OVERVIEW,"No overview available.");
            binding.detailOverview.setText(overview);
        }
        addActors();
        binding.backButton.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());
        binding.favoriteButton.setOnClickListener(v -> { favorite=!favorite; binding.favoriteButton.setImageResource(favorite?R.drawable.ic_star:R.drawable.ic_star_outline); Toast.makeText(requireContext(), favorite?"Добавлено в избранное":"Удалено из избранного",Toast.LENGTH_SHORT).show(); });
        binding.readMoreButton.setOnClickListener(v -> { expanded=!expanded; binding.detailOverview.setMaxLines(expanded?100:4); binding.readMoreButton.setText(expanded?"read less":"read all"); });
        binding.trailerOverlay.setOnClickListener(v -> startTrailer());
        return binding.getRoot();
    }
    private void addActors() {
        String[] names={"Leo","Brad","Margot","Quentin","Al","Kurt"}; int[] colors={0xFF5E7899,0xFF95705D,0xFFBD7D9A,0xFF675B7E,0xFFB68D57,0xFF517C76};
        for(int i=0;i<names.length;i++) { TextView t=new TextView(requireContext()); t.setText(names[i]); t.setTextColor(Color.WHITE); t.setTextSize(11); t.setGravity(17); t.setBackgroundTintList(android.content.res.ColorStateList.valueOf(colors[i]));
            android.graphics.drawable.GradientDrawable circle=new android.graphics.drawable.GradientDrawable(); circle.setShape(android.graphics.drawable.GradientDrawable.OVAL); circle.setColor(colors[i]); t.setBackground(circle);
            android.widget.LinearLayout.LayoutParams lp=new android.widget.LinearLayout.LayoutParams(dp(58),dp(58)); lp.setMargins(0,0,dp(10),0); t.setLayoutParams(lp); binding.actorsContainer.addView(t); }
    }
    private int dp(int value) { return (int)(value*getResources().getDisplayMetrics().density+0.5f); }
    private void startTrailer() {
        if (player!=null) { player.play(); binding.trailerOverlay.setVisibility(View.GONE); binding.playerView.setVisibility(View.VISIBLE); return; }
        player=new ExoPlayer.Builder(requireContext()).build(); binding.playerView.setPlayer(player);
        // Sample MP4 used to demonstrate a working in-app player. Replace with a licensed trailer URL if needed.
        MediaItem item=MediaItem.fromUri("https://storage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4");
        player.setMediaItem(item); player.prepare(); player.play(); binding.trailerOverlay.setVisibility(View.GONE); binding.playerView.setVisibility(View.VISIBLE);
        player.addListener(new Player.Listener() { @Override public void onPlayerError(androidx.media3.common.PlaybackException error) { if (isAdded()) Toast.makeText(requireContext(),"Не удалось загрузить видео. Проверь интернет-соединение.",Toast.LENGTH_LONG).show(); } });
    }
    @Override public void onStop() { super.onStop(); if(player!=null) player.pause(); }
    @Override public void onDestroyView() { if(player!=null){ player.release(); player=null; } super.onDestroyView(); binding=null; }
}
