package com.example.statesofindia;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.PagedList;

import com.example.statesofindia.data.State;

public class StateViewModel extends AndroidViewModel {

    private int PAGE_SIZE = 15;
    private StateRepository mRepository;
    public LiveData<PagedList<State>> mAllStates;

    private MutableLiveData<String> sortOrderChanged = new MutableLiveData<>();



    public StateViewModel(@NonNull Application application) {
        super(application);
        mRepository =  StateRepository.getRepository(application);
        sortOrderChanged.setValue("mStateId");
        mAllStates = Transformations.switchMap(sortOrderChanged, new Function<String, LiveData<PagedList<State>>>() {
            @Override
            public LiveData<PagedList<State>> apply(String input) {
                return mRepository.getAllPagedStates(input);
            }
        });
    }

    public void changeSortingOrder(String sortBy) {
        sortOrderChanged.postValue(sortBy);
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

    public void deleteState(State state){
        mRepository.deleteState(state);
    }

    public State getState(Integer stateId){
        return mRepository.getState(stateId);
    }

}
