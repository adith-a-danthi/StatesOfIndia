package com.example.statesofindia.ui.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.statesofindia.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;


/**
 * A simple {@link Fragment} subclass.
 */
public class StatsFragment extends Fragment {

    private  static final String TOTAL_GAME_KEY = "total_games";
    private static final String WON_GAME_KEY = "wins";

    public StatsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stats, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottomNav);

        NavController navController =
                Navigation.findNavController(requireActivity(),R.id.main_fragment);

        NavigationUI.setupWithNavController(bottomNavigationView,navController);

        SharedPreferences quizScore = requireActivity().getSharedPreferences("stats",getContext().MODE_PRIVATE);
        int gamesTotal = quizScore.getInt(TOTAL_GAME_KEY, 0);
        int winsTotal = quizScore.getInt(WON_GAME_KEY,0);

        final TextView gamesCount = requireActivity().findViewById(R.id.total_Games_Number);
        final TextView winsCount = requireActivity().findViewById(R.id.total_wins_Number);
        String games = "" + gamesTotal;
        String wins = "" + winsTotal;
        gamesCount.setText(games);
        winsCount.setText(wins);
        final SharedPreferences.Editor editor = quizScore.edit();
        Button resetBtn = requireActivity().findViewById(R.id.reset_stats);
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt(TOTAL_GAME_KEY,0);
                editor.putInt(WON_GAME_KEY,0);
                editor.apply();
                gamesCount.setText("0");
                winsCount.setText("0");
            }
        });

    }

}
