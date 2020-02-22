package com.example.statesofindia.ui.fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.statesofindia.R;
import com.example.statesofindia.StatePagingAdapter;
import com.example.statesofindia.StateViewModel;
import com.example.statesofindia.data.State;
import com.example.statesofindia.ui.NewStateActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {

    public ListFragment() {
        // Required empty public constructor
    }

    private StateViewModel mStateViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottomNav);

        FloatingActionButton fab = getActivity().findViewById(R.id.floatingActionButton);

        final NavController navController =
                Navigation.findNavController(requireActivity(),R.id.main_fragment);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),NewStateActivity.class);
                startActivity(intent);
            }
        });

        NavigationUI.setupWithNavController(bottomNavigationView,navController);

        mStateViewModel =new ViewModelProvider(getActivity()).get(StateViewModel.class);

        RecyclerView recyclerView = getActivity().findViewById(R.id.recyclerView);
        final StatePagingAdapter mStatePagingAdapter = new StatePagingAdapter();
        recyclerView.setAdapter(mStatePagingAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mStateViewModel.getAllPagedStates().observe(getViewLifecycleOwner(), new Observer<PagedList<State>>() {
            @Override
            public void onChanged(PagedList<State> states) {
                mStatePagingAdapter.submitList(states);
            }
        });

    }



}
