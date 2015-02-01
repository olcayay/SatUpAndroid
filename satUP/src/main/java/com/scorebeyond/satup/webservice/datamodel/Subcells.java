package com.scorebeyond.satup.webservice.datamodel;

public class Subcells
{
    private boolean append;
    private Title title;
    private Subtitle subtitle;
    private String type;

    public boolean isAppend() {
		return append;
	}

	public void setAppend(boolean append) {
		this.append = append;
	}

	public Title getTitle ()
    {
        return title;
    }

    public void setTitle (Title title)
    {
        this.title = title;
    }

    public Subtitle getSubtitle ()
    {
        return subtitle;
    }

    public void setSubtitle (Subtitle subtitle)
    {
        this.subtitle = subtitle;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }
}