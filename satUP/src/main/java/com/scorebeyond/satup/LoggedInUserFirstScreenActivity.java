package com.scorebeyond.satup;

import com.scorebeyond.satup.test.SlidingMenuTestActivity;
import com.scorebeyond.satup.webservice.RetrofitInterface;
import com.scorebeyond.satup.webservice.datamodel.User;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class LoggedInUserFirstScreenActivity extends Activity{

	RetrofitInterface retrofitInterface = null;
	User user = null;
	private Spinner questionNumberSpinner;
	private TextView currentlySignedin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_loggedin_user_first_screen);
		retrofitInterface = ((SBApplication)getApplicationContext()).getRetrofitInterface();
		user = ((SBApplication)getApplicationContext()).getAppUser();

		questionNumberSpinner = (Spinner)findViewById(R.id.question_number_spinner);
//		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.array.question_number_arrays, R.layout.spinner_item);
//		questionNumberSpinner.setAdapter(adapter);
//		adapter.setDropDownViewResource(R.layout.spinner_item);

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
	            this, R.array.question_number_arrays, R.layout.spinner_item);
	adapter.setDropDownViewResource(R.layout.spinner_item);
	questionNumberSpinner.setAdapter(adapter);

		questionNumberSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		    @Override
		    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
		        ((TextView)parentView.getChildAt(0)).setTextColor(Color.rgb(255, 255, 255));
		    }

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});

		currentlySignedin = (TextView)findViewById(R.id.currently_singedin_testview);
		currentlySignedin.setText(getResources().getString(R.string.currently_signedin) + " " + user.getUsername());
	}

	public void createTest(View view)
	{
		int questionNumber = ( questionNumberSpinner.getSelectedItemPosition() + 1)* 5;
		gotoTestActivity(questionNumber, new String[] {"math:geometry"});
	}

	public void signoutButtonPressed(View view){
		Dialog dialog = createSignoutDialog();
		if (dialog != null)
			dialog.show();
	}

	public void signout(){
		((SBApplication)getApplicationContext()).setAppUser(null);
		returnToLoginScreen();
	}

	private void returnToLoginScreen() {
		Intent intent = new Intent(this,FirstScreen.class);
		startActivity(intent);
	}

	protected void gotoTestActivity(int questionNumber, String[] strings) {
		//Intent intent = new Intent(this,TestActivity.class);
		Intent intent = new Intent(this,SlidingMenuTestActivity.class);
		intent.putExtra("questionNumber", questionNumber);
		intent.putExtra("testContent", strings);
		startActivity(intent);

	}

	/*
    protected void saveText(Test test) {
		((SBApplication)getApplicationContext()).setTest(test);
	}
    */

	public Dialog createSignoutDialog() {
		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Signing out");

		builder.setMessage(
				"Are you sure you want to logout?")
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								signout();
							}
						})
				.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
					}
				});
		return builder.create();
	}

    public void startSynonymGame(View view)
    {
        Intent intent = new Intent(this,SynonymGameActivity.class);
        startActivity(intent);

    }

    public void startFlashCardGame(View view)
    {
        Intent intent = new Intent(this,FlashCardActivity.class);
        startActivity(intent);
    }

}
