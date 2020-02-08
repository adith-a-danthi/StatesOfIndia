package com.example.statesofindia;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.statesofindia.data.State;

public class StateViewHolder extends RecyclerView.ViewHolder {

    public CardView rootItem;
    private final TextView stateNameView,stateCapitalView;

    public StateViewHolder(@NonNull View itemView) {
        super(itemView);
        stateNameView = itemView.findViewById(R.id.state_nameTv);
        stateCapitalView = itemView.findViewById(R.id.state_capitalTv);
        rootItem = itemView.findViewById(R.id.rootItem);
    }

    public void bind(State state){
        stateNameView.setText(state.getStateName());
        stateCapitalView.setText(state.getCapital());
    }

}
