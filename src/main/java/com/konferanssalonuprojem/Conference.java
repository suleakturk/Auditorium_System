package com.konferanssalonuprojem;

public class Conference {
    private String conferenceName;
    private String conferenceTime;
    private String fullSeat;

    public Conference() {
    }

    public Conference(String conferenceName, String conferenceTime, String fullSeat) {
        this.conferenceName = conferenceName;
        this.conferenceTime = conferenceTime;
        this.fullSeat = fullSeat;
    }

    public String getFullSeat() {
        return fullSeat;
    }

    public void setFullSeat(String fullSeat) {
        this.fullSeat = fullSeat;
    }

    public String getConferenceName() {
        return conferenceName;
    }

    public void setConferenceName(String conferenceName) {
        this.conferenceName = conferenceName;
    }

    public String getConferenceTime() {
        return conferenceTime;
    }

    public void setConferenceTime(String conferenceTime) {
        this.conferenceTime = conferenceTime;
    }
}
