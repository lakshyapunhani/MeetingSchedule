package com.example.meetingscheduler;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Meeting
{
    @SerializedName("start_time")
    @Expose
    private String start_time;

    @SerializedName("end_time")
    @Expose
    private String end_time;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("participants")
    @Expose
    private String[] participants;

    public String getStart_time ()
    {
        return start_time;
    }

    public void setStart_time (String start_time)
    {
        this.start_time = start_time;
    }

    public String getEnd_time ()
    {
        return end_time;
    }

    public void setEnd_time (String end_time)
    {
        this.end_time = end_time;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String[] getParticipants ()
    {
        return participants;
    }

    public void setParticipants (String[] participants)
    {
        this.participants = participants;
    }
}
