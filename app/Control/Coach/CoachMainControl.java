package app.Control.Coach;

import app.Control.Launch.appMain;
import app.Entity.Account.CurrentAccount;
import app.Entity.Live.CoachLive;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

/**
 * This class is the control class for coachUI
 * This class will show the student livebook information
 * and it will allow the coach to choose the student to start live session
 */

public class CoachMainControl {
    private String coach;
    private appMain appmain = new appMain();

    /**
     * This method is for getting student information and put them in a table and a choice box.
     */
    public ArrayList getStuName(){
        ArrayList Stuname = new ArrayList();
        ArrayList stuTemp = new ArrayList();
        coach = CurrentAccount.getCurAccount().getId();
        File f = new File("Account\\LiveInfoForCoach.txt");
        try {
            String str = null;
            FileInputStream fis = new FileInputStream(f);
            InputStreamReader reader = new InputStreamReader(fis, "UTF-8");
            BufferedReader br = new BufferedReader(reader);
            while ((str = br.readLine()) != null) {
                if(Objects.equals(coach,appMain.jsonReader(str).get("coachName").toString().replace("\"", ""))){
                    String name = appMain.jsonReader(str).get("userID").toString().replace("\"", "");
                    String date = appMain.jsonReader(str).get("scheduledStartTime").toString().replace("\"", "");
                    Stuname.add(name +": "+date);
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Stuname;
    }

    /**
     * This method is for getting student information and put them in a table and a choice box.
     */
    public ArrayList<CoachLive> getStu(){
        ArrayList<CoachLive> coachLiveData = new ArrayList<CoachLive>();
        coach = CurrentAccount.getCurAccount().getId();
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = null;
        File f = new File("Account\\LiveInfoForCoach.txt");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dt = new Date();
        String curDate = formatter.format(dt);
        try {
            String str = null;
            FileInputStream fis = new FileInputStream(f);
            InputStreamReader reader = new InputStreamReader(fis, "UTF-8");
            BufferedReader br = new BufferedReader(reader);

            while ((str = br.readLine()) != null) {
                jsonObject = (JsonObject) jsonParser.parse(str);
                if(Objects.equals(coach,appMain.jsonReader(str).get("coachName").toString().replace("\"", ""))){
                    CoachLive info = new Gson().fromJson(appMain.jsonReader(str),CoachLive.class);
                    if(jsonObject.get("liveDate").toString().replace("\"", "").equals(curDate)){
                        coachLiveData.add(info);
                    }
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
        return coachLiveData;
    }
}