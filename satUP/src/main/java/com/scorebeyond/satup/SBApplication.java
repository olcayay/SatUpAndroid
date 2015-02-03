    package com.scorebeyond.satup;

    import retrofit.RestAdapter;
    import retrofit.RestAdapter.LogLevel;
    import retrofit.android.AndroidLog;

    import com.google.gson.Gson;
    import com.scorebeyond.satup.webservice.RetrofitInterface;
    import com.scorebeyond.satup.webservice.datamodel.test.Test;
    import com.scorebeyond.satup.webservice.datamodel.test.TestStatResult;
    import com.scorebeyond.satup.webservice.datamodel.User;

    import android.app.Application;
    import android.content.SharedPreferences;
    import android.content.SharedPreferences.Editor;

    public class SBApplication extends Application {

        private static SBApplication singleton;
        private User appUser;
        private RetrofitInterface retrofitInterface;
        private Test test;
        private SharedPreferences mPrefs;
        private TestStatResult testStatResult;

        public SBApplication getInstance() {
            return singleton;
        }

        @Override
        public void onCreate() {
            super.onCreate();
            singleton = this;

            mPrefs = getSharedPreferences("preferences",
                    MODE_PRIVATE);
            Gson gson = new Gson();
            String json = mPrefs.getString("User", "");
            appUser = gson.fromJson(json, User.class);

            retrofitInterface = new RestAdapter.Builder()
                    .setEndpoint(RetrofitInterface.API_URL)
                    .setLogLevel(LogLevel.FULL).setLog(new AndroidLog("SB"))
                    .build().create(RetrofitInterface.class);

        }

        public User getAppUser() {
            return appUser;
        }

        public void setAppUser(User appUser) {
            this.appUser = appUser;

            Editor prefsEditor = mPrefs.edit();
            Gson gson = new Gson();
            String json = gson.toJson(appUser);
            prefsEditor.putString("User", json);
            prefsEditor.commit();
        }

        public RetrofitInterface getRetrofitInterface() {
            return retrofitInterface;
        }

        public Test getTest() {
            return test;
        }

        public void setTest(Test test) {
            this.test = test;
        }

        public TestStatResult getTestStatResult() {
            return testStatResult;
        }

        public void setTestStatResult(TestStatResult testStatResult) {
            this.testStatResult = testStatResult;
        }
    }