package com.example.statesofindia.ui.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.statesofindia.R;
import com.example.statesofindia.data.State;
import com.example.statesofindia.ui.custom.QuizView;
import com.example.statesofindia.ui.viewmodels.HomeViewModel;
import com.example.statesofindia.ui.viewmodels.HomeViewModelFactory;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;
import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private QuizView quizView;
    private SharedPreferences quizScore;

    private  static final String TOTAL_GAME_KEY = "total_games";
    private static final String WON_GAME_KEY = "wins";

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);

        homeViewModel = (new ViewModelProvider(this,new HomeViewModelFactory(getActivity().getApplication(),1))).get(HomeViewModel.class);
        quizView = getActivity().findViewById(R.id.quiz_view);

        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottomNav);

        final NavController navController =
                Navigation.findNavController(requireActivity(),R.id.main_fragment);

        NavigationUI.setupWithNavController(bottomNavigationView,navController);

        quizScore = requireActivity().getSharedPreferences("stats", Context.MODE_PRIVATE);

        homeViewModel.quizData.observe(getActivity(), new Observer<List<State>>() {
            @Override
            public void onChanged(List<State> states) {
                if(states != null){
                    if (states.size() == 4) {
                        quizView.setData(states);
                    } else {
                        Toast.makeText(getActivity(), "Add More states", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        quizView.setOptionsClickListener(new QuizView.OptionsClickListener() {
            @Override
            public void optionClicked(Boolean correctAnswer) {
                updateResult(correctAnswer);
            }
        });
    }

    private void updateResult(Boolean result){
        int currentTotal = quizScore.getInt(TOTAL_GAME_KEY,  0);
        int wins = quizScore.getInt(WON_GAME_KEY, 0);

        SharedPreferences.Editor editor = quizScore.edit();

        if (result){
            wins++;
            Toast.makeText(getActivity(), "Correct Answer", Toast.LENGTH_SHORT).show();
            editor.putInt(WON_GAME_KEY, wins);
        } else {
            Toast.makeText(getActivity(), "Wrong Answer", Toast.LENGTH_SHORT).show();
        }
        homeViewModel.refreshGame();
        quizView.reset();

        currentTotal++;

        editor.putInt(TOTAL_GAME_KEY, currentTotal);
        editor.apply();
    }

}
