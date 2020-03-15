package com.example.statesofindia;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;

import com.example.statesofindia.data.State;

public class StatePagingAdapter extends PagedListAdapter<State,StateViewHolder> {

/*    private SwipeActionListener swipeActionListener = null;
    public static final Integer  SWIPE_LEFT = 121;
    public static final Integer SWIPE_RIGHT = 131;*/

    public StatePagingAdapter() {
        super(DIFF_CALLBACK);
    }

/*   public interface SwipeActionListener {
        void swipedState(State state, Integer direction);
   }

   public void setSwipeActionListener(SwipeActionListener swipeActionListener)
   {
       this.swipeActionListener = swipeActionListener;
   }*/

    @NonNull
    @Override
    public StateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater mLayoutInflator = LayoutInflater.from(parent.getContext());
        View itemView = mLayoutInflator.inflate(R.layout.recyclerview_item,parent,false);
        return new StateViewHolder(itemView);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull StateViewHolder holder, int position) {
        final State currentState = getItem(position);
        if (currentState != null) {
            holder.bind(currentState);

            /*if (swipeActionListener != null) {
                holder.rootItem.setOnTouchListener(new SwipeDetector(holder.itemView.getContext()) {
                    @SuppressLint("ClickableViewAccessibility")
                    public void onSwipeLeft() {
                        swipeActionListener.swipedState(currentState, SWIPE_LEFT);
                    }
                    public void onSwipeRight() {
                        swipeActionListener.swipedState(currentState, SWIPE_RIGHT);
                    }
                });

                *//*holder.rootItem.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        swipeActionListener.swipedState(currentState, SWIPE_LEFT);
                        return true;
                    }
                });*//*

            }*/
        }
    }

    public State getStateAtPos(int pos)
    {
        return getItem(pos);
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
