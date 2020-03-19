package com.example.statesofindia.database;

import android.content.Context;
import android.content.res.AssetManager;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.statesofindia.data.State;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {State.class}, version = 1, exportSchema = false)
public abstract class StateRoomDatabase extends RoomDatabase {

    public abstract StateDao stateDao();

    private static StateRoomDatabase INSTANCE;
    private static ExecutorService executor = Executors.newSingleThreadExecutor();

    public static StateRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (StateRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            StateRoomDatabase.class,
                            "state_database"
                    ).addCallback(new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            executor.execute(new Runnable() {
                                @Override
                                public void run() {
                                    prePopulateDb(context.getAssets(), INSTANCE.stateDao());
                                }
                            });
                        }
                    }).fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }

    //Read the states data in JSON file and prepopulate the RoomDB
    private static void prePopulateDb(AssetManager assetManager, StateDao stateDao){
        BufferedReader reader = null;
        StringBuilder stringBuilder = new StringBuilder();
        String json = "";
        try {
            reader = new BufferedReader(
                    new InputStreamReader(assetManager.open("state-capital.json")));

            // do reading, usually loop until end of file reading
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                //process line
                stringBuilder.append(mLine);
            }
            json = stringBuilder.toString();
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
        try{
            JSONObject states = new JSONObject(json);
            JSONObject section = states.getJSONObject("sections");
            populateFromJSON(section.getJSONArray("States (A-L)"), stateDao);
            populateFromJSON(section.getJSONArray("States (M-Z)"), stateDao);
            populateFromJSON(section.getJSONArray("Union Territories"), stateDao);
        } catch (JSONException ignored){}
    }

    private static void populateFromJSON(JSONArray states, StateDao stateDao){
        try{
            for (int i=0 ;i<states.length(); i++) {
                JSONObject stateData = states.getJSONObject(i);
                String stateName = stateData.getString("key");
                String stateCapital = stateData.getString("val");
                stateDao.insertState(new State(null,stateName,stateCapital));
            }
        }catch (JSONException ignored){}
    }

}
