package com.scorebeyond.satup.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.scorebeyond.satup.R;
import com.scorebeyond.satup.SBApplication;
import com.scorebeyond.satup.webservice.RetrofitInterface;
import com.scorebeyond.satup.webservice.TestRequest;
import com.scorebeyond.satup.webservice.WebServiceAdapter;
import com.scorebeyond.satup.webservice.datamodel.Answers;
import com.scorebeyond.satup.webservice.datamodel.QuestionResult;
import com.scorebeyond.satup.webservice.datamodel.QuestionResultList;
import com.scorebeyond.satup.webservice.datamodel.Questions;
import com.scorebeyond.satup.webservice.datamodel.Test;
import com.scorebeyond.satup.webservice.datamodel.TestStatResult;
import com.scorebeyond.satup.webservice.datamodel.User;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SlidingMenuTestActivity extends ActionBarActivity {

    private static final int REQUEST_CODE = 1;
    Test test = null;
    private ListView mDrawerList;
    private com.scorebeyond.satup.test.NavDrawerListAdapter navDrawerListAdapter;
    private ArrayList<NavDrawerItem> navDrawerItems = new ArrayList<NavDrawerItem>();
    private ViewPager viewPager;
    List<Integer> selectedSelectionList = new ArrayList<Integer>();
    List<Integer> timeSpentList = new ArrayList<Integer>();
    List<Boolean> markedPageList = new ArrayList<Boolean>();
    List<Boolean> reportedPageList = new ArrayList<Boolean>();
    List<Boolean> isSolutionModeModeActive = new ArrayList<Boolean>();
    //int testDurationMs = 0;
    private Chronometer testDurationChronometer;
    //private TimerTask task;
    private SlidingMenu menu;
    private TextView actionBarTitle;
    private ImageButton actionBarMenuButton;
    private ImageButton flagImageButton;
    protected int currentPage;
    private RelativeLayout testPageWrapperLayout;
    private LinearLayout bottomLayout;
    private RetrofitInterface retrofitInterface;
    private User user;
    private RelativeLayout practiceReadyLayout;
    private View leftMenuView;
    private RelativeLayout generatingPracticeLayout;
    private long lastPause;
    int oldPagePosition = 0;
    long durationMS = 0;
    private QuestionPagerAdapter questionPagerAdapter;
    public FinishTestFragment finishTestFragment;
    boolean isReviewModeActive = false;
    private LinearLayout reviewModeBottomLayout;
    public HashMap questionFragmentList;
    private Button leftMenuFinishButton;
    private RelativeLayout reviewModeBottomLayoutFirstRow;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_sliding_test);

        // setBehindContentView(R.layout.test_sliding_menu);

        bottomLayout = (LinearLayout)findViewById(R.id.bottomLayout);
        testPageWrapperLayout = (RelativeLayout)findViewById(R.id.testPageWrapperLayout);
        testPageWrapperLayout.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
				/*
				if ( bottomLayout.getVisibility() == View.VISIBLE )
				{	
					bottomLayout.setVisibility(View.INVISIBLE);
					getActionBar().hide();
				}
				else
				{	
					bottomLayout.setVisibility(View.VISIBLE);
					getActionBar().show();
				}
				*/
            }
        });

        LayoutInflater inflater = (LayoutInflater) getApplicationContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        leftMenuView = inflater.inflate(R.layout.test_sliding_menu, null);

        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeBehind(SlidingMenu.TOUCHMODE_MARGIN);
        menu.setShadowWidth(15);
        menu.setFadeDegree(0.0f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
        menu.setBehindWidth(400);
        menu.setMenu(leftMenuView);

        test = ((SBApplication) getApplicationContext()).getTest();
        testDurationChronometer = (Chronometer) findViewById(R.id.testChronometer);
        practiceReadyLayout = (RelativeLayout)findViewById(R.id.practice_ready_layout);
        generatingPracticeLayout = (RelativeLayout)findViewById(R.id.practice_generating_layout);
        bottomLayout = (LinearLayout)findViewById(R.id.bottomLayout);
        reviewModeBottomLayout = (LinearLayout)findViewById(R.id.reviewModeBottomLayout);
        leftMenuFinishButton = (Button)leftMenuView.findViewById(R.id.finish_button);
        reviewModeBottomLayoutFirstRow = (RelativeLayout)findViewById(R.id.reviewModeBottomLayoutFirstRow);

        getActionBar().setDisplayShowCustomEnabled(true);
        // getActionBar().setDisplayHomeAsUpEnabled(true);

        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.test_action_bar, null);
        getActionBar().setCustomView(mCustomView);
        // getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM |
        // ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_HOME_AS_UP);
        // getActionBar().setDisplayShowCustomEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);

        actionBarTitle = (TextView) findViewById(R.id.actionBarTitle);
        actionBarMenuButton = (ImageButton) findViewById(R.id.actionBarMenuButton);
        flagImageButton = (ImageButton) findViewById(R.id.flagImageButton);

        getActionBar().setBackgroundDrawable(new ColorDrawable(0xff3498DB));


        ImageView quitImageView = (ImageView) leftMenuView
                .findViewById(R.id.quit_imageview);
        Button finishButton = (Button) leftMenuView
                .findViewById(R.id.finish_button);

        quitImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showQuitFromTestTestDialog();
            }
        });

        finishButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if ( isReviewModeActive )
                {
                    menu.showContent();
                    gotoTestStatActivity();
                }
                else
                {
                    int position = test.getQuestions().length;
                    viewPager.setCurrentItem(position);
                    menu.showContent();
                }
            }
        });

        actionBarMenuButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                menu.showMenu();
            }
        });

        flagImageButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (currentPage < markedPageList.size()) {

                    if ( isReviewModeActive )
                    {
                        boolean isReported = reportedPageList.get(currentPage);
                        reportedPageList.set(currentPage, !isReported);

                        if (isReported)
                            flagImageButton
                                    .setImageResource(R.drawable.ic_flagoff);
                        else
                            flagImageButton
                                    .setImageResource(R.drawable.ic_flagon);

                        showNotImplementedWarning();
                    }
                    else
                    {
                        boolean isMarked = markedPageList.get(currentPage);
                        markedPageList.set(currentPage, !isMarked);

                        if (isMarked)
                            flagImageButton
                                    .setImageResource(R.drawable.ic_bmark_off);
                        else
                            flagImageButton
                                    .setImageResource(R.drawable.ic_bmark_on);

                        navDrawerListAdapter.notifyDataSetChanged();

                    }

                }
            }
        });


        retrofitInterface = ((SBApplication)getApplicationContext()).getRetrofitInterface();
        user = ((SBApplication)getApplicationContext()).getAppUser();

		/*
		 * View header =
		 * getLayoutInflater().inflate(R.layout.left_slider_header, null); View
		 * footer = getLayoutInflater().inflate(R.layout.left_slider_footer,
		 * null);
		 * 
		 * mDrawerList.addHeaderView(header); mDrawerList.addFooterView(footer);
		 */

        getActionBar().hide();

        Bundle bundle = getIntent().getExtras();
        if ( bundle!= null)
        {
            int questionNumber = bundle.getInt("questionNumber");
            String[] testContentArray = bundle.getStringArray("testContent");
            createTest(questionNumber, testContentArray);
        }

        //questionFragmentList = new HashMap<>();
        questionFragmentList = new HashMap();
    }

    private void fillTimeSpentList() {
        //for (Questions questions : test.getQuestions()) {
        for (int i = 0; i < test.getQuestions().length;i++) {
            timeSpentList.add(0);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        lastPause = SystemClock.elapsedRealtime();
        testDurationChronometer.stop();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //testDurationChronometer.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if ( test != null )
        {
            testDurationChronometer.setBase(testDurationChronometer.getBase() + SystemClock.elapsedRealtime() - lastPause);
            testDurationChronometer.start();
        }
    }

    private void fillSelectedSelectionList() {
        //for (Questions questions : test.getQuestions()) {
        for (int i = 0; i < test.getQuestions().length;i++) {
            selectedSelectionList.add(-1);
            markedPageList.add(false);
            isSolutionModeModeActive.add(false);
            reportedPageList.add(false);
        }
    }

    public int getUnansweredQuestionNumber() {

        int count = 0;
        for (int i = 0; i < test.getQuestions().length; i++) {
            if (selectedSelectionList.get(i) == -1)
                count++;
        }

        return count;
    }

    protected void setPageTitle(int position) {

        String title;
        if ( isReviewModeActive )
            title = "See Results";
        else
            title = "Finish Test";

        if (position < test.getQuestions().length) {
            title = position + 1 + " / " + test.getQuestions().length;
        }

        // getActionBar().setTitle(title);
        actionBarTitle.setText(title);
    }

    private void fillNavDrawerItems() {

        int questionCount = test.getQuestions().length;
        for (int i = 0; i < questionCount; i++) {
            String title = "Question #" + (i + 1);
            navDrawerItems.add(new NavDrawerItem(title));
        }
    }

    private class QuestionPagerAdapter extends FragmentPagerAdapter {

        public QuestionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {

            if (pos == test.getQuestions().length)
            {
                finishTestFragment = FinishTestFragment.newInstance();

                return finishTestFragment;
            }
            else
            {
                QuestionFragment questionFragment = QuestionFragment.newInstance(pos);
                //if ( pos < questionFragmentList.size() )
                {
                    questionFragmentList.put(pos, questionFragment);
                }

                return questionFragment;
            }
        }

        @Override
        public int getCount() {
            return test.getQuestions().length + 1;
        }
    }

    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // display view for selected nav drawer item
            displayView(position);
        }
    }

    private void displayView(int position) {
        viewPager.setCurrentItem(position);

        mDrawerList.setItemChecked(position, true);
        mDrawerList.setSelection(position);
        menu.showContent();
        // setTitle(navMenuTitles[position]);
        // mDrawerLayout.closeDrawer(leftDrawerLayout);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                menu.toggle(true);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        // boolean drawerOpen = mDrawerLayout.isDrawerOpen(leftDrawerLayout);
        // menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

	/*
	 * @Override protected void onPostCreate(Bundle savedInstanceState) {
	 * super.onPostCreate(savedInstanceState); // Sync the toggle state after
	 * onRestoreInstanceState has occurred. //mDrawerToggle.syncState(); }
	 */

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        // mDrawerToggle.onConfigurationChanged(newConfig);
    }

    public void setSelection(int questionIndex, int selection) {
        selectedSelectionList.set(questionIndex, selection);
    }

    public int getSelection(int questionIndex) {
        return selectedSelectionList.get(questionIndex);
    }

    @Override
    public void onBackPressed() {
        showQuitFromTestTestDialog();
    }

    public void quitFromTestClicked(View view) {
        showQuitFromTestTestDialog();
    }

    private void showQuitFromTestTestDialog() {
        Dialog dialog = createQuitFromTestTestDialog();
        if (dialog != null)
            dialog.show();
    }

    public Dialog createQuitFromTestTestDialog() {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if ( isReviewModeActive )
            builder.setTitle("Leaving the review?");
        else
            builder.setTitle("Leaving the practice in progress?");

        String messageString;
        if ( isReviewModeActive )
            messageString = "Are you sure you want to quit the review?";
        else
            messageString = "Hard workers rarely quit. Are you sure you want to quit?";

        builder.setMessage(
                messageString)
                .setPositiveButton("Yes, quit",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                quitFromTest();
                            }
                        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    protected void quitFromTest() {
        finish();
    }

    public List<Integer> getSelectedSelectionList() {
        return selectedSelectionList;
    }

    public List<Boolean> getMarkedPageList() {
        return markedPageList;
    }

    public void sendTestResults() {
        if ( isReviewModeActive )
        {
            gotoTestStatActivity();
        }
        else
        {
            QuestionResultList resultList = new QuestionResultList();
            int questionCount = test.getQuestions().length;
            final String testID = test.getTest_id();
            String userID = user.getUser_id();

            for (int i = 0; i < questionCount; i++) {
                Questions question = test.getQuestions()[i];
                String questionID = question.getId();
                int selectedAnswer = selectedSelectionList.get(i);
                int timeSpent = ( timeSpentList.get(i) / 1000);
                String answerID = null;
                int result = 0;

                if ( selectedAnswer >= 0 )
                {
                    Answers answer = question.getAnswers()[selectedAnswer];
                    answerID = answer.getId();
                    if ( answer.getCorrect() )
                        result = 1;
                    else
                        result = -1;
                }

                QuestionResult questionResult = new QuestionResult(testID, questionID, userID, timeSpent, answerID, result);
                resultList.addQuestionResult(questionResult);
            }

            retrofitInterface.sendTestResult(user.getSecurity_token(), user.getUser_id(), testID, resultList, new Callback<Response>(){

                @Override
                public void success(Response result, Response arg1) {
                    Toast.makeText(getApplicationContext(), "Test sonuçları gönderildi " + test.getTest_id(), Toast.LENGTH_SHORT).show();
                    getTestStat(user.getSecurity_token(), user.getUser_id(), testID);
                }

                @Override
                public void failure(RetrofitError arg0) {
                    Toast.makeText(getApplicationContext(), "Test Error", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    protected void getTestStat(String security_token, String user_id,
                               String testID) {

        retrofitInterface.getTestStat(security_token, user_id, testID, new Callback<Response>(){

            @Override
            public void success(Response result, Response arg1) {
                TestStatResult testStatResult = WebServiceAdapter.getTestStatResultFromResponse(result);
                if (  testStatResult != null )
                {
                    Toast.makeText(getApplicationContext(), "Test sonu�lar� al�nd� " + test.getTest_id(), Toast.LENGTH_SHORT).show();
                    testStatIsReady(testStatResult);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Test Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void failure(RetrofitError arg0) {

            }
        });
    }

    protected void testStatIsReady(TestStatResult testStatResult) {
        ((SBApplication)getApplicationContext()).setTestStatResult(testStatResult);
        gotoTestStatActivity();
    }

    private void gotoTestStatActivity() {
        Intent intent = new Intent(this,TestStatActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE) {
            if(resultCode == RESULT_OK){
                setReviewMode(true);
            }
            if (resultCode == RESULT_CANCELED) {
                finish();
            }
        }
    }

    private void setReviewMode(boolean b) {
        isReviewModeActive = b;
        if ( isReviewModeActive )
        {
            if ( currentPage == test.getQuestions().length )
                reviewModeBottomLayoutFirstRow.setVisibility(View.INVISIBLE);

            reviewModeBottomLayout.setVisibility(View.VISIBLE);

            bottomLayout.setVisibility(View.INVISIBLE);

            if ( finishTestFragment!= null )
                finishTestFragment.setReviewMode(true);

            if ( currentPage == test.getQuestions().length )
                actionBarTitle.setText("See Results");
        }
        else
        {
            reviewModeBottomLayout.setVisibility(View.INVISIBLE);
            bottomLayout.setVisibility(View.VISIBLE);

            if ( finishTestFragment!= null )
                finishTestFragment.setReviewMode(false);

        }

        resetMarkedPageList();
        navDrawerListAdapter.notifyDataSetChanged();

        leftMenuFinishButton.setText(getResources().getString(R.string.see_results));

    }

    private void resetMarkedPageList() {

        markedPageList.clear();
        //for (Questions questions : test.getQuestions()) {
        for (int i = 0; i < test.getQuestions().length;i++) {
            markedPageList.add(false);
        }
    }

    public void startPracticeButtonClicked(View view)
    {
        practiceReadyLayout.setVisibility(View.INVISIBLE);
        getActionBar().show();
        testDurationChronometer.setBase(SystemClock.elapsedRealtime());
        testDurationChronometer.start();
        durationMS = System.currentTimeMillis();
    }

    public void cancelButtonClicked(View view)
    {
        finish();
    }

    public void createTest(int questionNumber, String[] testContentArray)
    {
        retrofitInterface.createTest(user.getSecurity_token(), user.getUser_id(), new TestRequest(questionNumber, testContentArray), new Callback<Response>(){

            @Override
            public void success(Response result, Response arg1) {
                Test test = WebServiceAdapter.getTestObjectFromResponse(result);
                if (  test != null )
                {
                    testIsReady(test);
                    Toast.makeText(getApplicationContext(), "Test oluşturuldu " + test.getTest_id(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Test Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void failure(RetrofitError arg0) {
            }
        });


    }

    protected void testIsReady(Test testObject) {
        this.test = testObject;
        ((SBApplication)getApplicationContext()).setTest(testObject);
        setPageTitle(0);

        fillNavDrawerItems();
        fillSelectedSelectionList();
        fillTimeSpentList();

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        questionPagerAdapter = new QuestionPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(questionPagerAdapter);
        viewPager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
                setPageTitle(position);
                if (position == test.getQuestions().length)
                {
                    flagImageButton.setVisibility(View.INVISIBLE);

                    if (isReviewModeActive)
                    {
                        reviewModeBottomLayout.setVisibility(View.VISIBLE);
                        reviewModeBottomLayoutFirstRow.setVisibility(View.INVISIBLE);
                    }
                    else
                    {
                        findViewById(R.id.edit_view).setVisibility(View.INVISIBLE);
                        findViewById(R.id.calculator_view).setVisibility(View.INVISIBLE);
                    }
                }
                else {
                    flagImageButton.setVisibility(View.VISIBLE);

                    if ( isReviewModeActive )
                    {
                        boolean isReported = reportedPageList.get(currentPage);

                        if (isReported)
                            flagImageButton
                                    .setImageResource(R.drawable.ic_flagon);
                        else
                            flagImageButton
                                    .setImageResource(R.drawable.ic_flagoff);
                    }
                    else
                    {
                        boolean isMarked = markedPageList.get(currentPage);

                        if ( isMarked )
                            flagImageButton.setImageResource(R.drawable.ic_bmark_on);
                        else
                            flagImageButton.setImageResource(R.drawable.ic_bmark_off);
                    }

                    if (isReviewModeActive)
                    {
                        reviewModeBottomLayout.setVisibility(View.VISIBLE);
                        reviewModeBottomLayoutFirstRow.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        findViewById(R.id.edit_view).setVisibility(View.VISIBLE);
                        findViewById(R.id.calculator_view).setVisibility(View.VISIBLE);
                    }
                }

                long newDuration = System.currentTimeMillis();
                if ( durationMS > 0 )
                {
                    int elapsedTime = (int)(newDuration - durationMS);
                    if ( oldPagePosition < test.getQuestions().length )
                        timeSpentList.set(oldPagePosition, timeSpentList.get(oldPagePosition) + elapsedTime);
                }

                oldPagePosition = position;
                durationMS = newDuration;

                if ( position == test.getQuestions().length )
                {
                    if ( finishTestFragment != null )
                        finishTestFragment.updateUnansweredText();
                }

                if (currentPage < isSolutionModeModeActive.size())
                {
                    if ( !isSolutionModeModeActive.get(currentPage) )
                    {
                        ((TextView)findViewById(R.id.show_solution_textview)).setText(getResources().getString(R.string.show_question));
                        notifyFragmentAboutSolutionMode();
                    }
                    else
                    {
                        ((TextView)findViewById(R.id.show_solution_textview)).setText(getResources().getString(R.string.show_solution));
                        notifyFragmentAboutSolutionMode();
                    }
                }

                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)viewPager.getLayoutParams();
                viewPager.setLayoutParams(params); //causes layout update
                if ( isReviewModeActive )
                {
                    params.addRule(RelativeLayout.ABOVE, R.id.reviewModeBottomLayout);
                }
                else
                {
                    params.addRule(RelativeLayout.ABOVE, R.id.bottomLayout);
                }

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });

        // mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) leftMenuView
                .findViewById(R.id.list_slidermenu);
        // leftDrawerLayout = (RelativeLayout)
        // findViewById(R.id.leftDrawerLayout);
        navDrawerListAdapter = new NavDrawerListAdapter(
                getApplicationContext(), navDrawerItems);
        navDrawerListAdapter.setActivity(this);
        mDrawerList.setAdapter(navDrawerListAdapter);
        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

        generatingPracticeLayout.setVisibility(View.INVISIBLE);

    }

    public void showSolutionClicked(View view)
    {
        //if ( currentPage < questionFragmentList.size() )
        {
            QuestionFragment questionFragment = (QuestionFragment) questionFragmentList.get(currentPage);
            if (questionFragment != null)
            {
                if ( !isSolutionModeModeActive.get(currentPage) )
                {
                    questionFragment.showSolution();
                    isSolutionModeModeActive.set(currentPage, true);
                    ((TextView)findViewById(R.id.show_solution_textview)).setText(getResources().getString(R.string.show_question));
                }
                else
                {
                    questionFragment.showQuestion();
                    isSolutionModeModeActive.set(currentPage, false);
                    ((TextView)findViewById(R.id.show_solution_textview)).setText(getResources().getString(R.string.show_solution));
                }
            }
        }
    }

    public void notifyFragmentAboutSolutionMode()
    {
        QuestionFragment questionFragment = (QuestionFragment) questionFragmentList.get(currentPage);
        if (questionFragment != null)
        {
            if ( isSolutionModeModeActive.get(currentPage) )
            {
                questionFragment.showSolution();
                ((TextView)findViewById(R.id.show_solution_textview)).setText(getResources().getString(R.string.show_question));
            }
            else
            {
                questionFragment.showQuestion();
                ((TextView)findViewById(R.id.show_solution_textview)).setText(getResources().getString(R.string.show_solution));
            }
        }

    }

    public boolean isReviewModeActive() {
        return isReviewModeActive;
    }

    public void setReviewModeActive(boolean isReviewModeActive) {
        this.isReviewModeActive = isReviewModeActive;
    }

    public void notImplementedMethod(View view)
    {
        showNotImplementedWarning();
    }

    private void showNotImplementedWarning() {
        Toast.makeText(this, "This function is not implemented yet", Toast.LENGTH_SHORT).show();
    }

}
