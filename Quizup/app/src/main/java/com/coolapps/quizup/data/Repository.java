package com.coolapps.quizup.data;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.coolapps.quizup.controller.AppController;
import com.coolapps.quizup.model.Question;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    private ArrayList<Question> QuestionList = new ArrayList<>();
    private String url = "https://opentdb.com/api.php?amount=30&category=17&type=boolean";
    //"https://opentdb.com/api.php?amount=35&category=18&type=boolean";

    public ArrayList<Question> getQuestionList(final AsyncQuestionTask callBack){

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        for (int i = 0; i < response.getJSONArray("results").length(); i++) {
                            JSONObject questionObject = (JSONObject) response.getJSONArray("results").get(i);

                            QuestionList.add(new Question(decodeString(questionObject.getString("question")),
                                    questionObject.getBoolean("correct_answer")));

                        }
                        if( callBack != null)
                            callBack.onQuestionsLoaded(QuestionList);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }, error -> Log.d("Volley",error.toString()));
         AppController.getInstance().addToRequestQueue(jsonObjectRequest);
        return  QuestionList;
    }

    private String decodeString(String str) {
       if(str == null)
           return "";
        return str.replaceAll("&lt;", "<").replaceAll("&gt;", ">")
                .replaceAll("&apos;", "'").replaceAll("&quot;", "\"")
                .replaceAll("&amp;", "&").replaceAll("&#039;","'");


    }

}
