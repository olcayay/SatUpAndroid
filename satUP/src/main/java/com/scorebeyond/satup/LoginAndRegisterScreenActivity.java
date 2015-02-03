    package com.scorebeyond.satup;

    import com.scorebeyond.satup.webservice.LoginRequest;
    import com.scorebeyond.satup.webservice.RegisterRequest;
    import com.scorebeyond.satup.webservice.RetrofitInterface;
    import com.scorebeyond.satup.webservice.WebServiceAdapter;
    import com.scorebeyond.satup.webservice.datamodel.Profile;
    import com.scorebeyond.satup.webservice.datamodel.User;

    import retrofit.Callback;
    import retrofit.RetrofitError;
    import retrofit.client.Response;
    import android.app.Activity;
    import android.content.Intent;
    import android.graphics.Paint;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.TextView;
    import android.widget.Toast;

    public class LoginAndRegisterScreenActivity extends Activity {

        EditText email_edittext;
        EditText password_edittext;
        EditText username_edittext;
        Button email_login_or_register_button;
        TextView forgot_password_textview;
        TextView error_message_textview;

        User appUser;
        boolean isLoginModeActive = true;

        RetrofitInterface retrofitInterface = null;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

    //		requestWindowFeature(Window.FEATURE_NO_TITLE);
    //	    getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

            setContentView(R.layout.activity_login_screen);

            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                isLoginModeActive = bundle.getBoolean("isLoginModeActive");
            }

            retrofitInterface = ((SBApplication)getApplicationContext()).getRetrofitInterface();
            createViews();
            underlineTextView();
            prepareViews();
        }

        private void createViews() {
            email_edittext = (EditText) findViewById(R.id.email_edittext);
            password_edittext = (EditText) findViewById(R.id.password_edittext);
            username_edittext = (EditText) findViewById(R.id.username_edittext);
            email_login_or_register_button = (Button) findViewById(R.id.email_login_or_register_button);
            forgot_password_textview = (TextView) findViewById(R.id.forgot_password_textview);
            error_message_textview = (TextView) findViewById(R.id.error_message_textview);
        }

        private void prepareViews() {
            if (!isLoginModeActive) {
                email_login_or_register_button.setText(getResources().getString(
                        R.string.register));
                username_edittext.setVisibility(View.VISIBLE);
                forgot_password_textview.setVisibility(View.INVISIBLE);
            }
        }

        private void underlineTextView() {
            TextView forgot_password_textview = (TextView) findViewById(R.id.forgot_password_textview);
            forgot_password_textview.setPaintFlags(forgot_password_textview
                    .getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        }

        public void closeButtonClicked(View view) {
            finish();
        }

        public void loginOrRegisterButtonClicked(View view) {

            if (isLoginModeActive) {

                //login("olcayay@gmail.com", "longpassword");

                if (email_edittext.getText().toString().equals("")) {
                    error_message_textview.setVisibility(View.VISIBLE);
                    error_message_textview.setText(getResources().getString(
                            R.string.error_message_noemail));
                } else if (password_edittext.getText().toString().equals("")) {
                    error_message_textview.setVisibility(View.VISIBLE);
                    error_message_textview.setText(getResources().getString(
                            R.string.error_message_nopassword));
                } else {
                    error_message_textview.setVisibility(View.INVISIBLE);
                    login(email_edittext.getText().toString(), password_edittext
                            .getText().toString());
                }

            } else {
                if (email_edittext.getText().toString().equals("")) {
                    error_message_textview.setVisibility(View.VISIBLE);
                    error_message_textview.setText(getResources().getString(
                            R.string.error_message_noemail));
                } else if (password_edittext.getText().toString().equals("")) {
                    error_message_textview.setVisibility(View.VISIBLE);
                    error_message_textview.setText(getResources().getString(
                            R.string.error_message_nopassword));
                } else if (username_edittext.getText().toString().equals("")) {
                    error_message_textview.setVisibility(View.VISIBLE);
                    error_message_textview.setText(getResources().getString(
                            R.string.error_message_nousername));
                } else {
                    error_message_textview.setVisibility(View.INVISIBLE);
                    register(email_edittext.getText().toString(), password_edittext
                            .getText().toString(), username_edittext.getText()
                            .toString());
                }

            }
        }


        private void register(String email, String password, String username) {
            retrofitInterface.register(new RegisterRequest(email, password, username), new Callback<Response>() {
                @Override
                public void success(Response result, Response response) {

                    User user = WebServiceAdapter.getUserObjectFromResponse(result);
                    if (  user != null )
                    {
                        Toast.makeText(getApplicationContext(), "You have successfully registered! Welcome " + user.getUsername(), Toast.LENGTH_SHORT).show();

                        saveUser(user);
                        createProfile();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "User Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_SHORT).show();
                }

            });

        }

        protected void createProfile() {
            retrofitInterface.createProfile(appUser.getSecurity_token(), appUser.getUser_id(), new Callback<Response>(){

                @Override
                public void success(Response result, Response arg1) {
                    Profile profile = WebServiceAdapter.getProfileObjectFromResponse(result);
                    if (  profile != null )
                    {
                        Toast.makeText(getApplicationContext(), "Profiliniz oluşturuldu " + profile.getId(), Toast.LENGTH_SHORT).show();
                        gotoLoggedInUserFirstScreen();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "User Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void failure(RetrofitError arg0) {
                }
            });
        }

        protected void gotoLoggedInUserFirstScreen() {
            Intent intent = new Intent(this, LoggedInUserFirstScreenActivity.class);
            startActivity(intent);
        }

        private void login(String email, String password) {

            retrofitInterface.login(new LoginRequest(email, password), new Callback<Response>() {
                @Override
                public void success(Response result, Response response) {

                    User user = WebServiceAdapter.getUserObjectFromResponse(result);
                    if (  user != null )
                    {
                        saveUser(user);
                        createProfile();
                        Toast.makeText(getApplicationContext(), "You have successfully logged in! Welcome " + user.getUsername(), Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "User Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_SHORT).show();
                }

            });
        }

        protected void saveUser(User user) {
            appUser = user;
            ((SBApplication)getApplicationContext()).setAppUser(user);
        }
    }
