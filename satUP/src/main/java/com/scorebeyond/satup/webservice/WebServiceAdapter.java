package com.scorebeyond.satup.webservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

import retrofit.client.Response;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.scorebeyond.satup.webservice.datamodel.flashcardgame.FlashCardGameItem;
import com.scorebeyond.satup.webservice.datamodel.Profile;
import com.scorebeyond.satup.webservice.datamodel.test.Test;
import com.scorebeyond.satup.webservice.datamodel.test.TestStatResult;
import com.scorebeyond.satup.webservice.datamodel.User;
import com.scorebeyond.satup.webservice.datamodel.vocabularygame.VocabularyGame;

public class WebServiceAdapter {

    public static User getUserObjectFromResponse(Response result) {

        User user = null;
        try {
            String bodyResponse = getResultBodyString(result);

            Gson gson = new Gson();
            user = gson.fromJson(( ((JsonObject) new JsonParser()
                    .parse(bodyResponse)).get("result")), User.class);

        } catch (Exception e) {
        }

        return user;

    }

    private static String getResultBodyString(Response result) {

        String bodyResponse = null;
        BufferedReader reader;
        StringBuilder sb = new StringBuilder();
        try {

            reader = new BufferedReader(new InputStreamReader(result
                    .getBody().in()));

            String line;

            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }

                bodyResponse = sb.toString();
            } catch (IOException e) {
            }
        } catch (IOException e) {
        }

        return bodyResponse;
    }

    public static Profile getProfileObjectFromResponse(Response result) {

        Profile profile = null;
        try {
            String bodyResponse = getResultBodyString(result);
            Gson gson = new Gson();
            profile = gson
                    .fromJson(( ((JsonObject) new JsonParser()
                            .parse(bodyResponse)).get("result")), Profile.class);

        } catch (Exception e) {
        }

        return profile;

    }

    public static Test getTestObjectFromResponse(Response result) {

        Test test = null;
        try {
            String bodyResponse = getResultBodyString(result);
            Gson gson = new Gson();
            test = gson.fromJson(( ((JsonObject) new JsonParser()
                    .parse(bodyResponse)).get("result")), Test.class);

        } catch (Exception e) {
        }

        return test;
    }

    public static TestStatResult getTestStatResultFromResponse(Response result) {
        TestStatResult testStat = null;
        try {
            String bodyResponse = getResultBodyString(result);
            Gson gson = new Gson();

            Type type = new TypeToken<List<TestStatResult>>(){}.getType();
            List<TestStatResult> data =  gson.fromJson((JsonArray) ((JsonObject) new JsonParser()
                    .parse(bodyResponse)).get("result"), type);

            if ( data.size() > 0 ) {
                testStat = data.get(0);
            }
        }
        catch (Exception e) {
            Log.e("SB", e.getMessage());
        }

        return testStat;
    }

    public static List<FlashCardGameItem> getFlashCardItemsFromResponse(Response result) {
        List<FlashCardGameItem> flashCardList = null;
        try {
            String bodyResponse = getResultBodyString(result);
            Gson gson = new Gson();

            Type type = new TypeToken<List<FlashCardGameItem>>(){}.getType();
            flashCardList =  gson.fromJson((JsonArray) ((JsonObject) new JsonParser()
                    .parse(bodyResponse)).get("result"), type);
        }
        catch (Exception e) {
            Log.e("SB", e.getMessage());
        }

        return flashCardList;
    }

    public static VocabularyGame getVocabularyGameFromResponse(Response result) {
        VocabularyGame vocabularyGame = null;
        try {
            String bodyResponse = getResultBodyString(result);
            Gson gson = new Gson();
            vocabularyGame = gson.fromJson(( ((JsonObject) new JsonParser()
                    .parse(bodyResponse)).get("result")), VocabularyGame.class);

        } catch (Exception e) {
        }

        return vocabularyGame;
    }
}
