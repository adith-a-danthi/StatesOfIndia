package com.example.statesofindia.ui.fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.statesofindia.R;
import com.example.statesofindia.StatePagingAdapter;
import com.example.statesofindia.StateViewModel;
import com.example.statesofindia.data.State;
import com.example.statesofindia.ui.NewStateActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {

    private State currState;
    private static final int NEW_STATE_ACTIVITY_REQUEST_CODE = 1;
    private static final int UPDATE_STATE_ACTIVITY_REQUEST_CODE = 2;

    public static final String EXTRA_DATA_STATE_NAME = "extra_state_name_to_be_updated";
    public static final String EXTRA_DATA_STATE_CAPITAL = "extra_state_capital_to_be_updated";
    public static final String EXTRA_DATA_ID = "extra_data_id";

    private StateViewModel mStateViewModel;
    private SharedPreferences sortByPreference;

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottomNav);
        final CoordinatorLayout coordinatorLayout = requireActivity().findViewById(R.id.coordinator_layout);

        final NavController navController =
                Navigation.findNavController(requireActivity(), R.id.main_fragment);


        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        mStateViewModel = new ViewModelProvider(requireActivity()).get(StateViewModel.class);

        sortByPreference = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String s = sortByPreference.getString("list_preference_1", "mStateId");
        mStateViewModel.changeSortingOrder(s);

        RecyclerView recyclerView = requireActivity().findViewById(R.id.recyclerView);
        final StatePagingAdapter mStatePagingAdapter = new StatePagingAdapter();
        recyclerView.setAdapter(mStatePagingAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mStateViewModel.mAllStates.observe(getViewLifecycleOwner(), new Observer<PagedList<State>>() {
            @Override
            public void onChanged(PagedList<State> states) {
                mStatePagingAdapter.submitList(states);
            }
        });

        final Snackbar snackbar = Snackbar.make(coordinatorLayout, "State is Deleted!", Snackbar.LENGTH_LONG)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mStateViewModel.insertState(currState);
                    }
                });
        snackbar.setAnchorView(R.id.bottomNav);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                currState = mStatePagingAdapter.getStateAtPos(position);
                mStateViewModel.deleteState(currState);
                snackbar.show();
            }

        });


        sortByPreference.registerOnSharedPreferenceChangeListener(listener);

        itemTouchHelper.attachToRecyclerView(recyclerView);

        mStatePagingAdapter.setItemOnClickListener(new StatePagingAdapter.ClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                State currState = mStatePagingAdapter.getStateAtPos(position);
                launchUpdateStateActivity(currState);
            }
        });

        FloatingActionButton fab = requireActivity().findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Add new State", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), NewStateActivity.class);
                startActivityForResult(intent, NEW_STATE_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    private void launchUpdateStateActivity(State state) {
        Intent intent = new Intent(getActivity(), NewStateActivity.class);
        intent.putExtra(EXTRA_DATA_STATE_NAME, state.getStateName());
        intent.putExtra(EXTRA_DATA_STATE_CAPITAL, state.getCapital());
        intent.putExtra(EXTRA_DATA_ID, state.getStateId());
        startActivityForResult(intent, UPDATE_STATE_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void onStop() {
        sortByPreference.unregisterOnSharedPreferenceChangeListener(listener);
        super.onStop();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(getActivity(), "State Saved", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Result code not ok", Toast.LENGTH_SHORT).show();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private SharedPreferences.OnSharedPreferenceChangeListener listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if (key.equals("list_preference_1")) {
                String s = sharedPreferences.getString(key, "mStateId");
                mStateViewModel.changeSortingOrder(s);
            }
        }
    };

}
