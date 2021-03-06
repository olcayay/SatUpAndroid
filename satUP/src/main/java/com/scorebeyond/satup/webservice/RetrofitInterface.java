package com.scorebeyond.satup.webservice;

import com.scorebeyond.satup.webservice.datamodel.test.QuestionResultList;
import com.scorebeyond.satup.webservice.datamodel.vocabularygame.VocabularyGameResult;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;

public interface RetrofitInterface {

    public static final String API_URL = "http://api.scorebeyond.com";

    //static RetrofitInterface instance = null;

    @Headers("Content-Type: application/json")
    @POST("/v1/account/login")
    void login(@Body LoginRequest loginInfo, Callback<Response> callback);

    @Headers("Content-Type: application/json")
    @POST("/v1/account/register")
    void register(@Body RegisterRequest registerInfo, Callback<Response> callback);

    @Headers("Content-Type: application/json")
    @GET("/v1/sat/{user_id}/profile")
    void createProfile(@Header("X-Token") String security_token, @Path("user_id") String user_id, Callback<Response> callback);

    @Headers("Content-Type: application/json")
    @POST("/v1/sat/{user_id}/test")
    void createTest(@Header("X-Token") String security_token, @Path("user_id") String user_id, @Body TestRequest testInfo, Callback<Response> callback);

    @Headers("Content-Type: application/json")
    @PUT("/v1/sat/{user_id}/test/{test_id}")
    void sendTestResult(@Header("X-Token") String security_token, @Path("user_id") String user_id, @Path("test_id") String test_id, @Body QuestionResultList testResultList, Callback<Response> callback);

    @Headers("Content-Type: application/json")
    @GET("/v1/sat/{user_id}/test/{test_id}/stats")
    void getTestStat(@Header("X-Token") String security_token, @Path("user_id") String user_id, @Path("test_id") String test_id, Callback<Response> callback);

    @Headers("Content-Type: application/json")
    @GET("/v1/sat/{user_id}/flashcards")
    void getFlashCards(@Header("X-Token") String security_token, @Path("user_id") String user_id, @Query("count") int count, @Query("mode") String mode, Callback<Response> callback);

    @Headers("Content-Type: application/json")
    @POST("/v1/sat/{user_id}/vocab")
    void getVocabularyGame(@Header("X-Token") String security_token, @Path("user_id") String user_id, Callback<Response> callback);

    @Headers("Content-Type: application/json")
    @PUT("/v1/sat/{user_id}/vocab/{test_id}")
    void sendVocabularyGameResult(@Header("X-Token") String security_token, @Path("user_id") String user_id, @Path("test_id") String test_id, @Body VocabularyGameResult vocabularyGameResult, Callback<Response> callback);

}
