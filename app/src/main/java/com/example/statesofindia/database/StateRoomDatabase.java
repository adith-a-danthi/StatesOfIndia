package com.example.statesofindia.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.statesofindia.data.State;

@Database(entities = {State.class}, version = 1, exportSchema = false)
public abstract class StateRoomDatabase extends RoomDatabase {

    public abstract StateDao stateDao();

    private  static StateRoomDatabase INSTANCE;

    public static StateRoomDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (StateRoomDatabase.class){
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            StateRoomDatabase.class,
                            "state_database"
                    ).build();
                }
            }
        }
        return INSTANCE;
    }

}
