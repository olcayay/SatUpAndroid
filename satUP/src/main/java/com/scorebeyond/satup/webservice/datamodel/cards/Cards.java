package com.scorebeyond.satup.webservice.datamodel.cards;

public class Cards
{
    private boolean append;
    private Title title;
    private float percentage;
    private int score;
    private String type;
    private Subtitle subtitle;
    private Subcells[] subcells;
    private String value;
    private int time;
    
    public Title getTitle ()
    {
        return title;
    }

    public void setTitle (Title title)
    {
        this.title = title;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

	public Subtitle getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(Subtitle subtitle) {
		this.subtitle = subtitle;
	}

	public Subcells[] getSubcells() {
		return subcells;
	}

	public void setSubcells(Subcells[] subcells) {
		this.subcells = subcells;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setAppend(boolean append) {
		this.append = append;
	}

	public void setPercentage(float percentage) {
		this.percentage = percentage;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public boolean isAppend() {
		return append;
	}

	public float getPercentage() {
		return percentage;
	}

	public int getScore() {
		return score;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}


}
			