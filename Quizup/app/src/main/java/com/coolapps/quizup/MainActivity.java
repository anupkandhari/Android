package com.coolapps.quizup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.coolapps.quizup.data.AsyncQuestionTask;
import com.coolapps.quizup.data.Repository;
import com.coolapps.quizup.databinding.ActivityMainBinding;
import com.coolapps.quizup.model.Question;
import com.coolapps.quizup.model.Score;
import com.coolapps.quizup.utils.Prefs;
import com.google.android.material.snackbar.Snackbar;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ArrayList<Question> questionArrayList;
    private int currentQuestionIndex = 0;
    private int scoreCounter = 0;
    private Score score;
    private Prefs prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        score = new Score();
        prefs = new Prefs(MainActivity.this);

        currentQuestionIndex = prefs.getState();

        binding.highestScoreText.setText(MessageFormat.format("Highest: {0}", String.valueOf(prefs.getHighestScore())));


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
            getNextQuestion();
        });



    }

    private void getNextQuestion() {
        currentQuestionIndex = (currentQuestionIndex + 1) % questionArrayList.size();
        updateQuestion();
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
                addPoints();
            }
            else{
                msgId = R.string.wrong;
                shakeAnimation();
                deductPoints();
            }

        }
        else
            msgId = R.string.question_not_loaded;
        Snackbar.make(binding.cardView,msgId,Snackbar.LENGTH_SHORT).show();

    }

    private void deductPoints() {


        if (scoreCounter > 0) {
            scoreCounter -= 100;
            score.setScore(scoreCounter);
            binding.scoreText.setText(MessageFormat.format("Current Score: {0}",
                    String.valueOf(score.getScore())));

        } else {
            scoreCounter = 0;
            score.setScore(scoreCounter);

        }
    }

    private void addPoints() {
        scoreCounter += 100;
        score.setScore(scoreCounter);
        binding.scoreText.setText(String.valueOf(score.getScore()));
        binding.scoreText.setText(MessageFormat.format("Current Score: {0}",
                String.valueOf(score.getScore())));

    }

    @Override
    protected void onPause() {
        prefs.saveHighestScore(score.getScore());
        prefs.setState(currentQuestionIndex);
        Log.d("State", "onPause: saving state " + prefs.getState() );
        Log.d("Pause", "onPause: saving score " + prefs.getHighestScore() );
        super.onPause();
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
                getNextQuestion();
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
                getNextQuestion();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }


}