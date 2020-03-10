package com.example.statesofindia.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.example.statesofindia.R;
import com.example.statesofindia.StateViewModel;
import com.example.statesofindia.data.State;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class NewStateActivity extends AppCompatActivity {

    private MaterialButton addBtn;
    StateViewModel mStateViewModel;
    private TextInputEditText stateNameEt, stateCapitalEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_state);

        mStateViewModel = new ViewModelProvider(this).get(StateViewModel.class);

        stateNameEt = findViewById(R.id.stateNameEt);
        stateCapitalEt = findViewById(R.id.stateCapitalEt);
        addBtn = findViewById(R.id.addBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String stateName = Objects.requireNonNull(stateNameEt.getText()).toString();
                    String stateCapital = Objects.requireNonNull(stateCapitalEt.getText()).toString();
                    State state = new State(stateName, stateCapital);
                    mStateViewModel.insertState(state);
                    setResult(RESULT_OK);
                    finish();
            }
        });

    }
}
