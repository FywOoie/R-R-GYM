package app.Control.Coach;

import app.Control.Launch.appMain;
import app.Entity.Account.CurrentAccount;
import app.Entity.Video.Videoinfo;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

/**
 * This class is the control class for coachVideoUI
 * This class will allow the coach to assign video classes for students
 * Coach can see the student's video assignment records
 */

public class CoachVideoControl {

    @FXML
    private ChoiceBox<String> choice1;
    @FXML
    private ChoiceBox<String> choice2;

    private static String student;
    private static String video;
    private static String coach;

    public static String getCoach() {
        return coach;
    }

    public static String getStudent() {
        return student;
    }

    public static String getVideo() {
        return video;
    }

    public static void setVideo(String video) {
        CoachVideoControl.video = video;
    }

    public static void setCoach(String coach) {
        CoachVideoControl.coach = coach;
    }

    public static void setStudent(String student) {
        CoachVideoControl.student = student;
    }

    /**
     * This method is for loading the student's video information records
     */
    public ArrayList<Videoinfo> getInfo(){
        ArrayList<Videoinfo> videoData = new ArrayList<Videoinfo>();
        coach = CurrentAccount.getCurAccount().getId();
        File f = new File("Account\\StudentVideo.txt");
        try {
            String str = null;
            FileInputStream fis = new FileInputStream(f);
            InputStreamReader reader = new InputStreamReader(fis, "UTF-8");
            BufferedReader br = new BufferedReader(reader);
            while ((str = br.readLine()) != null) {
                if(Objects.equals(coach,appMain.jsonReader(str).get("CoachName").toString().replace("\"", ""))){
                    Videoinfo info = new Gson().fromJson(appMain.jsonReader(str),Videoinfo.class);
                    videoData.add(info);
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return videoData;
    }

    /**
     * This method is button listener
     * when button is clicked, it will write the student, coach and video information to the txt
     *
     */
    public int SaveVideoClick(){
        int flag = 0;
        int number = 0;
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("StuName",student);
        jsonObject.addProperty("VideoName",video);
        jsonObject.addProperty("CoachName",coach);
        if(student == null || video == null){
            number = 2;
        }
        else {
            number = checkRepetition();
        }
        switch (number){
            case 1:
                flag = 1;
                break;
            case 2:
                flag = 2;
                break;
            default:
                flag = 3;
                File f= new File("Account\\StudentVideo.txt");
                try {
                    FileOutputStream fos = new FileOutputStream(f,true);
                    OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
                    osw.write(jsonObject.toString());
                    osw.write("\r\n");
                    osw.close();
                }catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
        getInfo();
        return flag;
    }

    /**
     * This method is for checking whether the video has already enter in the student video list
     * @return check, a int number for method SaveVideoClick to detect
     */
    public int checkRepetition(){
        int check=0;
        File f= new File("Account\\StudentVideo.txt");
        try {
            String str =null;
            FileInputStream fis = new FileInputStream(f);
            InputStreamReader reader = new InputStreamReader(fis, "UTF-8");
            BufferedReader br = new BufferedReader(reader);
            while ((str = br.readLine()) != null) {
                if(Objects.equals(student, appMain.jsonReader(str).get("StuName").toString().replace("\"", ""))
                        && Objects.equals(video, appMain.jsonReader(str).get("VideoName").toString().replace("\"", ""))
                        && Objects.equals(coach, appMain.jsonReader(str).get("CoachName").toString().replace("\"", ""))){

                    check = 1;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }

}
