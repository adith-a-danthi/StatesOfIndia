package com.example.statesofindia.database;

import android.content.Context;
import android.content.res.AssetManager;
import android.net.Uri;
import android.util.Log;

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
    static ExecutorService executor = Executors.newSingleThreadExecutor();

    private static Callback callback =  new Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            test();
        }

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
                    ).addCallback(new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            prePopulateDb(context.getAssets());
                        }
                    }).fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }

    private static void prePopulateDb(AssetManager assetManager){
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
            JSONArray statesAL = section.getJSONArray("States (A-L)");
            for (int i=0 ;i<statesAL.length(); i++) {
                JSONObject stateData = statesAL.getJSONObject(i);
                String stateName = stateData.getString("key");
                String stateCapital = stateData.getString("val");
                INSTANCE.stateDao().insertState(new State(0,stateName,stateCapital));
            }
        } catch (JSONException je){

        }

    }

    private static void test() {
        String location = "asset:///state-capital.json";
        Uri uri = Uri.parse(location);
        Log.d("test", "Uri: " + uri.getPath());
        //File file = new File(uri.getPath());
    }
}
