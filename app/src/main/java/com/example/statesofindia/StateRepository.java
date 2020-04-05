package com.example.statesofindia;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.preference.PreferenceManager;
import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.example.statesofindia.data.State;
import com.example.statesofindia.database.StateDao;
import com.example.statesofindia.database.StateRoomDatabase;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class StateRepository {

    private static StateRepository REPOSITORY = null;

    private StateDao mStateDao;
    ExecutorService executor = Executors.newSingleThreadExecutor();
    private SharedPreferences preferences;
    private int PAGE_SIZE = 15;
//    private LiveData<List<State>> mAllStates;

    private StateRepository(Application application){
        StateRoomDatabase db = StateRoomDatabase.getDatabase(application);
        mStateDao = db.stateDao();
        preferences = PreferenceManager.getDefaultSharedPreferences(application);
    }

    public static StateRepository getRepository(Application application)
    {
        if (REPOSITORY == null) {
            synchronized (StateRepository.class){
                if (REPOSITORY == null){
                    REPOSITORY = new StateRepository(application);
                }
            }
        }
        return REPOSITORY;
    }

    public LiveData<PagedList<State>> getAllPagedStates(String sortBy){
        LiveData<PagedList<State>> mPagedList =
                new LivePagedListBuilder<>(
                        mStateDao.getAllStates(constructQuery(sortBy)),
                        PAGE_SIZE
                ).build();
        return mPagedList;
    }


    public void insertState (final State state){
// Alternate Method - easier in kotlin
        executeInThread(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                mStateDao.insertState(state);
                return null;
            }
        });

//        new insertAsyncTask(mStateDao, state).execute();
    }

    private void executeInThread(final Callable<Void> func){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    func.call();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void updateState(final State state){
// Easier in java
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mStateDao.updateState(state);
            }
        });
//        new updateAsyncTask(mStateDao).execute(state);
    }

    public void deleteState(final State state){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mStateDao.deleteState(state);
            }
        });
    }

    public void deleteAll(){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mStateDao.deleteAll();
            }
        });
    }

    public State getState(final int stateId){
        return mStateDao.getState(stateId);
    }

    @WorkerThread
    public State getRandomState(){
        return mStateDao.getRandomState();
    }

    public Future<List<State>> getQuizStates(){
        Callable<List<State>> callable = new Callable<List<State>>() {
            @Override
            public List<State> call() {
                return mStateDao.getQuizStates();
            }
        };
        return executor.submit(callable);
    }

/*

        private class insertAsyncTask extends AsyncTask<Void,Void,Void>{

        private StateDao mAsyncTaskDao;
        private State state;

        insertAsyncTask(StateDao dao, State state){
            mAsyncTaskDao = dao;
            this.state = state;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.insert(state);
            return null;
        }
    }
*/

    private SupportSQLiteQuery constructQuery(String sortBy) {
        String query = "SELECT * FROM state ORDER BY " + sortBy + " ASC";
        return new SimpleSQLiteQuery(query);
    }

}

