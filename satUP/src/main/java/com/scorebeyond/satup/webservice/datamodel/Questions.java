package com.scorebeyond.satup.webservice.datamodel;

public class Questions
{
    private String[] tags;
    private String id;
    private String assets;
    private String app;
    private String text;
    private String category;
    private String[] subjects;
    private String explanation;
    private Answers[] answers;
    private String type;
    private String[] solution;
    private String asset_url;

    public String[] getTags ()
    {
        return tags;
    }

    public void setTags (String[] tags)
    {
        this.tags = tags;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getAssets ()
    {
        return assets;
    }

    public void setAssets (String assets)
    {
        this.assets = assets;
    }

    public String getApp ()
    {
        return app;
    }

    public void setApp (String app)
    {
        this.app = app;
    }

    public String getText ()
    {
        return text;
    }

    public void setText (String text)
    {
        this.text = text;
    }

    public String getCategory ()
    {
        return category;
    }

    public void setCategory (String category)
    {
        this.category = category;
    }

    public String[] getSubjects ()
    {
        return subjects;
    }

    public void setSubjects (String[] subjects)
    {
        this.subjects = subjects;
    }

    public String getExplanation ()
    {
        return explanation;
    }

    public void setExplanation (String explanation)
    {
        this.explanation = explanation;
    }

    public Answers[] getAnswers ()
    {
        return answers;
    }

    public void setAnswers (Answers[] answers)
    {
        this.answers = answers;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    public String[] getSolution ()
    {
        return solution;
    }

    public void setSolution (String[] solution)
    {
        this.solution = solution;
    }

    public String getAsset_url ()
    {
        return asset_url;
    }

    public void setAsset_url (String asset_url)
    {
        this.asset_url = asset_url;
    }
}