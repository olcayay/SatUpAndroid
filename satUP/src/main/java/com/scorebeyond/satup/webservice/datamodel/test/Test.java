package com.scorebeyond.satup.webservice.datamodel.test;

import com.scorebeyond.satup.webservice.datamodel.test.Questions;

public class Test
{
    private Questions[] questions;
    private String test_id;

    public Questions[] getQuestions ()
    {
        return questions;
    }

    public void setQuestions (Questions[] questions)
    {
        this.questions = questions;
    }

    public String getTest_id ()
    {
        return test_id;
    }

    public void setTest_id (String test_id)
    {
        this.test_id = test_id;
    }
}