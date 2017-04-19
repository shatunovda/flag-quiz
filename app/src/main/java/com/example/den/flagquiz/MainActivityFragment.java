package com.example.den.flagquiz;

import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainActivityFragment extends Fragment {

    private static final String TAG = "FlagQuiz Activity";
    private static final int FLAGS_IN_QUIZ = 10;

    private List<String> fileNameList;
    private List<String> quizCountriesList;
    private Set<String> regionSet;
    private String correctAnswer;
    private int totalGuesses;
    private int correctAnswers;
    private int guessRows;
    private SecureRandom random;
    private Handler handler;
    private Animation shakeAnimation;

    private LinearLayout quizLinearLayout;
    private TextView questionNumberTextView;
    private ImageView flagImageView;
    private LinearLayout[] guessLinearLayouts;
    private TextView answerTextView;


    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        fileNameList = new ArrayList<>();
        quizCountriesList = new ArrayList<>();
        random = new SecureRandom();
        handler = new Handler();

        shakeAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.incorrect_shake);
        shakeAnimation.setRepeatCount(3);

        quizLinearLayout = (LinearLayout) view.findViewById(R.id.quizLinearLayout);
        questionNumberTextView = (TextView) view.findViewById(R.id.questionNumberTextView);
        flagImageView =(ImageView) view.findViewById(R.id.flagImageView);

        guessLinearLayouts = new LinearLayout[4];
        guessLinearLayouts[0] = (LinearLayout) view.findViewById(R.id.row1LinearLayout);
        guessLinearLayouts[1] = (LinearLayout) view.findViewById(R.id.row2LinearLayout);
        guessLinearLayouts[2] = (LinearLayout) view.findViewById(R.id.row3LinearLayout);
        guessLinearLayouts[3] = (LinearLayout) view.findViewById(R.id.row4LinearLayout);

        answerTextView = (TextView) view.findViewById(R.id.answerTextView);

        for (LinearLayout row : guessLinearLayouts) {
            for (int column = 0; column < row.getChildCount(); column++) {
                Button button = (Button) row.getChildAt(column);
                button.setOnClickListener(guessButtonListener);
            }
        }

        questionNumberTextView.setText(getString(R.string.question, 1, FLAGS_IN_QUIZ));
        return view;
    }

    public void updateGuessRows(SharedPreferences sharedPreferences) {
        String choices = sharedPreferences.getString(MainActivity.CHOICES, null);
        guessRows = Integer.parseInt(choices) / 2;
        for (LinearLayout layout : guessLinearLayouts) {
            layout.setVisibility(View.GONE);
        }
        for (int row = 0; row < guessRows; row++) {
            guessLinearLayouts[row].setVisibility(View.VISIBLE);
        }
    }

    public void updateRegion(SharedPreferences defaultSharedPreferences) {
    }

    public void resetQuiz() {
    }
}
