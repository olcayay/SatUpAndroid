package com.scorebeyond.satup.webservice.datamodel.test;

import java.util.ArrayList;
import java.util.List;

public class QuestionResultList {
	List<QuestionResult> results;

	public QuestionResultList() {
		super();
		
		results = new ArrayList<QuestionResult>();
	}
	
	public void addQuestionResult(QuestionResult result)
	{
		results.add(result);
	}

}
