package com.scorebeyond.satup.test;


import com.scorebeyond.satup.R;
import com.scorebeyond.satup.webservice.datamodel.test.Answers;
import com.scorebeyond.satup.webservice.datamodel.test.Questions;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AnswerAdapter extends ArrayAdapter<Answers> {

	Context context;
	int resource;
	Answers[] answers = null;
	String[] selectionCharArray = {"A","B","C","D","E","F","G","H","I","J"};
	int selectedItemIndex = -1;
	private boolean isReviewModeActive;
	private Questions question;
	
	public AnswerAdapter(Context context, int resource, Answers[] answers) {
		super(context, resource, answers);
		
		this.context = context;
		this.resource = resource;
		this.answers = answers;
		
		isReviewModeActive = ((SlidingMenuTestActivity)context).isReviewModeActive();

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View v = convertView;

		if (v == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(resource, null);
		}

		Answers answer = answers[position];

		if (answer != null) {

			final TextView selectionCharTV = (TextView) v.findViewById(R.id.selectionChar);
			final LinearLayout selectionLayout = (LinearLayout) v.findViewById(R.id.selectionCircle);
			
			selectionCharTV.setText(selectionCharArray[position]);
			
//			final WebView selectionWV = (WebView) v.findViewById(R.id.selection_webview);
//			String questionText = answer.getText();
//			String questionTextWithFullPathSrc = questionText.replace("src=\"",
//					"src=\"" + question.getAsset_url());
//			selectionWV.getSettings().setJavaScriptEnabled(true);
//			selectionWV.setWebViewClient(new WebViewClient());
//
//			selectionWV.loadData(questionTextWithFullPathSrc, "text/html", "UTF-8");

			final TextView selectionText = (TextView) v.findViewById(R.id.selectionText);
			
			String answerText = answer.getText();
			Spanned spanned = Html.fromHtml(answerText);			
			selectionText.setText(spanned);

			
			if ( selectedItemIndex == position )
			{
				if ( isReviewModeActive )
				{
					if ( answer.getCorrect() )
					{
						selectionLayout.setBackgroundResource(R.drawable.red_circle);
					}
					else
						selectionLayout.setBackgroundResource(R.drawable.green_circle);						
					
				}
				else	
					selectionLayout.setBackgroundResource(R.drawable.blue_circle);
				
				selectionCharTV.setTextColor(Color.WHITE);				
			}
			else
			{
				if ( isReviewModeActive &&
				     answer.getCorrect() )
				{
					selectionLayout.setBackgroundResource(R.drawable.dark_gray_circle);
					selectionCharTV.setTextColor(Color.WHITE);																		
				}
				else
				{
					selectionLayout.setBackgroundResource(R.drawable.gray_circle);
					selectionCharTV.setTextColor(Color.BLACK);													
				}	
			}	
		}
		
		return v;

	}

	public void selectedItemChanged(int position) {
		selectedItemIndex = position;
	}

	public void setQuestion(Questions question) {
		this.question = question;
	}


}
