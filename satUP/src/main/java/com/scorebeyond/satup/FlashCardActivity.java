package com.scorebeyond.satup;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.Toast;
import android.view.View;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.scorebeyond.satup.webservice.RetrofitInterface;
import com.scorebeyond.satup.webservice.WebServiceAdapter;
import com.scorebeyond.satup.webservice.datamodel.flashcardgame.FlashCardGameItem;
import com.scorebeyond.satup.webservice.datamodel.User;

import java.util.List;


public class FlashCardActivity extends ActionBarActivity {

    private User user;
    private RetrofitInterface retrofitInterface;
    private SeekBar seekBarRandomWords;
    private SeekBar seekBarTroubleWords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_card);
        user = ((SBApplication)getApplicationContext()).getAppUser();
        retrofitInterface = ((SBApplication)getApplicationContext()).getRetrofitInterface();
        seekBarRandomWords = (SeekBar)findViewById(R.id.seekBarRandomWords);
        seekBarTroubleWords = (SeekBar)findViewById(R.id.seekBarTroubleWords);
        //getFlashCards();
    }

    private void getFlashCards( int count, String modeString) {
            retrofitInterface.getFlashCards(user.getSecurity_token(), user.getUser_id(), count, modeString, new Callback<Response>() {
                @Override
                public void success(Response result, Response arg1) {
                    List<FlashCardGameItem> flashCardList = WebServiceAdapter.getFlashCardItemsFromResponse(result);
                    if ( flashCardList.size() > 0 )
                    {
                        Toast.makeText(getApplicationContext(),flashCardList.size() + " tane flashcard geldi",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void failure(RetrofitError arg0) {

                }
            });
        }

    public void startStudyingRandomWords(View view)
    {
        getFlashCards(seekBarRandomWords.getProgress(),"random");
    }

    public void startStudyingTroubleWords(View view)
    {
        getFlashCards(seekBarTroubleWords.getProgress(),"trouble");
    }

}
