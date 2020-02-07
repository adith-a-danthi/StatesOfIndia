package com.example.statesofindia;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.statesofindia.data.State;

public class StateViewModel extends AndroidViewModel {

    private int PAGE_SIZE = 15;
    private StateRepository mRepository;
    private DataSource.Factory<Integer, State> mAllStates;

    public StateViewModel(@NonNull Application application) {
        super(application);
        mRepository = new StateRepository(application);
        mAllStates = mRepository.getAllPagedStates();
    }

    public void insertState(State state){
        mRepository.insertState(state);
    }

    public void deleteAll(){
        mRepository.deleteAll();
    }

    public void updateState(State state){
        mRepository.updateState(state);
    }

    public LiveData<PagedList<State>> getAllPagedStates(){
        LiveData<PagedList<State>> mPagedList =
                new LivePagedListBuilder<>(
                        mAllStates,
                        PAGE_SIZE
                ).build();
        return mPagedList;
    }

    public void deleteState(State state){
        mRepository.deleteState(state);
    }

}
