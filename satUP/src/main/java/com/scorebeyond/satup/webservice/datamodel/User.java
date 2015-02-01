package com.scorebeyond.satup.webservice.datamodel;

public class User
{
    private String username;
    private String email;
    private String created_at;
    private String user_id;
    private String security_token;
    
    public String getUsername ()
    {
        return username;
    }

    public void setUsername (String username)
    {
        this.username = username;
    }

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    public String getCreated_at ()
    {
        return created_at;
    }

    public void setCreated_at (String created_at)
    {
        this.created_at = created_at;
    }

    public String getUser_id ()
    {
        return user_id;
    }

    public void setUser_id (String user_id)
    {
        this.user_id = user_id;
    }

    public String getSecurity_token ()
    {
        return security_token;
    }

    public void setSecurity_token (String security_token)
    {
        this.security_token = security_token;
    }
}
