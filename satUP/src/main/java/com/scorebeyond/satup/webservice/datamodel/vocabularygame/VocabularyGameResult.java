package com.scorebeyond.satup.webservice.datamodel.vocabularygame;

import java.util.Map;

/**
 * Created by olcayay on 03/02/15.
 */
public class VocabularyGameResult {

    private int total_time;
    private Map<String, Integer> word_scores;

    private VocabularyGameResultItem[] results;

    private int score;


    public int getTotal_time ()
    {
        return total_time;
    }

    public void setTotal_time (int total_time)
    {
        this.total_time = total_time;
    }

    public VocabularyGameResultItem[] getResults ()
    {
        return results;
    }

    public void setResults (VocabularyGameResultItem[] results)
    {
        this.results = results;
    }

    public int getScore ()
    {
        return score;
    }

    public void setScore (int score)
    {
        this.score = score;
    }

    public Map<String, Integer> getWord_scores ()
    {
        return word_scores;
    }

    public void setWord_scores (Map<String, Integer> word_scores)
    {
        this.word_scores = word_scores;
    }
}
