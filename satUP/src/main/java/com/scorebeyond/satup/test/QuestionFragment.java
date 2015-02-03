package com.scorebeyond.satup.test;

import com.scorebeyond.satup.R;
import com.scorebeyond.satup.SBApplication;
import com.scorebeyond.satup.webservice.datamodel.test.Questions;
import com.scorebeyond.satup.webservice.datamodel.test.Test;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class QuestionFragment extends Fragment {

    private Questions question;
    ListView answerListView;
    AnswerAdapter answerAdapter = null;
    int selectedSelection = -1;
    private int questionIndex;
    private LinearLayout wrapperLayout;
    private LinearLayout solutionLayout;
    private boolean isReviewModeActive;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.question_page_layout, container,
                false);

        //TextView questionTextTV = (TextView) v.findViewById(R.id.question_text_textview);

        View header = getActivity().getLayoutInflater().inflate(R.layout.question_page_header, null);

        WebView questionTextWV = (WebView) header.findViewById(R.id.question_text_webview);
        TextView questionExplanationTV = (TextView) header.findViewById(R.id.explanation_textview);
        answerListView = (ListView)v.findViewById(R.id.answerList);
        wrapperLayout = (LinearLayout)v.findViewById(R.id.wrapperLayout);
        solutionLayout = (LinearLayout)v.findViewById(R.id.solutionLayout);

        isReviewModeActive = ((SlidingMenuTestActivity)getActivity()).isReviewModeActive();

//		answerListView.setOnTouchListener(new OnTouchListener() {
//
//		    public boolean onTouch(View v, MotionEvent event) {
//		        if (event.getAction() == MotionEvent.ACTION_MOVE) {
//		            return true; // Indicates that this has been handled by you and will not be forwarded further.
//		        }
//		        return false;
//		    }
//		});

        questionIndex = getArguments().getInt("index");
        selectedSelection = ((SlidingMenuTestActivity)getActivity()).getSelection(questionIndex);
        Test test = ((SBApplication) getActivity().getApplicationContext())
                .getTest();
        question = test.getQuestions()[questionIndex];

        questionExplanationTV.setText(question.getExplanation());

        String questionText = question.getText();
        String questionTextWithFullPathSrc = questionText.replace("src=\"",
                "src=\"" + question.getAsset_url());
//		URLImageParser p = new URLImageParser(questionTextTV, getActivity());
//		// Spanned spanned =
//		// Html.fromHtml(questionTextWithFullPathSrc,getImageHTML(),null);
//		Spanned spanned = Html.fromHtml(questionTextWithFullPathSrc, p, null);
//		questionTextTV.setText(spanned);

        //getActivity().getWindow().requestFeature(Window.FEATURE_PROGRESS);
        questionTextWV.getSettings().setJavaScriptEnabled(true);
        questionTextWV.setWebViewClient(new WebViewClient());
        //questionTextWV.getSettings().setBuiltInZoomControls(true);

        //questionTextWV.setInitialScale(1);
        questionTextWV.getSettings().setUseWideViewPort(true);
        questionTextWV.getSettings().setLoadWithOverviewMode(true);


        questionTextWV.loadData(questionTextWithFullPathSrc, "text/html", "UTF-8");
        //questionTextWV.loadUrl("http://www.google.com");

        prepareSolutionScreen();



        answerAdapter = new AnswerAdapter(getActivity(), R.layout.answer_list_item, question.getAnswers());
        answerAdapter.setQuestion(question);
        answerListView.setAdapter(answerAdapter);
        answerListView.setOnItemClickListener( new OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                if ( isReviewModeActive )
                    return;

                selectedSelection = position - 1;
                answerAdapter.selectedItemChanged(selectedSelection);
                answerAdapter.notifyDataSetChanged();
                notifyActivityForSelection(selectedSelection);
            }

        });

        if ( selectedSelection > -1 )
        {
            answerListView.setSelection(selectedSelection);
            answerAdapter.selectedItemChanged(selectedSelection);
        }

        answerListView.addHeaderView(header);

        return v;
    }

    private void prepareSolutionScreen() {

        for ( int i = 0; i < question.getSolution().length; i++)
        {
            addSolution(i);
        }

    }

    private void addSolution(final int index) {

        View solutionView = getActivity().getLayoutInflater().inflate(R.layout.solution_step_layout, null);
        final WebView solutionWV = (WebView) solutionView.findViewById(R.id.solution_webview);
        final TextView hideOrShowTV = (TextView) solutionView.findViewById(R.id.hide_or_show_solution_textview);


        String solutionText = question.getSolution()[index];
        String solutionTextWithFullPathSrc = solutionText.replace("src=\"",
                "src=\"" + question.getAsset_url());
        solutionWV.getSettings().setJavaScriptEnabled(true);
        solutionWV.setWebViewClient(new WebViewClient());
        solutionWV.loadData(solutionTextWithFullPathSrc, "text/html", "UTF-8");

//		solutionWV.getSettings().setUseWideViewPort(true);
//		solutionWV.getSettings().setLoadWithOverviewMode(true);


        if ( index == 0 )
        {
            hideOrShowTV.setText(getActivity().getResources().getString(R.string.hide_step) + " " + ( index + 1 ));
            solutionWV.setVisibility(View.VISIBLE);
        }
        else
        {
            hideOrShowTV.setText(getActivity().getResources().getString(R.string.show_step) + " " + (index + 1));
            solutionWV.setVisibility(View.GONE);
        }

        hideOrShowTV.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if ( solutionWV.getVisibility() == View.VISIBLE )
                {
                    hideOrShowTV.setText(getActivity().getResources().getString(R.string.show_step) + " " + (index + 1));
                    solutionWV.setVisibility(View.GONE);
                }
                else
                {
                    hideOrShowTV.setText(getActivity().getResources().getString(R.string.hide_step) + " " + (index + 1));
                    solutionWV.setVisibility(View.VISIBLE);
                }
            }
        });

        solutionLayout.addView(solutionView);

    }

    protected void notifyActivityForSelection(int position) {
        ((SlidingMenuTestActivity)getActivity()).setSelection(questionIndex, position);
    }

    public static QuestionFragment newInstance(int index) {

        QuestionFragment f = new QuestionFragment();
        Bundle b = new Bundle();
        b.putInt("index", index);

        f.setArguments(b);

        return f;
    }

    /*
    public ImageGetter getImageHTML() {
        ImageGetter imageGetter = new ImageGetter() {
            public Drawable getDrawable(String source) {
                try {
                    Drawable drawable = Drawable.createFromStream(new URL(
                            source).openStream(), "src name");
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                            drawable.getIntrinsicHeight());
                    return drawable;
                } catch (IOException exception) {
                    Log.v("IOException", exception.getMessage());
                    return null;
                }
            }
        };
        return imageGetter;
    }
    */

    public void showSolution() {
        wrapperLayout.setVisibility(View.INVISIBLE);
        solutionLayout.setVisibility(View.VISIBLE);
    }

    public void showQuestion() {
        wrapperLayout.setVisibility(View.VISIBLE);
        solutionLayout.setVisibility(View.GONE);

    }
}