package com.scorebeyond.satup.webservice.datamodel.test;

public class QuestionResult {
	String test_id;
	String question_id;
	String user_id;
	int time_spent;
	String[] answers;
	int result;
	
	public QuestionResult(String test_id, String question_id, String user_id,
			int time_spent, String answer, int result) {
		super();
		this.test_id = test_id;
		this.question_id = question_id;
		this.user_id = user_id;
		this.time_spent = time_spent;
		this.answers = new String[]{ answer};
		this.result = result;
	}	
}
