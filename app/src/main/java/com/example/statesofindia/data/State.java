package com.example.statesofindia.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "state")
public class State {

    @PrimaryKey(autoGenerate = true)
    private Integer mStateId;

    @NonNull
    @ColumnInfo(name = "state")
    private String mStateName;

    @NonNull
    @ColumnInfo(name = "capital")
    private String mCapital;

    public Integer getStateId() {
        return mStateId;
    }

    public State(Integer stateId, String stateName, @NonNull String capital) {
        this.mStateId = stateId;
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

    public void setStateName(String mStateName) {
        this.mStateName = mStateName;
    }

    public void setCapital(@NonNull String mCapital) {
        this.mCapital = mCapital;
    }

    public boolean equals(State s2) {
        return (mStateName == s2.getStateName() && mCapital == s2.getCapital());
    }

}
