package com.scorebeyond.satup.webservice.datamodel;
                
public class Profile
{
   private String reminder;
   private String unsubscribed_email;
   private String[] target_dates;
   private String credits;
   private String target_score;
   private String[] upcoming_test_dates;
   private String id;
   private Quota quota;
   private String timezone;
   private String updated_at;
   private String created_at;
   private String grade;
   private String can_redeem_code;

   public String getReminder ()
   {
       return reminder;
   }

   public void setReminder (String reminder)
   {
       this.reminder = reminder;
   }

   public String getUnsubscribed_email ()
   {
       return unsubscribed_email;
   }

   public void setUnsubscribed_email (String unsubscribed_email)
   {
       this.unsubscribed_email = unsubscribed_email;
   }

   public String[] getTarget_dates ()
   {
       return target_dates;
   }

   public void setTarget_dates (String[] target_dates)
   {
       this.target_dates = target_dates;
   }

   public String getCredits ()
   {
       return credits;
   }

   public void setCredits (String credits)
   {
       this.credits = credits;
   }

   public String getTarget_score ()
   {
       return target_score;
   }

   public void setTarget_score (String target_score)
   {
       this.target_score = target_score;
   }

   public String[] getUpcoming_test_dates ()
   {
       return upcoming_test_dates;
   }

   public void setUpcoming_test_dates (String[] upcoming_test_dates)
   {
       this.upcoming_test_dates = upcoming_test_dates;
   }

   public String getId ()
   {
       return id;
   }

   public void setId (String id)
   {
       this.id = id;
   }

   public Quota getQuota ()
   {
       return quota;
   }

   public void setQuota (Quota quota)
   {
       this.quota = quota;
   }

   public String getTimezone ()
   {
       return timezone;
   }

   public void setTimezone (String timezone)
   {
       this.timezone = timezone;
   }

   public String getUpdated_at ()
   {
       return updated_at;
   }

   public void setUpdated_at (String updated_at)
   {
       this.updated_at = updated_at;
   }

   public String getCreated_at ()
   {
       return created_at;
   }

   public void setCreated_at (String created_at)
   {
       this.created_at = created_at;
   }

   public String getGrade ()
   {
       return grade;
   }

   public void setGrade (String grade)
   {
       this.grade = grade;
   }

   public String getCan_redeem_code ()
   {
       return can_redeem_code;
   }

   public void setCan_redeem_code (String can_redeem_code)
   {
       this.can_redeem_code = can_redeem_code;
   } 
}

class Quota
{
    private String quota;
    private String solved;
    private String reset_time;
    
    public String getQuota ()
    {
        return quota;
    }

    public void setQuota (String quota)
    {
        this.quota = quota;
    }

    public String getSolved ()
    {
        return solved;
    }

    public void setSolved (String solved)
    {
        this.solved = solved;
    }

    public String getReset_time ()
    {
        return reset_time;
    }

    public void setReset_time (String reset_time)
    {
        this.reset_time = reset_time;
    }
}