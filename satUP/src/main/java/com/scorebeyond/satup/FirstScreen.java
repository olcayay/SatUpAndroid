package com.scorebeyond.satup;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class FirstScreen extends Activity {

    Button facebook_login_button;
    Button email_login_button;
    TextView dont_have_account_textview;
    Button create_account_button;
    boolean isCreateButtonAtFirstState = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//	    getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_first_screen);

        if (((SBApplication) getApplicationContext()).getAppUser() != null) {
            gotoLoggedInUserFirstScreen();
            return;
        }


        underlineTextView();

        facebook_login_button = (Button) findViewById(R.id.facebook_login_button);
        email_login_button = (Button) findViewById(R.id.email_login_button);
        create_account_button = (Button) findViewById(R.id.create_account_button);
        dont_have_account_textview = (TextView) findViewById(R.id.dont_have_account_textview);

    }

    protected void gotoLoggedInUserFirstScreen() {
        Intent intent = new Intent(this, LoggedInUserFirstScreenActivity.class);
        startActivity(intent);
    }

    private void underlineTextView() {
        TextView why_do_i_need_textview = (TextView) findViewById(R.id.why_do_i_need_textview);
        why_do_i_need_textview.setPaintFlags(why_do_i_need_textview.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    public void createAccountButtonClicked(View view) {
        if (isCreateButtonAtFirstState) {
            facebook_login_button.setText(getResources().getString(R.string.facebook_register));
            email_login_button.setText(getResources().getString(R.string.email_register));
            create_account_button.setText(getResources().getString(R.string.back_to_login));
            dont_have_account_textview.setVisibility(View.INVISIBLE);
        } else {
            facebook_login_button.setText(getResources().getString(R.string.facebook_login));
            email_login_button.setText(getResources().getString(R.string.email_login));
            create_account_button.setText(getResources().getString(R.string.create_account));
            dont_have_account_textview.setVisibility(View.VISIBLE);
        }

        isCreateButtonAtFirstState = !isCreateButtonAtFirstState;
    }

    public void emailButtonClicked(View view) {
        if (isCreateButtonAtFirstState) {
            gotoLoginScreen();
        } else {
            gotoRegisterScreen();
        }
    }

    private void gotoRegisterScreen() {
        Intent intent = new Intent(this, LoginAndRegisterScreenActivity.class);
        intent.putExtra("isLoginModeActive", false);
        startActivity(intent);
    }

    private void gotoLoginScreen() {
        Intent intent = new Intent(this, LoginAndRegisterScreenActivity.class);
        intent.putExtra("isLoginModeActive", true);
        startActivity(intent);
    }

    public void facebookButtonClicked(View view) {
        if (isCreateButtonAtFirstState) {
            Toast.makeText(this, "Login with Facebook is not active yet", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Register with Facebook is not active yet", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.first_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
