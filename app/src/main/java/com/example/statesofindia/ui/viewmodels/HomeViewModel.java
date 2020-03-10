package com.example.statesofindia.ui.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.statesofindia.StateRepository;
import com.example.statesofindia.data.State;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class HomeViewModel extends AndroidViewModel {

    private StateRepository stateRepository;
    public MutableLiveData<List<State>> quizData = new MutableLiveData<>();

    public HomeViewModel(@NonNull Application application, Integer x) {
        super(application);
        stateRepository = StateRepository.getRepository(application);
        loadGame();
    }

    private void loadGame()
    {
        try {
            quizData.postValue(stateRepository.getQuizStates().get());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void refreshGame() {
        loadGame();
    }

}
