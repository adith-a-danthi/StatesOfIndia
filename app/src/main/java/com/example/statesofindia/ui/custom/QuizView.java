package com.example.statesofindia.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.statesofindia.data.State;

import java.util.List;
import java.util.Random;

public class QuizView extends LinearLayout {

    private State correctState;
    private RadioGroup optionsRadio;
    private Integer correctOptionId;

    private OptionsClickListener optionsClickListener = null;

    public QuizView(Context context) {
        super(context);
        initRadios();
    }

    public QuizView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initRadios();
    }

    public interface OptionsClickListener{
        void optionClicked(Boolean correctAnswer);
    }

    public void setOptionsClickListener(OptionsClickListener optionsClickListener)
    {
        this.optionsClickListener = optionsClickListener;
    }

    private void initRadios()
    {
        optionsRadio = new RadioGroup(getContext());
        optionsRadio.setId(View.generateViewId());

    }

    public void setData(List<State> states)
    {
        Random random = new Random(System.currentTimeMillis());
        int correctOption = random.nextInt(4);

        correctState = states.get(correctOption);

        TextView questionTv = new TextView(getContext());
        String question = "What is the capital of " + correctState.getStateName();
        questionTv.setText(question);
        this.addView(questionTv);

        this.addView(optionsRadio);

        RadioButton[] radios = new RadioButton[4];


        radios[correctOption] = new RadioButton(getContext());
        radios[correctOption].setId(View.generateViewId());
        radios[correctOption].setText(correctState.getCapital());
        correctOptionId = radios[correctOption].getId();
        int j = 0;
        for (int i = 0; i<4;i++){
            if (i == correctOption) {
                optionsRadio.addView(radios[correctOption]);
                continue;
            }
            radios[i] = new RadioButton(getContext());
            radios[i].setId(View.generateViewId());
            radios[i].setText(states.get(j++).getCapital());
            optionsRadio.addView(radios[i]);
        }
        initListener();
    }

    private void initListener()
    {
        optionsRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (optionsClickListener != null) {
                    if (checkedId == correctOptionId) {
                        optionsClickListener.optionClicked(true);
                    } else {
                        optionsClickListener.optionClicked(false);
                    }
                }
            }
        });
    }

    public void reset()
    {
        optionsRadio.removeAllViews();
        this.removeAllViews();
    }
}
