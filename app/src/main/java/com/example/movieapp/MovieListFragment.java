package com.example.movieapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.movieapp.databinding.FragmentMovieListBinding;
import java.util.ArrayList;
import java.util.List;

public class MovieListFragment extends Fragment {
    private FragmentMovieListBinding binding;
    private final List<Movie> movies = new ArrayList<>();
    @Nullable @Override public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=FragmentMovieListBinding.inflate(inflater, container, false);
        makeMovies();
        binding.movieRecycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.movieRecycler.setAdapter(new MovieAdapter(movies, this::openMovie));
        binding.menuButton.setOnClickListener(v -> {
            PopupMenu menu = new PopupMenu(requireContext(), binding.menuButton);
            menu.getMenu().add("Все фильмы"); menu.getMenu().add("Избранное"); menu.getMenu().add("О приложении");
            menu.setOnMenuItemClickListener(item -> { Toast.makeText(requireContext(), item.getTitle(), Toast.LENGTH_SHORT).show(); return true; });
            menu.show();
        });
        return binding.getRoot();
    }
    private void makeMovies() {
        if (!movies.isEmpty()) return;
        movies.add(new Movie("Fast & Furious Presents: Hobbs & Shaw","Movie","6.6","David Leitch","Lawman Luke Hobbs and outcast Deckard Shaw form an unlikely alliance when a cyber-genetically enhanced villain threatens the future.","6.5/10","60/100","H&S",R.drawable.bg_movie_blue));
        movies.add(new Movie("Once Upon a Time in Hollywood","Movie","9.4","Quentin Tarantino","Quentin Tarantino's Once Upon a Time in Hollywood visits 1969 Los Angeles, where everything is changing, as TV star Rick Dalton and his longtime stunt double Cliff Booth navigate a changing film industry.","7.6/10","83/100","1969",R.drawable.bg_movie_pink));
        movies.add(new Movie("Angel Has Fallen","Movie","7.1","Ric Roman Waugh","Secret Service agent Mike Banning is framed for an assassination attempt and must uncover the truth while escaping capture.","6.4/10","45/100","ANG",R.drawable.bg_movie_orange));
        movies.add(new Movie("BoJack Horseman: Season 6","TV Series","8.6","Raphael Bob-Waksberg","A washed-up sitcom star tries to make peace with his past while learning how to move forward.","8.8/10","93/100","BOJ",R.drawable.bg_movie_purple));
        movies.add(new Movie("Official Secrets","Movie","8.3","Gavin Hood","The true story of a British intelligence whistleblower who exposed a controversial operation before the Iraq War.","7.3/10","63/100","OS",R.drawable.bg_movie_green));
        movies.add(new Movie("Catherine the Great","TV Series","7.6","Philip Martin","A historical drama following Catherine the Great as she consolidates power and shapes the Russian Empire.","6.2/10","61/100","CG",R.drawable.bg_movie_pink));
    }
    private void openMovie(Movie movie) {
        MovieDetailFragment fragment=MovieDetailFragment.newInstance(movie);
        requireActivity().getSupportFragmentManager().beginTransaction()
            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
            .replace(R.id.fragmentContainer, fragment).addToBackStack("movie_detail").commit();
    }
    @Override public void onDestroyView() { super.onDestroyView(); binding=null; }
}
