package com.example.statesofindia;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;

import com.example.statesofindia.data.State;

public class StatePagingAdapter extends PagedListAdapter<State,StateViewHolder> {

    public StatePagingAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public StateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater mLayoutInflator = LayoutInflater.from(parent.getContext());
        View itemView = mLayoutInflator.inflate(R.layout.recyclerview_item,parent,false);
        return new StateViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StateViewHolder holder, int position) {
        final State currentState = getItem(position);
        if(currentState != null){
            holder.bind(currentState);
        }
    }

    private static DiffUtil.ItemCallback<State> DIFF_CALLBACK = new DiffUtil.ItemCallback<State>() {
        @Override
        public boolean areItemsTheSame(@NonNull State oldItem, @NonNull State newItem) {
            return oldItem.getStateName() == newItem.getStateName();
        }

        @Override
        public boolean areContentsTheSame(@NonNull State oldItem, @NonNull State newItem) {
            return oldItem.equals(newItem);
        }
    };

}
