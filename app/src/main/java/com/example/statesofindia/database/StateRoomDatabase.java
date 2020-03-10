package com.example.statesofindia.database;

import android.content.Context;
import android.telecom.Call;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.statesofindia.data.State;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {State.class}, version = 1, exportSchema = false)
public abstract class StateRoomDatabase extends RoomDatabase {

    public abstract StateDao stateDao();

    private static StateRoomDatabase INSTANCE;
    static ExecutorService executor = Executors.newSingleThreadExecutor();

    private static Callback callback =  new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    INSTANCE.stateDao().insertState(new State(null, "Test", "Test"));
                }
            });
        }
    };

    public static StateRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (StateRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            StateRoomDatabase.class,
                            "state_database"
                    ).addCallback(callback).fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }

}
