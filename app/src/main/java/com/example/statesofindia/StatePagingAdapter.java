package com.example.statesofindia;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;

import com.example.statesofindia.data.State;

public class StatePagingAdapter extends PagedListAdapter<State,StateViewHolder> {

/*    private SwipeActionListener swipeActionListener = null;
    public static final Integer  SWIPE_LEFT = 121;
    public static final Integer SWIPE_RIGHT = 131;*/

    public ClickListener clickListener;

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
    public void onBindViewHolder(@NonNull StateViewHolder holder, final int position) {
        final State currentState = getItem(position);
        if (currentState != null) {
            holder.bind(currentState);

            if (clickListener != null){
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickListener.onItemClick(v,position);
                    }
                });
            }

        }
    }

    public State getStateAtPos(int pos)
    {
        return getItem(pos);
    }

    public void setItemOnClickListener(ClickListener clickListener){
        this.clickListener = clickListener;
    }
    public interface ClickListener{
        void onItemClick(View v,int position);
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
