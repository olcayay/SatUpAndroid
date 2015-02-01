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
import com.scorebeyond.satup.webservice.datamodel.Profile;
import com.scorebeyond.satup.webservice.datamodel.Test;
import com.scorebeyond.satup.webservice.datamodel.TestStatResult;
import com.scorebeyond.satup.webservice.datamodel.User;

public class WebServiceAdapter {

    public static User getUserObjectFromResponse(Response result) {

        User user = null;
        try {
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
                } catch (IOException e) {
                }
            } catch (IOException e) {
            }

            String bodyResponse = sb.toString();
            Gson gson = new Gson();
            user = gson.fromJson(( ((JsonObject) new JsonParser()
                    .parse(bodyResponse)).get("result")), User.class);

        } catch (Exception e) {
        }

        return user;

    }

    public static Profile getProfileObjectFromResponse(Response result) {

        Profile profile = null;
        try {
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
                } catch (IOException e) {
                }
            } catch (IOException e) {
            }

            String bodyResponse = sb.toString();
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
                } catch (IOException e) {
                }
            } catch (IOException e) {
            }

            String bodyResponse = sb.toString();
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
                } catch (IOException e) {
                }
            } catch (IOException e) {
            }

            String bodyResponse = sb.toString();
            Gson gson = new Gson();

//			ArrayList<TestStatResult> testStatResultArrayList = new ArrayList<TestStatResult>();
//			List<TestStatResult> list = gson.fromJson(((JsonArray) ((JsonObject) new JsonParser()
//			.parse(bodyResponse)).get("result")), testStatResultArrayList.getClass());
//			
//			if ( list.size() > 0 )
//				testStat = list.get(0);

            //Gson gson = new Gson();
            //String jsonString = "[{\"id\":18,\"city\":\"test\",\"street\":\"test 1\",\"zipcode\":121209,\"state\":\"IL\",\"lat\":32.158138,\"lng\":34.807838},{\"id\":19,\"city\":\"test\",\"street\":\"1\",\"zipcode\":76812,\"state\":\"IL\",\"lat\":32.161041,\"lng\":34.810410}]";
            Type type = new TypeToken<List<TestStatResult>>(){}.getType();
            List<TestStatResult> data =  gson.fromJson(((JsonArray) ((JsonObject) new JsonParser()
                    .parse(bodyResponse)).get("result")), type);

            if ( data.size() > 0 )
                testStat = data.get(0);
			
			/*
			testStat = gson.fromJson(((JsonObject) ((JsonObject) new JsonParser()
					.parse(bodyResponse)).get("result")), TestStatResult.class);
			*/
        }
        catch (Exception e) {
            Log.e("SB", e.getMessage());
        }

        return testStat;
    }
}
