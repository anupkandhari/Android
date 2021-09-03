package com.coolapps.quizup.data;

import com.coolapps.quizup.model.Question;

import java.util.ArrayList;
import java.util.List;

public interface AsyncQuestionTask {
    public void onQuestionsLoaded(ArrayList<Question> questionList);
}
