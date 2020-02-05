package com.example.statesofindia;

import android.app.Application;
import android.os.AsyncTask;

import androidx.paging.DataSource;

import com.example.statesofindia.data.State;
import com.example.statesofindia.database.StateDao;
import com.example.statesofindia.database.StateRoomDatabase;

public class StateRepository {

    private StateDao mStateDao;
    private DataSource.Factory<Integer,State> mAllStates;
//    private LiveData<List<State>> mAllStates;

    public StateRepository(Application application){
        StateRoomDatabase db = StateRoomDatabase.getDatabase(application);
        mStateDao = db.stateDao();
        mAllStates = mStateDao.getAllPagedStates();
    }

    public DataSource.Factory<Integer,State> getAllPagedStates(){
        return mStateDao.getAllPagedStates();
    }

    public void insert (State state){
        new insertAsyncTask(mStateDao, state).execute();
    }

    public void updateState(State state){
        new updateAsyncTask(mStateDao).execute(state);
    }

    public void deleteState(State state){
        new deleteStateAsyncTask(mStateDao).execute(state);
    }

    public void deleteAll(){
        new deleteAllAsyncTask(mStateDao).execute();
    }

    private class updateAsyncTask extends AsyncTask<State,Void,Void>{

        private StateDao mAsyncTaskDao;

        public updateAsyncTask(StateDao dao) {
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(State... states) {
            mAsyncTaskDao.updateState(states[0]);
            return null;
        }
    }

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

    private class deleteStateAsyncTask extends AsyncTask<State,Void,Void>{

        private StateDao mAsyncTaskDao;

        public deleteStateAsyncTask(StateDao mAsyncTaskDao) {
            this.mAsyncTaskDao = mAsyncTaskDao;
        }

        @Override
        protected Void doInBackground(State... states) {
            mAsyncTaskDao.deleteState(states[0]);
            return null;
        }
    }

    private class deleteAllAsyncTask extends AsyncTask<Void,Void,Void>{

        private StateDao mAsyncTaskDao;

        public deleteAllAsyncTask(StateDao mAsyncTaskDao) {
            this.mAsyncTaskDao = mAsyncTaskDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

}

