package app.Entity.Live;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * This class is an entity class for live session.
 * The class contains necessary attributes to describe a
 * live session. Developer can get required information of a
 * live session and record the information to the text file.
 */
public class LiveSession {

    private int liveID; // Unique id of this live
    private String coachName; // The coach who launches the live broadcast
    private String stuName;
    private String scheduledStartTime; // Scheduled time of the live course
    private String liveTitle; // The title of the live broadcast
    private int week; // The day in a week
    private SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    private JsonParser jsonParser = new JsonParser();
    private String selected = "NO"; // Indicate whether a video is selected or not.
    private String liveDate;

    /**
     * This method is the constructor of this class.
     * User must specify the necessary information of the live
     * session before creating an instance of it.
     *
     * @param liveID Unique id of this live
     * @param coachName The coach who launches the live broadcast
     * @param liveTitle The title of the live broadcast
     * @param scheduledStartTime Scheduled time of the live course
     * @param week The day in a week
     */
    public LiveSession(int liveID, String coachName, String liveTitle, String scheduledStartTime, int week, Date date) {
        this.liveID = liveID;
        this.coachName = coachName;
        this.liveTitle = liveTitle;
        this.scheduledStartTime = scheduledStartTime;
        this.week = week;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        this.liveDate = sdf.format(date);
    }

    /**
     * This method is getter for the variable {@code week}
     *
     * @return The value of {@code week}
     */
    public int getWeek() {
        return week;
    }

    /**
     * This method is getter for the variable {@code liveID}
     *
     * @return The value of {@code liveID}
     */
    public int getLiveID() {
        return liveID;
    }

    /**
     * This method is getter for the variable {@code coachName}
     *
     * @return The value of {@code coachName}
     */
    public String getCoachName() {
        return coachName;
    }

    /**
     * This method is getter for the variable {@code liveTitle}
     *
     * @return The value of {@code liveTitle}
     */
    public String getLiveTitle() {
        return liveTitle;
    }

    /**
     * This method is getter for the variable {@code selected}
     *
     * @return The value of {@code selected}
     */
    public String getSelected() {
        return selected;
    }

    /**
     * This method is getter for the variable {@code liveDate}
     *
     * @return The value of {@code liveDate}
     */
    public String getLiveDate() {
        return liveDate;
    }

    /**
     * This method is getter for the variable {@code coachName}
     *
     * @return The value of {@code coachName}
     */
    public String getScheduledStartTime() {
        return scheduledStartTime;
    }

    /**
     * This method is called when a live is booked by the user.
     */
    public void liveSelect() {
        this.selected = "YES";
    }

    /**
     * This method is called when a live is booked by the user.
     */
    public void liveCancel() {
        this.selected = "NO";
    }

    /**
     * This method is used to record the detailed information of live for user.
     * This method is called when the live-book record is updated.
     */
    public void liveRecordUser(String stuName) {
        JsonObject jsonObject = new JsonObject();
        SimpleDateFormat formatter_new = new SimpleDateFormat("yyyy-MM-dd");
        int weekNum = 0;
        try {
            Date dt = formatter_new.parse(this.liveDate);
            Calendar cal = Calendar.getInstance();
            cal.setTime(dt);
            weekNum = cal.get(Calendar.DAY_OF_WEEK) - 1;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        File f = new File("Account\\userLive.txt");
        try {
            // Add new property to the json object.
            jsonObject.addProperty("liveID", this.liveID);
            jsonObject.addProperty("coachName", this.coachName);
            jsonObject.addProperty("stuName",stuName);
            jsonObject.addProperty("liveTitle", this.liveTitle);
            jsonObject.addProperty("week", this.week);
            jsonObject.addProperty("liveDate", this.liveDate);
            jsonObject.addProperty("scheduledStartTime", this.scheduledStartTime);
            jsonObject.addProperty("selected", this.selected);
            // Open a file stream to write to the file.
            FileOutputStream fos = new FileOutputStream(f, true);
            OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
            // Write the json object to the file.
            osw.write(jsonObject.toString());
            osw.write("\r\n");
            osw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to record the detailed record of live-book information.
     * This method is called when the user books or cancels the reservation of
     * a live session. And the recorded information shows which user booked which
     * live.
     */
    public void selectedLiveRecord(String userID, String userSelectedDate) {
        JsonObject jsonObject = new JsonObject();
        SimpleDateFormat formatter_new = new SimpleDateFormat("yyyy-MM-dd");
        int weekNum = 0;
        try {
            Date dt = formatter_new.parse(userSelectedDate);
            Calendar cal = Calendar.getInstance();
            cal.setTime(dt);
            weekNum = cal.get(Calendar.DAY_OF_WEEK) - 1;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        File f = new File("Account\\LiveInfoForCoach.txt");
        try {
            // Add new property to the json object.
            jsonObject.addProperty("liveID", this.liveID);
            jsonObject.addProperty("coachName", this.coachName);
            jsonObject.addProperty("liveTitle", this.liveTitle);
            jsonObject.addProperty("week", weekNum);
            jsonObject.addProperty("scheduledStartTime", this.scheduledStartTime);
            jsonObject.addProperty("userID", userID);
            jsonObject.addProperty("liveDate", userSelectedDate);
            // Open a file stream to write to the file.
            FileOutputStream fos = new FileOutputStream(f, true);
            OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
            // Write the json object to the file.
            osw.write(jsonObject.toString());
            osw.write("\r\n");
            osw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}