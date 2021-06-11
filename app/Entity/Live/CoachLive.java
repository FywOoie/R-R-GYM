package app.Entity.Live;

public class CoachLive {
    private int liveID; // Unique id of this live
    private String coachName; // The coach who launches the live broadcast

    private String scheduledStartTime; // Scheduled time of the live course
    private String liveData;
    private String liveTitle; // The title of the live broadcast
    private int week; // The day in a week.
    private String userID;

    public String getLiveData() { return liveData; }

    public int getWeek() {
        return week;
    }

    public int getLiveID() {
        return liveID;
    }

    public String getCoachName() {
        return coachName;
    }

    public String getLiveTitle() {
        return liveTitle;
    }

    public String getUserID() {
        return userID;
    }

    public String getScheduledStartTime() {
        return scheduledStartTime;
    }
}
