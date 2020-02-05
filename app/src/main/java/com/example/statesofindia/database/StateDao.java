package com.example.statesofindia.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.statesofindia.data.State;

import androidx.paging.DataSource;
import androidx.room.Update;

@Dao
public interface StateDao {

    @Insert
    void insert(State state);

    @Query("SELECT * FROM state ORDER BY mStateName ASC")
    DataSource.Factory<Integer,State> getAllPagedStates();

    @Query("DELETE FROM state")
    void deleteAll();

    @Delete
    void deleteState(State state);

    @Update
    void updateState(State state);

}
