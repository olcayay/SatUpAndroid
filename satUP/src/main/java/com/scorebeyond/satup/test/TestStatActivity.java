package com.scorebeyond.satup.test;

import com.scorebeyond.satup.R;
import com.scorebeyond.satup.SBApplication;
import com.scorebeyond.satup.webservice.datamodel.Cards;
import com.scorebeyond.satup.webservice.datamodel.Subcells;
import com.scorebeyond.satup.webservice.datamodel.TestStatResult;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class TestStatActivity extends Activity{

    private final static int BIGGER_MARGIN = 20;
    private final static int SMALLER_MARGIN = 2;

    private LinearLayout containerLayout;
    private TestStatResult testStatResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test_stat);

        testStatResult = ((SBApplication)getApplicationContext()).getTestStatResult();
        containerLayout = ( LinearLayout )findViewById(R.id.container_layout);

        if ( testStatResult != null )
            addCardViews(testStatResult);



//		View progressView = getLayoutInflater().inflate(R.layout.card_type_progress, null);
//		containerLayout.addView(progressView);
//		setMargins(progressView, 0, 2, 0, 0);		
//		
//		View timeView1 = getLayoutInflater().inflate(R.layout.card_type_time, null);
//		TextView title = (TextView)timeView1.findViewById(R.id.card_title_textview);
//		title.setText("Başlık 1");
//		containerLayout.addView(timeView1);
//		setMargins(timeView1, 0, 10, 0, 0);
//
//		View timeView2 = getLayoutInflater().inflate(R.layout.card_type_time, null);
//		LinearLayout titleLayout = (LinearLayout)timeView2.findViewById(R.id.card_title_layout);
//		titleLayout.setVisibility(View.GONE);
//		containerLayout.addView(timeView2);
//		setMargins(timeView2, 0, 2, 0, 0);
//
//		View timeView3 = getLayoutInflater().inflate(R.layout.card_type_time, null);
//		containerLayout.addView(timeView3);
//		setMargins(timeView3, 0, 20, 0, 0);

    }



    private void addCardViews(TestStatResult testStatResult) {
        Cards[] cards = testStatResult.getCards();

        //for ( int i = 0; i < cards.length; i++ )
        for(Cards card : cards)
        {
            if ( card.getType().equals("progress") )
            {
                addProgressView(card);
            }
            else if ( card.getType().equals("time") )
            {
                addTimeView(card);
            }
            else if ( card.getType().equals("horizontal") )
            {
                addHorizontalView(card);
            }
        }
    }



    private void addHorizontalView(Cards card) {
        View horizantalView = getLayoutInflater().inflate(R.layout.card_type_horizantal, null);
        if ( card.getTitle() != null &&
                card.getTitle().getText() != null )
        {
            TextView titleTextView = (TextView)horizantalView.findViewById(R.id.card_title_textview);
            titleTextView.setText(card.getTitle().getText());
        }
        else
        {
            LinearLayout cardTitleLayout = (LinearLayout)horizantalView.findViewById(R.id.card_title_layout);
            cardTitleLayout.setVisibility(View.GONE);
        }

        LinearLayout horizantalViewBody = (LinearLayout)horizantalView.findViewById(R.id.card_body_layout);

        int subcellCount = card.getSubcells().length;
        for ( int i = 0; i < subcellCount; i++ )
        {
            Subcells subcell = card.getSubcells()[i];
            addHorizantalSubView(horizantalViewBody,subcell,i);
        }

        containerLayout.addView(horizantalView);

        int topMargin = card.isAppend()?SMALLER_MARGIN:BIGGER_MARGIN;
        setMargins(horizantalView, 0, topMargin, 0, 0);

    }



    private void addHorizantalSubView(LinearLayout horizantalViewBody, Subcells subcell, int i) {
        View horizantalItemView = getLayoutInflater().inflate(R.layout.card_type_horizantal_item, null);

        TextView titleTextView = (TextView)horizantalItemView.findViewById(R.id.card_title_textview);
        titleTextView.setText(subcell.getTitle().getText());

        TextView subtitleTextView = (TextView)horizantalItemView.findViewById(R.id.card_subtitle_textview);
        subtitleTextView.setText(subcell.getSubtitle().getText());

        int colorCode = subcell.getSubtitle().getColor();
        if ( colorCode == 14 || colorCode == 9 )
            subtitleTextView.setTextColor(Color.RED);
        else if ( colorCode == 5 )
            subtitleTextView.setTextColor(Color.GREEN);


        horizantalViewBody.addView(horizantalItemView);

        LayoutParams param = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT, 1.0f);
        horizantalItemView.setLayoutParams(param);

        if ( i > 0 )
        {
            setMargins(horizantalItemView, SMALLER_MARGIN, 0, 0, 0);
        }

    }



    private void addTimeView(Cards card) {
        View timeView = getLayoutInflater().inflate(R.layout.card_type_time, null);
        if ( card.getTitle() != null &&
                card.getTitle().getText() != null )
        {
            TextView titleTextView = (TextView)timeView.findViewById(R.id.card_title_textview);
            titleTextView.setText(card.getTitle().getText());
        }
        else
        {
            LinearLayout cardTitleLayout = (LinearLayout)timeView.findViewById(R.id.card_title_layout);
            cardTitleLayout.setVisibility(View.GONE);
        }


        if ( card.getSubtitle() != null &&
                card.getSubtitle().getText() != null )
        {
            TextView subTitleTextView = (TextView)timeView.findViewById(R.id.card_subtitle_textview);
            subTitleTextView.setText( card.getSubtitle().getText());
        }

        TextView valueTextView = (TextView)timeView.findViewById(R.id.card_score_textview);
        if ( card.getValue() != null &&
                !card.getValue().equals("") )
        {
            valueTextView.setText( card.getValue());
        }
        else
        {
            int time = card.getTime();
            String timeStr;

            if (time < 60 )
                timeStr = String.valueOf(time)+"s";
            else
            {
                int minutes = time / 60;
                int seconds = time % 60;

                timeStr = String.format("%02d:%02d", minutes,seconds);
            }

            valueTextView.setText( timeStr);
        }


        containerLayout.addView(timeView);

        int topMargin = card.isAppend()?SMALLER_MARGIN:BIGGER_MARGIN;
        setMargins(timeView, 0, topMargin, 0, 0);
    }



    private void addProgressView(Cards card) {
        View progressView = getLayoutInflater().inflate(R.layout.card_type_progress, null);
        if ( card.getTitle() != null &&
                card.getTitle().getText() != null )
        {
            TextView titleTextView = (TextView)progressView.findViewById(R.id.card_title_textview);
            titleTextView.setText(card.getTitle().getText());
        }
        else
        {
            LinearLayout cardTitleLayout = (LinearLayout)progressView.findViewById(R.id.card_title_layout);
            cardTitleLayout.setVisibility(View.INVISIBLE);
        }

        TextView scoreTextView = (TextView)progressView.findViewById(R.id.card_score_textview);
        ProgressBar progressBar = (ProgressBar)progressView.findViewById(R.id.progressBar);
        scoreTextView.setText( String.valueOf(card.getScore()));

        int progressValue = (int)(card.getPercentage()*100);
        progressBar.setSecondaryProgress(progressValue);
        containerLayout.addView(progressView);

        int topMargin = card.isAppend()?SMALLER_MARGIN:BIGGER_MARGIN;
        setMargins(progressView, 0, topMargin, 0, 0);
    }

    public void reviewTestButtonClicked(View view)
    {
        Intent resultIntent = new Intent();
        setResult(Activity.RESULT_OK, resultIntent);

        finish();
    }

    public void backToMainButtonClicked(View view)
    {
        Dialog dialog = createLeavingTheReviewDialog();
        dialog.show();
    }

    public void backToMain()
    {
        Intent resultIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, resultIntent);

        finish();
    }

    /*
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
    */

    public static void setMargins (View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.setLayoutParams(p);
        }
    }

    public Dialog createLeavingTheReviewDialog() {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Leaving the review?");
        String messageString = "Are you sure you want to quit the review?";

        builder.setMessage(
                messageString)
                .setPositiveButton("Yes, quit",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                backToMain();
                            }
                        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        return builder.create();
    }

}
