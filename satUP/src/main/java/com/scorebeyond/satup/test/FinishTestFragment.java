package com.scorebeyond.satup.test;

import java.io.IOException;
import java.net.URL;

import com.scorebeyond.satup.R;
import com.scorebeyond.satup.SBApplication;
import com.scorebeyond.satup.utils.URLImageParser;
import com.scorebeyond.satup.webservice.datamodel.Questions;
import com.scorebeyond.satup.webservice.datamodel.Test;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class FinishTestFragment extends Fragment {

	private TextView testStatusTV;
	private Button finishTestButton;
	boolean isReviewModeActive;
	private View line_view;
	private View finish_line_textview;
	private int unansweredCount;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater
				.inflate(R.layout.finish_test_layout, container, false);

		isReviewModeActive = ((SlidingMenuTestActivity)getActivity()).isReviewModeActive();
		
		testStatusTV = (TextView) v.findViewById(R.id.test_status_textview);
		finishTestButton = (Button) v
				.findViewById(R.id.finish_button_at_finish_page);
		line_view = (View)v.findViewById(R.id.line_view);
		finish_line_textview = (View)v.findViewById(R.id.finish_line_textview);
		
		
		finishTestButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finishButtonAtFinishPageClicked();
			}
		});

		if ( isReviewModeActive )
		{
			setReviewMode(true);
		}	
		
		return v;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		updateUnansweredText();
	}

	public void finishButtonAtFinishPageClicked() {
		
		if ( isReviewModeActive )
		{
			sendTestResults();
		}
		else
		{
			if ( unansweredCount > 0 )
			{	
				Dialog dialog = createFinishTestDialog();
				if (dialog != null) {
					dialog.show();
				}
			}
			else
			{
				sendTestResults();
			}	
		}	
	}

	public static FinishTestFragment newInstance() {

		FinishTestFragment f = new FinishTestFragment();

		return f;
	}

	public Dialog createFinishTestDialog() {
		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Finish Test");

		unansweredCount = ((SlidingMenuTestActivity) getActivity())
				.getUnansweredQuestionNumber();
		String warningMessage = "";
		if (unansweredCount > 0)
			warningMessage = "You have "
					+ unansweredCount
					+ " unanswered questions. Do you still want to finish test?";

		builder.setMessage(warningMessage)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								sendTestResults();
							}
						})
				.setNegativeButton("No, Continue",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
							}
						});

		return builder.create();
	}

	protected void sendTestResults() {
		((SlidingMenuTestActivity)getActivity()).sendTestResults();
	}

	public void updateUnansweredText() {
		
		if ( getActivity() != null )
		{	
			unansweredCount = ((SlidingMenuTestActivity) getActivity())
					.getUnansweredQuestionNumber();
	
			if ( testStatusTV != null )
			{
				if (unansweredCount > 0)
					testStatusTV.setText("You have " + unansweredCount
							+ " unanswered questions");
				else
					testStatusTV.setText("");				
			}	
		}
	}

	public void setReviewMode(boolean reviewMode) {
		// TODO Auto-generated method stub
		isReviewModeActive= reviewMode;
		
		if ( isReviewModeActive )
		{
			testStatusTV.setVisibility(View.INVISIBLE);
			finishTestButton.setText("Results");
			line_view.setVisibility(View.INVISIBLE);
			finish_line_textview.setVisibility(View.INVISIBLE);
		}	
	}
}