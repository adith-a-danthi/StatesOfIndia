package com.example.statesofindia.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "state")
public class State {

    @PrimaryKey
    private String mStateName;

    @NonNull
    @ColumnInfo(name = "capital")
    private String mCapital;

    public State(String stateName,@NonNull String capital){
        this.mStateName = stateName;
        this.mCapital = capital;
    }

    public String getStateName() {
        return mStateName;
    }

    @NonNull
    public String getCapital() {
        return mCapital;
    }

    public void setmStateName(String mStateName) {
        this.mStateName = mStateName;
    }

    public void setmCapital(@NonNull String mCapital) {
        this.mCapital = mCapital;
    }
}
