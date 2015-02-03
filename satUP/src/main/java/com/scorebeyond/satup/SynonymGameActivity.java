package com.scorebeyond.satup;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.scorebeyond.satup.webservice.RetrofitInterface;
import com.scorebeyond.satup.webservice.WebServiceAdapter;
import com.scorebeyond.satup.webservice.datamodel.User;
import com.scorebeyond.satup.webservice.datamodel.vocabularygame.VocabularyGame;
import com.scorebeyond.satup.webservice.datamodel.vocabularygame.VocabularyGameResult;
import com.scorebeyond.satup.webservice.datamodel.vocabularygame.VocabularyGameResultItem;

import java.util.HashMap;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class SynonymGameActivity extends ActionBarActivity {

    private User user;
    private RetrofitInterface retrofitInterface;
    private VocabularyGame vocabularyGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synonym_game);

        user = ((SBApplication)getApplicationContext()).getAppUser();
        retrofitInterface = ((SBApplication)getApplicationContext()).getRetrofitInterface();

        createVocabularyGame();
    }

    private void createVocabularyGame() {
        retrofitInterface.getVocabularyGame(user.getSecurity_token(), user.getUser_id(), new Callback<Response>() {
            @Override
            public void success(Response result, Response arg1) {
                vocabularyGame = WebServiceAdapter.getVocabularyGameFromResponse(result);
                if (vocabularyGame != null) {
                }
            }

            @Override
            public void failure(RetrofitError arg0) {

            }
        });

    }

    public void sendVocabularyGameResult(View view)
    {
        VocabularyGameResult gameResult = createTestData();
        retrofitInterface.sendVocabularyGameResult(user.getSecurity_token(), user.getUser_id(), vocabularyGame.getId(), gameResult, new Callback<Response>(){

            @Override
            public void success(Response result, Response arg1) {
            }

            @Override
            public void failure(RetrofitError arg0) {
            }
        });

    }

    public VocabularyGameResult createTestData() {
        VocabularyGameResult gameResult = new VocabularyGameResult();
        gameResult.setScore(10);
        gameResult.setTotal_time(63);

        HashMap<String, Integer> wordScores = new HashMap<String, Integer>();
        wordScores.put("adept", 15);
        wordScores.put("betide", -15);
        wordScores.put("demonstrative", 8);
        wordScores.put("happen", -15);
        gameResult.setWord_scores(wordScores);

        VocabularyGameResultItem resultItem1 = new VocabularyGameResultItem();
        resultItem1.setSelection("demonstrative");
        resultItem1.setWord("showing");

        VocabularyGameResultItem resultItem2 = new VocabularyGameResultItem();
        resultItem2.setSelection("to turn");
        resultItem2.setWord("wend");

        VocabularyGameResultItem resultItem3 = new VocabularyGameResultItem();
        resultItem3.setSelection("adept");
        resultItem3.setWord("happen");

        gameResult.setResults(new VocabularyGameResultItem[]{resultItem1, resultItem2, resultItem3});

        convertObjectToJson(gameResult);

        return gameResult;

        /*
        {
           "results":[
              {
                 "selection":"demonstrative",
                 "word":"showing"
              },
              {
                 "selection":"to turn",
                 "word":"wend"
              },
              {
                 "selection":"adept",
                 "word":"happen"
              },
              {
                 "selection":"betide",
                 "word":"skillful"
              }
           ],
           "score":10,
           "total_time":63,
           "word_scores":{
              "adept":-15,
              "betide":-15,
              "demonstrative":8,
              "happen":-15,
              "showing":8,
              "skillful":-15,
              "to turn":5,
              "wend":5
           }
        }
        */
    }

    private String convertObjectToJson(Object object) {
        Gson gson = new Gson();
        String json = gson.toJson(object);
        Log.d("SATUP",json);
        return json;

    }





}
