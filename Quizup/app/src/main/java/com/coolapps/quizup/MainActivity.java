package com.coolapps.quizup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.coolapps.quizup.data.AsyncQuestionTask;
import com.coolapps.quizup.data.Repository;
import com.coolapps.quizup.databinding.ActivityMainBinding;
import com.coolapps.quizup.model.Question;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ArrayList<Question> questionArrayList;
    private int currentQuestionIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        questionArrayList = new Repository().getQuestionList(questionList -> {binding.questionTextView
                .setText(questionList.get(currentQuestionIndex).getQuestion());
                updateCounter();
                });


        updateCounter();

        binding.buttonTrue.setOnClickListener(v -> {
            checkAnswer(true);
            updateQuestion();
        });

        binding.buttonFalse.setOnClickListener(v -> {
            checkAnswer(false);
            updateQuestion();
        });

        binding.buttonNext.setOnClickListener(v -> {
            currentQuestionIndex = (currentQuestionIndex + 1) % questionArrayList.size();
            updateQuestion();
        });



    }

    private void updateQuestion() {
        if (questionArrayList.size() != 0) {
            binding.questionTextView.setText(questionArrayList.get(currentQuestionIndex).getQuestion());
            updateCounter();
        }
        else
            Snackbar.make(binding.cardView,R.string.question_not_loaded,Snackbar.LENGTH_SHORT).show();
    }

    private void updateCounter() {
        binding.questionCounter.setText(String.format(" %d/%d", currentQuestionIndex, questionArrayList.size()));
    }

    private void checkAnswer(boolean userChoice) {
        int msgId;
        if (questionArrayList.size() != 0) {
            boolean correctAnswer = questionArrayList.get(currentQuestionIndex).getTrue();
            if (userChoice == correctAnswer){
                msgId = R.string.correct;
                fadeAnimation();
            }
            else{
                msgId = R.string.wrong;
                shakeAnimation();
            }

        }
        else
            msgId = R.string.question_not_loaded;
        Snackbar.make(binding.cardView,msgId,Snackbar.LENGTH_SHORT).show();

    }

    private void fadeAnimation() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(300);
        alphaAnimation.setRepeatCount(1);
        alphaAnimation.setRepeatMode(Animation.REVERSE);

        binding.cardView.setAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                binding.questionTextView.setTextColor(Color.GREEN);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                binding.questionTextView.setTextColor(Color.WHITE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void shakeAnimation() {
        Animation shake = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shake_animation);
        binding.cardView.setAnimation(shake);
        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                binding.questionTextView.setTextColor(Color.RED);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                binding.questionTextView.setTextColor(Color.WHITE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }


}