package com.scorebeyond.satup.webservice.datamodel.vocabularygame;

/**
 * Created by olcayay on 03/02/15.
 */

import java.util.Map;

public class VocabularyGame {
    private String id;

    private String time;

    private Map<String, String> word_mapping;

    private String user_id;

    private String type;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getTime ()
    {
        return time;
    }

    public void setTime (String time)
    {
        this.time = time;
    }

    public Map<String, String> getWord_mapping ()
    {
        return word_mapping;
    }

    public void setWord_mapping (Map<String, String> word_mapping)
    {
        this.word_mapping = word_mapping;
    }

    public String getUser_id ()
    {
        return user_id;
    }

    public void setUser_id (String user_id)
    {
        this.user_id = user_id;
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
