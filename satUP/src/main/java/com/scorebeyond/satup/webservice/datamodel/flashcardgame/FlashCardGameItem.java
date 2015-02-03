package com.scorebeyond.satup.webservice.datamodel.flashcardgame;

/**
 * Created by olcayay on 03/02/15.
 */
public class FlashCardGameItem {
    private String definition;

    private String name;

    private String[] synonyms;

    public String getDefinition ()
    {
        return definition;
    }

    public void setDefinition (String definition)
    {
        this.definition = definition;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String[] getSynonyms ()
    {
        return synonyms;
    }

    public void setSynonyms (String[] synonyms)
    {
        this.synonyms = synonyms;
    }
}
