package com.example.statesofindia.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import com.example.statesofindia.R;
import com.example.statesofindia.StateViewModel;
import com.example.statesofindia.data.State;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import static com.example.statesofindia.ui.fragments.ListFragment.EXTRA_DATA_ID;
import static com.example.statesofindia.ui.fragments.ListFragment.EXTRA_DATA_STATE_CAPITAL;
import static com.example.statesofindia.ui.fragments.ListFragment.EXTRA_DATA_STATE_NAME;

public class NewStateActivity extends AppCompatActivity {

    StateViewModel mStateViewModel;
    private TextInputEditText stateNameEt, stateCapitalEt;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_state);

        stateNameEt = findViewById(R.id.stateNameEt);
        stateCapitalEt = findViewById(R.id.stateCapitalEt);
        MaterialButton addBtn = findViewById(R.id.addBtn);

        final Bundle extras = getIntent().getExtras();

        if(extras != null){
            String stateName = extras.getString(EXTRA_DATA_STATE_NAME, "");
            if(!stateName.isEmpty()){
                stateNameEt.setText(stateName);
//                stateNameEt.setSelection(stateName.length());
            }
            String stateCapital = extras.getString(EXTRA_DATA_STATE_CAPITAL, "");
            if(!stateName.isEmpty()){
                stateCapitalEt.setText(stateCapital);
                stateCapitalEt.setSelection(stateCapital.length());
                stateCapitalEt.requestFocus();
            }
            addBtn.setText("Save");
        }

        mStateViewModel = new ViewModelProvider(this).get(StateViewModel.class);


        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stateName = Objects.requireNonNull(stateNameEt.getText()).toString();
                String stateCapital = Objects.requireNonNull(stateCapitalEt.getText()).toString();

                if (extras != null && extras.containsKey(EXTRA_DATA_ID)){
                    Integer stateId = extras.getInt(EXTRA_DATA_ID, -1);
                    State state = new State(stateId, stateName, stateCapital);
                    mStateViewModel.updateState(state);
                } else {
                    State state = new State(stateName, stateCapital);
                    mStateViewModel.insertState(state);
                }
                setResult(RESULT_OK);
                finish();
            }
        });

    }
}
